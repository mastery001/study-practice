package spider;

/**
 * 爬虫策略
 * @author zouziwen
 *
 * 2016年5月18日 上午11:51:26
 */
public interface CrawlPolicy {
	
	/**
	 * 开始爬行
	 * @param seeds	一组网址
	 * @param pageExtractor 
	 * @param pageFetcher	网页抓取者
	 * @param receiver		网页接收者
	 * 2016年5月18日 下午12:00:18
	 */
	void crawling(String[] seeds , PageExtractor pageExtractor, PageFetcher pageFetcher, PageContentReceiver receiver);
	
    
}
