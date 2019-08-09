package spider;

import java.io.InputStream;
import java.util.Map;

/**
 * 网页抓取者
 * @author zouziwen
 *
 * 2016年5月17日 下午3:28:48
 */
public interface PageFetcher {
	
	PageContent fetch(String url) throws FetchException;
	
	/**
	 * 网页内容
	 * @author zouziwen
	 *
	 * 2016年5月17日 下午3:41:49
	 */
	public interface PageContent {
		
		/**
		 * 当前网页的url
		 * @return
		 * 2016年5月18日 上午11:27:52
		 */
		String url();
		
		/**
		 * 返回的头部
		 * @return
		 * 2016年5月18日 上午11:27:46
		 */
		Map<String , String> header();
		
		/**
		 * 二进制的内容
		 * @return
		 * 2016年5月18日 上午11:27:18
		 */
		byte[] byteContent();
		
		/**
		 * 字符形式的内容
		 * @return
		 * 2016年5月18日 上午11:27:40
		 */
		String content();
		
		/**
		 * 抓取的网页类型
		 * @return
		 * 2016年5月24日 下午1:25:43
		 */
		String contentType();
		
		/**
		 * 流式内容
		 * @return
		 * 2016年5月18日 上午11:27:26
		 */
		InputStream stream();
	}
}
