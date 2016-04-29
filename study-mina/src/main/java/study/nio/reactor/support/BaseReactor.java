package study.nio.reactor.support;

import study.nio.reactor.FilterChainBuilder;
import study.nio.reactor.Reactor;

public abstract class BaseReactor implements Reactor {

	/**
	 * Current filter chain builder.
	 */
	private FilterChainBuilder filterChainBuilder = new DefaultFilterChainBuilder();

	@Override
	public FilterChainBuilder getFilterChainBuilder() {
		return filterChainBuilder;
	}

	@Override
	public DefaultFilterChainBuilder getFilterChain() {
		if (filterChainBuilder instanceof DefaultFilterChainBuilder) {
			return (DefaultFilterChainBuilder) filterChainBuilder;
		} else {
			throw new IllegalStateException("Current filter chain builder is not a DefaultIoFilterChainBuilder.");
		}
	}

}
