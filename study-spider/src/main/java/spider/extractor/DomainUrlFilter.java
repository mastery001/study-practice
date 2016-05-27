package spider.extractor;

import spider.extractor.PageUrlExtractor.UrlFilter;

public class DomainUrlFilter implements UrlFilter {

	private final String[] domains;

	public DomainUrlFilter(String[] domains) {
		this.domains = domains;
	}

	@Override
	public boolean accept(String url) {
		if (urlInDomain(url))
			return true;
		else
			return false;
	}

	/**
	 * 该url是在域名下
	 * 
	 * @param url
	 * @return 2016年5月17日 下午8:38:38
	 */
	private boolean urlInDomain(String url) {
		for (String domain : domains) {
			if (url.indexOf(domain) != -1) {
				return true;
			}
		}
		return false;
	}
}

