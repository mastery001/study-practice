package study.nio.reactor;

import java.util.List;

import study.nio.reactor.Filter.NextFilter;
import study.nio.reactor.Filter.WriteRequest;

@SuppressWarnings("rawtypes")
public interface FilterChain {

	Session getSession();

	Entry getEntry(String name);

	Filter get(String name);

	NextFilter getNextFilter(String name);

	List getAll();

	List getAllReversed();

	boolean contains(String name);

	boolean contains(Filter filter);

	boolean contains(Class filterType);

	void addFirst(String name, Filter filter);

	void addLast(String name, Filter filter);

	void addBefore(String baseName, String name, Filter filter);

	/**
	 * Adds the specified filter with the specified name just after the filter
	 * whose name is <code>baseName</code> in this chain.
	 * 
	 * @throws FilterLifeCycleException
	 *             if {@link Filter#onPostAdd(FilterChain, String, NextFilter)}
	 *             or {@link Filter#init()} throws an exception.
	 */
	void addAfter(String baseName, String name, Filter filter);

	/**
	 * Removes the filter with the specified name from this chain.
	 * 
	 * @throws FilterLifeCycleException
	 *             if
	 *             {@link Filter#onPostRemove(FilterChain, String, NextFilter)}
	 *             or {@link Filter#destroy()} throws an exception.
	 */
	Filter remove(String name);

	/**
	 * Removes all filters added to this chain.
	 * 
	 * @throws Exception
	 *             if
	 *             {@link Filter#onPostRemove(FilterChain, String, NextFilter)}
	 *             thrown an exception.
	 */
	void clear() throws Exception;

	public void fireSessionCreated(Session session);

	public void fireSessionOpened(Session session);

	public void fireSessionClosed(Session session);

	public void fireMessageReceived(Session session, Object message);

	public void fireMessageSent(Session session, WriteRequest request);

	public void fireExceptionCaught(Session session, Throwable cause);

	public interface Entry {
		/**
		 * Returns the name of the filter.
		 */
		String getName();

		/**
		 * Returns the filter.
		 */
		Filter getFilter();

		/**
		 * Returns the {@link NextFilter} of the filter.
		 * 
		 * @throws IllegalStateException
		 *             if the {@link NextFilter} is not available
		 */
		NextFilter getNextFilter();
	}

}
