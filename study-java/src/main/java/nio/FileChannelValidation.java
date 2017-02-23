package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.junit.Test;

public class FileChannelValidation {
	
	/**  
	 * @throws Exception  
	 * @Description:  使用RandomAccessFile访问文件方式，原理是获取到底层操作系统的fd，
	 * 所以调用seek方法后，对应FileChannel的position为seek后的位置；
	 * 但若是一个新的RandomAccessFile访问同一个文件，生成了一个新的fd，所以两者position相互不影响
	 */
	@Test
	public void testPositionVisibility() throws Exception {
		File file = new File("out.txt");
		RandomAccessFile filePointer = new RandomAccessFile(file , "r");
		RandomAccessFile fileOtherPointer = new RandomAccessFile(file , "r");
		FileChannel fileOtherChannel = fileOtherPointer.getChannel();
		filePointer.seek(20);
		FileChannel fileChannel = filePointer.getChannel();
		System.out.println(fileChannel.position());
		System.out.println(fileOtherChannel.position());
		filePointer.close();
		fileOtherPointer.close();
	}
	
	@Test
	public void testTruncate() throws Exception{
		File file = new File("out.txt");
		RandomAccessFile filePointer = new RandomAccessFile(file , "rw");
		FileChannel fileChannel = filePointer.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
		// truncate()方法用于设置文件的长度size，若设置的size<当前size，则多出的部分会被删除
		fileChannel.truncate(100);
		fileChannel.read(buffer);
		buffer.flip();
		byte[] dst = new byte[buffer.limit()];
		buffer.get(dst);
		System.out.println(Arrays.toString(dst));
		filePointer.close();
	}
}
