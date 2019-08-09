package spider.extractor;

import java.util.Objects;
import java.util.Set;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import spider.PageExtractor;

/**
 * org.htmlparser工具包的抽象类
 * @author zouziwen
 *
 * 2016年5月23日 下午8:34:09
 */
public abstract class HtmlParserExtractor implements PageExtractor {

	private final NodeFilter filter;

	public HtmlParserExtractor() {
		filter = initNodeFilter();
		Objects.requireNonNull(filter, "filter should init");
	}

	@Override
	public Set<String> extract(String url) throws Exception {
		Parser parser = new Parser(url);
		parser.setEncoding("gb2312");
		NodeList list = parser.extractAllNodesThatMatch(filter);
		return extract(list);
	}

	protected abstract Set<String> extract(NodeList list) throws Exception;

	protected abstract NodeFilter initNodeFilter();

}
