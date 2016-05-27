package spider;

import java.util.Set;

/**
 * 网页内容提取者
 * @author zouziwen
 *
 * 2016年5月17日 下午5:42:30
 */
public interface PageExtractor {

	/**
	 * 对特定的网址中的内容进行提取网址
	 * @param url
	 * @return
	 * @throws Exception
	 * 2016年5月17日 下午5:45:14
	 */
	Set<String> extract(String url) throws Exception;
	
}
