package spider.extractor;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

/**
 * 图片导出器
 * @author zouziwen
 *
 * 2016年5月23日 下午8:24:42
 */
public class ImgExtractor extends HtmlParserExtractor{

	@Override
	protected Set<String> extract(NodeList list) throws Exception {
		Set<String> imgs = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			ImageTag tag = (ImageTag)list.elementAt(i);
			imgs.add(tag.getImageURL());
		}
		return imgs;
	}

	@Override
	protected NodeFilter initNodeFilter() {
		return new NodeClassFilter(ImageTag.class);
	}

}
