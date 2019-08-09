package spider.extractor;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * 网页中所有url的提取者
 * 
 * @author zouziwen
 *
 *         2016年5月17日 下午6:03:02
 */
public class PageUrlExtractor extends HtmlParserExtractor {

	public interface UrlFilter {

		UrlFilter TRUE_FILTER = new UrlFilter() {
			@Override
			public boolean accept(String url) {
				return true;
			}
		};

		boolean accept(String url);
	}

	private final UrlFilter filter;

	public PageUrlExtractor() {
		this(UrlFilter.TRUE_FILTER);
	}

	public PageUrlExtractor(UrlFilter filter) {
		this.filter = filter;
	}

	@Override
	protected Set<String> extract(NodeList list) throws Exception {
		Set<String> links = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			Node tag = list.elementAt(i);
			// <a> 标签
			if (tag instanceof LinkTag) {
				LinkTag link = (LinkTag) tag;
				String linkUrl = link.getLink();// URL
				if (filter.accept(linkUrl))
					links.add(linkUrl);
			} else {
				// <frame> 标签
				// 提取 frame 里 src 属性的链接， 如 <frame src="test.html"/>
				String frame = tag.getText();
				int start = frame.indexOf("src=");
				frame = frame.substring(start);
				int end = frame.indexOf(" ");
				if (end == -1)
					end = frame.indexOf(">");
				String frameUrl = frame.substring(5, end - 1);
				if (filter.accept(frameUrl))
					links.add(frameUrl);
			}
		}
		return links;
	}

	@Override
	protected NodeFilter initNodeFilter() {
		// 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性
		NodeFilter frameFilter = new NodeFilter() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean accept(Node node) {
				if (node.getText().startsWith("frame src=")) {
					return true;
				} else {
					return false;
				}
			}
		};
		// OrFilter 来设置过滤 <a> 标签和 <frame> 标签
		OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
		return linkFilter;
	}
}
