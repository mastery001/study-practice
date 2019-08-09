package spider.extractor;

import java.util.Set;

import org.junit.Test;

import spider.extractor.PageUrlExtractor;
import spider.extractor.PageUrlExtractor.UrlFilter;

public class PageUrlExtractorTest {

	@Test
	public void testExtract() throws Exception {
		final String visitUrl = "http://www.le.com";
		PageUrlExtractor extractor = new PageUrlExtractor(new UrlFilter() {

			@Override
			public boolean accept(String url) {
				if (url.startsWith(visitUrl))
					return true;
				else
					return false;
			}

		});
		Set<String> urls = extractor.extract(visitUrl);
		System.out.println(urls);
	}

}
