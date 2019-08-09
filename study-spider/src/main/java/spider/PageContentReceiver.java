package spider;

import spider.PageFetcher.PageContent;

/**
 * 网页内容接收者
 * @author zouziwen
 *
 * 2016年5月17日 下午3:36:01
 */
public interface PageContentReceiver {

	/**
	 * 该过程不能抛出异常
	 * @param pageContent
	 * 2016年5月18日 上午11:54:21
	 */
	void receive(PageContent pageContent);
}
