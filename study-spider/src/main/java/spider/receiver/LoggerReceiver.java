package spider.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spider.PageContentReceiver;
import spider.PageFetcher.PageContent;

public class LoggerReceiver implements PageContentReceiver {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void receive(PageContent pageContent){
		logger.info("reveive new page : {}",
				pageContent == null ? pageContent : pageContent.url());
	}

}
