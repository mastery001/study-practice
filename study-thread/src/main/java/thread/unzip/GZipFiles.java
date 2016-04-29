package thread.unzip;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipFiles {
	
	private static final int THREAD_COUNT = 4;
	private static ExecutorService service=Executors.newFixedThreadPool(THREAD_COUNT);
	
	public static void gzip(File file) {
		if(!file.isDirectory()) {
			GZipRunnable gzipRunnable = new GZipRunnable(file);
			service.submit(gzipRunnable);
		}else {
			File[] files = file.listFiles();
			for(File f : files) {
				gzip(f);
			}
		}
	}
	
	public static void shutdown() {
		service.shutdown();
	}
}
