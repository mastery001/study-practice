package spider;

/**
 * 爬虫客户端
 * @author zouziwen
 *
 * 2016年5月17日 下午8:20:04
 */
public interface SpiderClient {
	
	/**
	 * 一组网页
	 * @param seeds
	 * @throws Exception
	 * 2016年5月17日 下午8:20:39
	 */
	void crawling(String[] seeds) throws Exception;

}
