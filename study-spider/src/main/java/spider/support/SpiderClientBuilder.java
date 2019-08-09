package spider.support;

import spider.CrawlPolicy;
import spider.PageContentReceiver;
import spider.PageExtractor;
import spider.PageFetcher;
import spider.SpiderClient;
import spider.crawl.BreadthFirstPolicy;
import spider.extractor.PageUrlExtractor;
import spider.fetcher.HttpFetcher;
import spider.receiver.LoggerReceiver;

/**
 * SpiderClient构造器
 * 
 * @author zouziwen
 *
 *         2016年5月18日 上午11:07:28
 */
public final class SpiderClientBuilder {

	/**
	 * 网页抓取者 2016年5月18日 上午11:08:21
	 */
	private PageFetcher pageFetcher;

	/**
	 * 网页接收者 2016年5月18日 上午11:08:28
	 */
	private PageContentReceiver receiver;
	
	private PageExtractor pageExtractor;

	private CrawlPolicy policy;
	
	private SpiderClientBuilder(){}
	
	public static final SpiderClientBuilder newInstance() {
		return new SpiderClientBuilder();
	}
	
	public SpiderClient build() {
		if (pageFetcher == null) {
			pageFetcher = new HttpFetcher();
		}
		if (receiver == null) {
			receiver = new LoggerReceiver();
		}
		if(policy == null) {
			policy = new BreadthFirstPolicy();
		}
		if(pageExtractor == null) {
			pageExtractor = new PageUrlExtractor();
		}
		return SimpleSpiderClient.newSpiderClient(pageExtractor ,pageFetcher, receiver , policy);
	}

	/**
	 * 构造网页抓取者
	 * 
	 * @param pageFetcher
	 * @return 2016年5月18日 上午11:08:36
	 */
	public SpiderClientBuilder withPageFetcher(PageFetcher pageFetcher) {
		this.pageFetcher = pageFetcher;
		return this;
	}
	
	/**
	 * 构造网页内容导出者
	 * 
	 * @param pageFetcher
	 * @return 2016年5月18日 上午11:08:36
	 */
	public SpiderClientBuilder withPageExtractor(PageExtractor pageExtractor) {
		this.pageExtractor = pageExtractor;
		return this;
	}

	/**
	 * 构造网页接收者
	 * 
	 * @param receiver
	 * @return 2016年5月18日 上午11:08:45
	 */
	public SpiderClientBuilder withPageContentReceiver(PageContentReceiver receiver) {
		this.receiver = receiver;
		return this;
	}
	
	/**
	 * 构造爬虫策略
	 * 
	 * @param receiver
	 * @return 2016年5月18日 上午11:08:45
	 */
	public SpiderClientBuilder withCrawlPolicy(CrawlPolicy policy) {
		this.policy = policy;
		return this;
	}
}
