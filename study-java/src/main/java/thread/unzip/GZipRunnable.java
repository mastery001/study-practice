package thread.unzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 对于单个文件使用GZip压缩。
 * @author zouziwen
 *
 */
public class GZipRunnable implements Runnable {

	private final File file;

	public GZipRunnable(File file) {
		this.file = file;
	}

	@Override
	public void run() {
		if (!file.getName().endsWith(".gz")) {
			File outputFile = new File(file.getParent(), file.getName() + ".gz");

			if (!outputFile.exists()) {
				CountTime countTime = new CountTime(file);
				Thread t = new Thread(countTime);
				t.start();
				InputStream is = null;
				OutputStream os = null;
				try {
					is = new BufferedInputStream(new FileInputStream(file));
					os = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outputFile)));
					int b;
					while ((b = is.read()) != -1) {
						os.write(b);
					}
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (os != null) {
								os.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				t.interrupt();
			} else {
				System.out.println(outputFile+"文件已经存在，无法压缩!");
			}
		}
	}

}
