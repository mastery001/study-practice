package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @Description: MappedByteBufferModel的MapMode的三种模式测试
 * @Author:zouziwen
 * @Since:2017年2月13日
 * @Version:1.1.0
 */
public class MappedByteBufferModel {

	public static void main(String[] args) throws Exception {
		File tmpFile = File.createTempFile("mapped_test", null);
		RandomAccessFile accessFile = null;
		FileChannel channel = null;
		try {
			accessFile = new RandomAccessFile(tmpFile, "rw");
			channel = accessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(100);
			buffer.put("This is the file content".getBytes());
			buffer.flip();
			channel.write(buffer, 0);
			buffer.clear();
			buffer.put("This is more file content".getBytes());
			buffer.flip();
			channel.write(buffer, 8192);
			// 只读模式
			MappedByteBuffer r = channel.map(MapMode.READ_ONLY, 0, channel.size());
			// 写权限模式
			MappedByteBuffer rw = channel.map(MapMode.READ_WRITE, 0, channel.size());
			// 写时拷贝(copy-on-write)
			MappedByteBuffer cow = channel.map(MapMode.PRIVATE, 0, channel.size());

			System.out.println("Begin");
			showBuffer(r, rw, cow);

			// 修改cow的buffer
			cow.position(8);
			cow.put("COW".getBytes());
			System.out.println("Change to COW buffer");
			showBuffer(r, rw, cow);

			// 修改rw的buffer
			rw.position(9);
			rw.put(" R/W ".getBytes());
			rw.position(8194);
			rw.put(" R/W ".getBytes());
			rw.force();
			System.out.println("Change to R/W buffer");
			showBuffer(r, rw, cow);

			// 使用channel进行写文件
			buffer.clear();
			buffer.put("Channel write ".getBytes());
			buffer.flip();

			channel.write(buffer, 0);

			buffer.rewind();
			channel.write(buffer, 8202);
			System.out.println("Write on channel");
			showBuffer(r, rw, cow);

			// 再次修改cow的buffer
			cow.position(8207);
			cow.put(" COW2 ".getBytes());
			System.out.println("Second change to COW buffer");
			showBuffer(r, rw, cow);

			// 再次修改rw的buffer
			rw.position(0);
			rw.put(" R/W2 ".getBytes());
			rw.position(8210);
			rw.put(" R/W2 ".getBytes());
			rw.force();
			System.out.println("Second change to R/W buffer");
			showBuffer(r, rw, cow);
		} finally {
			if (channel != null) {
				channel.close();
			}
			if (accessFile != null) {
				accessFile.close();
			}
			tmpFile.deleteOnExit();
		}
	}

	private static void showBuffer(MappedByteBuffer r, MappedByteBuffer rw, MappedByteBuffer cow) throws Exception {
		dumpBuffer("R/O", r);
		dumpBuffer("R/W", rw);
		dumpBuffer("COW", cow);
		System.out.println("");
	}

	// Dump buffer content, counting and skipping nulls
	public static void dumpBuffer(String prefix, ByteBuffer buffer) throws Exception {
		System.out.print(prefix + ": '");
		int nulls = 0;
		int limit = buffer.limit();
		for (int i = 0; i < limit; i++) {
			char c = (char) buffer.get(i);
			if (c == '\u0000') {
				nulls++;
				continue;
			}
			if (nulls != 0) {
				System.out.print("|[" + nulls + " nulls]|");
				nulls = 0;
			}
			System.out.print(c);
		}
		System.out.println("'");
	}
}
