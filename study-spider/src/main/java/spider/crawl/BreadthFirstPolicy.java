package spider.crawl;

import java.util.Set;

import spider.BaseCrawlPolicy;
import spider.PageContentReceiver;
import spider.PageExtractor;
import spider.PageFetcher;
import spider.PageFetcher.PageContent;

/**
 * 宽度优先爬虫策略
 * 
 * @author zouziwen
 *
 *         2016年5月18日 上午11:52:29
 */
public class BreadthFirstPolicy extends BaseCrawlPolicy {

	/**
	 * 使用种子初始化URL队列
	 */
	private SpiderQueue initCrawlerWithSeeds(String[] seeds) {
		SpiderQueue queue = new SpiderQueue();
		for (int i = 0; i < seeds.length; i++)
			queue.addUnvisitedUrl(seeds[i]);
		return queue;
	}
	
	@Override
	public void crawling0(String[] seeds, PageExtractor pageExtractor, PageFetcher pageFetcher,
			PageContentReceiver receiver) {
		SpiderQueue queue = initCrawlerWithSeeds(seeds);
		// 循环条件：待抓取的链接不空且抓取的网页不多于 1000
		while (!queue.unVisitedUrlsEmpty() && queue.getVisitedUrlNum() <= 1000) {
			// 队头 URL 出队列
			String visitUrl = queue.unVisitedUrlDeQueue();
			if (visitUrl == null)
				continue;

			// 抓取
			PageContent pageContent = pageFetcher.fetch(visitUrl);

			// 接收内容
			receiver.receive(pageContent);

			// 该 URL 放入已访问的 URL 中
			queue.addVisitedUrl(visitUrl);

			// 提取出下载网页中的 URL
			try {
				Set<String> links = pageExtractor.extract(visitUrl);
				// 新的未访问的 URL 入队
				for (String link : links) {
					queue.addUnvisitedUrl(link);
				}
			} catch (Exception e) {

			}
		}
	}
	
}
