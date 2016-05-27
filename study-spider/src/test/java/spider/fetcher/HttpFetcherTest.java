package spider.fetcher;

import org.junit.Test;

import spider.PageFetcher;
import spider.PageFetcher.PageContent;
import spider.fetcher.HttpFetcher;

public class HttpFetcherTest {

	@Test
	public void testFetch() {
		String url = "https://www.baidu.com?wd=1";
		PageFetcher fetcher = new HttpFetcher();
		PageContent pageContent = fetcher.fetch(url);
		System.out.println(pageContent.content());
	}

}
