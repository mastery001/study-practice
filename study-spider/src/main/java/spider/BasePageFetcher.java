package spider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePageFetcher implements PageFetcher {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public PageContent fetch(String url) throws FetchException {
		// try {
		// url = UrlUtils.checkUrl(url);
		// } catch (Exception e) {
		// throw new FetchException(e.getMessage(), e);
		// }
		logger.debug("fetch {} start", url);
		return fetch0(url);
	}

	protected abstract PageContent fetch0(String url) throws FetchException;

	protected class SimplePageContent implements PageContent {

		private final byte[] byteContent;

		private final String content;

		private final InputStream stream;

		private final String url;

		private final Map<String, String> header;

		public SimplePageContent(String url, byte[] byteContent, String content, Map<String, String> header) {
			this.url = url;
			this.byteContent = byteContent;
			this.content = content;
			if (byteContent != null) {
				stream = new ByteArrayInputStream(byteContent);
			} else {
				stream = null;
			}
			this.header = header;
		}

		@Override
		public byte[] byteContent() {
			return this.byteContent;
		}

		@Override
		public InputStream stream() {
			return stream;
		}

		@Override
		public String url() {
			return this.url;
		}

		@Override
		public Map<String, String> header() {
			return header;
		}

		@Override
		public String content() {
			return content;
		}

		@Override
		public String contentType() {
			if(header != null) {
				return header.get("Content-Type");
			}
			return null;
		}

	}
}
