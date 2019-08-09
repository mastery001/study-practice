package spider.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

	public static final Pattern URL_REGEX = Pattern.compile(
			"^(http|www|https|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * 检查url，若不符合格式则提示异常,否则返回正确的url
	 * 
	 * @param url
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 *             2016年5月17日 下午6:22:34
	 */
	public static String checkUrl(String url) throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(url, "url is null");
		int wwwIndex = url.indexOf("www");
		if (wwwIndex == 0) {
			url = "http://" + url;
		}
		int moreSprit = url.lastIndexOf("/");
		if(moreSprit == url.length() - 1) {
			url = url.substring(0 , moreSprit);
		}
		Matcher matcher = URL_REGEX.matcher(url);
		boolean flag = matcher.find();
		if (!flag) {
			throw new IllegalArgumentException("invalid url " + url);
		}
		return url;
	}
	
	public static String[] extractDomain(String[] seeds) {
		String[] domains = new String[seeds.length];
		for(int i = 0; i < seeds.length; i++) {
			String url = seeds[i];
			if (url.startsWith("http://")) {
				url = url.substring(7);
			} else if (url.startsWith("https://")) {
				url = url.substring(8);
			}
			int index = url.indexOf("/");
			if (index != -1) {
				url.substring(0, index);
			}
			domains[i] = url;
		}
		return domains;
	}
	
	
	/**
	 * 根据 URL 和网页类型生成需要保存的网页的文件名，去除 URL 中的非文件名字符
	 */
	public static String getFileNameByUrl(String url, String contentType) {
		// 移除 "http://" 这七个字符
		url = url.substring(7);
		// 确认抓取到的页面为 text/html 类型
		if (contentType.indexOf("html") != -1) {
			// 把所有的url中的特殊符号转化成下划线
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
		} else {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
		}
		return url;
	}

}
