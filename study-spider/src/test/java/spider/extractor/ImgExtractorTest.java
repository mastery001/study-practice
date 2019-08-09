package spider.extractor;

import java.util.Set;

import org.junit.Test;

public class ImgExtractorTest {

	@Test
	public void testExtractString() throws Exception {
		Set<String> imgs = new ImgExtractor().extract("http://image.baidu.com/");
		System.out.println(imgs);
	}

}
