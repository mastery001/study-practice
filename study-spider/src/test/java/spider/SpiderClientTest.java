package spider;

import org.junit.Test;

import spider.SpiderClient;
import spider.extractor.ImgExtractor;
import spider.receiver.FileReceiver;
import spider.support.SpiderClientBuilder;

public class SpiderClientTest {

	@Test
	public void testCrawling() throws Exception {
		SpiderClient client = SpiderClientBuilder.newInstance().withPageExtractor(new ImgExtractor())
				.withPageContentReceiver(new FileReceiver("E:\\practice\\workbench\\image")).build();
		client.crawling(new String[] { "http://lol.qq.com/" });
	}

}
