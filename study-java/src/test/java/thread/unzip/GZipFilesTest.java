package thread.unzip;

import java.io.File;

public class GZipFilesTest {

	public static void main(String[] args) {
		File file = new File("E:/jar包");
		GZipFiles.gzip(file);
		GZipFiles.shutdown();
	}

}
