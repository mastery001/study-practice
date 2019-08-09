package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

  
/**  
 *@Description:  ReadableByteChannel和WritableByteChannel的Copy操作
 *@Author:zouziwen
 *@Since:2017年2月11日  
 *@Version:1.1.0  
 */
public class ChannelCopy {
	
	public static void main(String[] args) throws IOException {
		ReadableByteChannel in = Channels.newChannel(System.in);
		WritableByteChannel out = Channels.newChannel(System.out);
		
		channelCopy1(in, out);
		
		in.close();
		out.close();
		
	}
	
	static void channelCopy(ReadableByteChannel src , WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
		while(src.read(buffer) != -1) {
			// 将缓冲区置为待读取状态
			buffer.flip();
			
			// 写入
			dest.write(buffer);
			
			// 丢弃已经读取的字节，并将缓冲区置为待填充状态
			buffer.compact();
		}
		
		buffer.flip();
		
		// 检测缓冲区是否还存在值，若存在则再写入
		while(buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
	
	static void channelCopy1(ReadableByteChannel src , WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
		while(src.read(buffer) != -1) {
			buffer.flip();
			
			while(buffer.hasRemaining()) {
				dest.write(buffer);
			}
			
			buffer.clear();
		}
	}
}
