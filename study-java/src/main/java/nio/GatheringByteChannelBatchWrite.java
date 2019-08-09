package nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

  
/**  
 *@Description:  GatheringByteChannel写操作集合多个缓冲区的数据
 *@Author:zouziwen
 *@Since:2017年2月11日  
 *@Version:1.1.0  
 */
public class GatheringByteChannelBatchWrite {
	
	private static final String OUT_FILE_NAME = "out.txt";
	private static String [] col1 = {"Aggregate", "Enable", "Leverage", "Facilitate", "Synergize", "Repurpose", "Strategize", "Reinvent", "Harness"};
    private static String [] col2 = {"cross-platform", "best-of-breed", "frictionless", "ubiquitous", "extensible", "compelling",   "mission-critical", "collaborative", "integrated"};
    private static String [] col3 = {"methodologies", "infomediaries", "platforms", "schemas", "mindshare", "paradigms", "functionalities", "web services", "infrastructures"};
    private static String newline = System.getProperty ("line.separator");
    
    private static final Random random = new Random();
	
	public static void main(String[] args) throws IOException {
		int rep = 10;
		FileOutputStream out = new FileOutputStream(new File(OUT_FILE_NAME));
		GatheringByteChannel channel = out.getChannel();
		ByteBuffer[] buffers = mergeWrite(rep);
		channel.write(buffers);
		out.close();
	}

	private static ByteBuffer[] mergeWrite(int rep) {
		List<ByteBuffer> list = new LinkedList<ByteBuffer>();
		for(int i = 0 ; i < rep ; i ++) {
			list.add(pickRandom(col1 , " "));
			list.add(pickRandom(col2 , " "));
			list.add(pickRandom(col3 , newline));
		}
		return list.toArray(new ByteBuffer[list.size()]);
	}

	/**  
	 * 随机填充Buffer中的内容
	 * @param col
	 * @param string
	 * @return  
	 * @Description:  
	 */
	private static ByteBuffer pickRandom(String[] col, String separator) {
		String str = col[random.nextInt(col.length)];
		int totalSize = str.length() + separator.length();
		ByteBuffer buffer = ByteBuffer.allocate(totalSize);
		buffer.put(str.getBytes());
		buffer.put(separator.getBytes());
		buffer.flip();
		return buffer;
	}
}
