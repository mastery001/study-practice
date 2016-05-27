package spider;

import java.util.Objects;

import spider.util.UrlUtils;

public abstract class BaseCrawlPolicy implements CrawlPolicy {

	@Override
	public void crawling(String[] seeds, PageExtractor pageExtractor, PageFetcher pageFetcher,
			PageContentReceiver receiver) {
		validateSeeds(seeds);
		crawling0(seeds, pageExtractor, pageFetcher, receiver);
	}

	public abstract void crawling0(String[] seeds, PageExtractor pageExtractor, PageFetcher pageFetcher,
			PageContentReceiver receiver);

	/**
	 * 网址校验且提取域名
	 * 
	 * @param seeds
	 *            2016年5月17日 下午8:23:40
	 */
	private void validateSeeds(String[] seeds) {
		Objects.requireNonNull(seeds, "seeds is null");
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = UrlUtils.checkUrl(seeds[i]);
		}

	}

}
