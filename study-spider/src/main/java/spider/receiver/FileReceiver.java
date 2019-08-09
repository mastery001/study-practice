package spider.receiver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import spider.PageContentReceiver;
import spider.PageFetcher.PageContent;
import spider.util.UrlUtils;

public class FileReceiver implements PageContentReceiver {

	private String rootPath;
	
	public FileReceiver(String rootPath) {
		initRootPath(rootPath);
	}

	private void initRootPath(String rootPath) {
		if(rootPath == null) {
			this.rootPath = "";
		}else {
			File file = new File(rootPath);
			this.rootPath = file.getAbsolutePath() + "/";
		}
	}

	@Override
	public void receive(PageContent pageContent) {
		if (pageContent != null) {
			String url = pageContent.url();
			String filePath = UrlUtils.getFileNameByUrl(url, pageContent.contentType());
			byte[] data = pageContent.byteContent();
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(new FileOutputStream(new File(rootPath + filePath)));
				for (int i = 0; i < data.length; i++)
					out.write(data[i]);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
