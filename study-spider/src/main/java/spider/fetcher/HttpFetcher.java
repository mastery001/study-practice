package spider.fetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.http.HttpClientFactory;
import org.http.HttpResponseMessage;
import org.http.client.method.HttpGetRequest;
import org.http.exception.HttpInvokeException;

import spider.BasePageFetcher;
import spider.FetchException;

/**
 * 通过http抓取
 * 
 * @author zouziwen
 *
 *         2016年5月17日 下午4:21:31
 */
public class HttpFetcher extends BasePageFetcher {

	public static final HttpClientFactory DEFAULT_FACTORY = new HttpClientFactory() {
		final HttpClient httpClient;

		{
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			connectionManager.getParams().setConnectionTimeout(5000);
			connectionManager.getParams().setSoTimeout(15000);
			connectionManager.getParams().setDefaultMaxConnectionsPerHost(5);
			httpClient = new HttpClient(connectionManager);
		}

		@Override
		public HttpClient getConnection() {
			return httpClient;
		}
	};
	
	private final HttpClientFactory factory;

	public HttpFetcher() {
		this(DEFAULT_FACTORY);
	}

	public HttpFetcher(HttpClientFactory factory) {
		if(factory == null) {
			factory = DEFAULT_FACTORY;
		}
		this.factory = factory;
	}

	@Override
	protected PageContent fetch0(String url) throws FetchException {
		PageContent content = null;
		try {
			HttpResponseMessage response = new HttpGetRequest(url).sendRequest(factory);
			Header[] headers = response.getResponseHeaders();
			Map<String, String> headerMap = new HashMap<String, String>(headers.length, 1);
			for (Header header : headers) {
				headerMap.put(header.getName(), header.getValue());
			}
			content = new SimplePageContent(url, response.getResponseBody(), response.getContent() ,  headerMap);
		} catch (HttpInvokeException e) {
			throw new FetchException(e.getMessage(), e);
		} catch (IOException e) {
			throw new FetchException(e.getMessage(), e);
		}
		return content;
	}
}
