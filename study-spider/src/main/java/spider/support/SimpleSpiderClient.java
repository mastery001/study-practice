package spider.support;

import java.util.Objects;

import spider.CrawlPolicy;
import spider.PageContentReceiver;
import spider.PageExtractor;
import spider.PageFetcher;
import spider.SpiderClient;

/**
 * 爬虫客户端
 * 
 * @author zouziwen
 *
 *         2016年5月18日 上午11:14:08
 */
class SimpleSpiderClient implements SpiderClient {

	private final PageFetcher pageFetcher;

	private final PageContentReceiver receiver;

	private final CrawlPolicy crawPolicy;

	private final PageExtractor pageExtractor;

	private SimpleSpiderClient(PageExtractor pageExtractor, PageFetcher pageFetcher, PageContentReceiver receiver,
			CrawlPolicy crawPolicy) {
		Objects.requireNonNull(pageExtractor, "pageExtractor is null");
		Objects.requireNonNull(pageFetcher, "pageFetcher is null");
		Objects.requireNonNull(receiver, "receiver is null");
		Objects.requireNonNull(crawPolicy, "crawPolicy is null");
		this.pageExtractor = pageExtractor;
		this.pageFetcher = pageFetcher;
		this.receiver = receiver;
		this.crawPolicy = crawPolicy;
	}

	public static SpiderClient newSpiderClient(PageExtractor pageExtractor, PageFetcher pageFetcher,
			PageContentReceiver receiver, CrawlPolicy crawPolicy) {
		return new SimpleSpiderClient(pageExtractor, pageFetcher, receiver, crawPolicy);
	}

	@Override
	public void crawling(String[] seeds) throws Exception {
		crawPolicy.crawling(seeds, pageExtractor, pageFetcher, receiver);
	}

}
