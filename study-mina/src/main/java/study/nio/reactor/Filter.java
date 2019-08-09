package study.nio.reactor;

import java.net.SocketAddress;

public interface Filter {
	/**
	 * Invoked by {@link ReferenceCountingIoFilter} when this filter is added to
	 * a {@link FilterChain} at the first time, so you can initialize shared
	 * resources. Please note that this method is never called if you don't wrap
	 * a filter with {@link ReferenceCountingIoFilter}.
	 */
	void init() throws Exception;

	/**
	 * Invoked by {@link ReferenceCountingIoFilter} when this filter is not used
	 * by any {@link FilterChain} anymore, so you can destroy shared resources.
	 * Please note that this method is never called if you don't wrap a filter
	 * with {@link ReferenceCountingIoFilter}.
	 */
	void destroy() throws Exception;

	/**
	 * Invoked before this filter is added to the specified <tt>parent</tt>.
	 * Please note that this method can be invoked more than once if this filter
	 * is added to more than one parents. This method is not invoked before
	 * {@link #init()} is invoked.
	 *
	 * @param parent
	 *            the parent who called this method
	 * @param name
	 *            the name assigned to this filter
	 * @param nextFilter
	 *            the {@link NextFilter} for this filter. You can reuse this
	 *            object until this filter is removed from the chain.
	 */
	void onPreAdd(FilterChain parent, String name, NextFilter nextFilter) throws Exception;

	/**
	 * Invoked after this filter is added to the specified <tt>parent</tt>.
	 * Please note that this method can be invoked more than once if this filter
	 * is added to more than one parents. This method is not invoked before
	 * {@link #init()} is invoked.
	 *
	 * @param parent
	 *            the parent who called this method
	 * @param name
	 *            the name assigned to this filter
	 * @param nextFilter
	 *            the {@link NextFilter} for this filter. You can reuse this
	 *            object until this filter is removed from the chain.
	 */
	void onPostAdd(FilterChain parent, String name, NextFilter nextFilter) throws Exception;

	/**
	 * Invoked before this filter is removed from the specified <tt>parent</tt>.
	 * Please note that this method can be invoked more than once if this filter
	 * is removed from more than one parents. This method is always invoked
	 * before {@link #destroy()} is invoked.
	 * 
	 * @param parent
	 *            the parent who called this method
	 * @param name
	 *            the name assigned to this filter
	 * @param nextFilter
	 *            the {@link NextFilter} for this filter. You can reuse this
	 *            object until this filter is removed from the chain.
	 */
	void onPreRemove(FilterChain parent, String name, NextFilter nextFilter) throws Exception;

	/**
	 * Invoked after this filter is removed from the specified <tt>parent</tt>.
	 * Please note that this method can be invoked more than once if this filter
	 * is removed from more than one parents. This method is always invoked
	 * before {@link #destroy()} is invoked.
	 * 
	 * @param parent
	 *            the parent who called this method
	 * @param name
	 *            the name assigned to this filter
	 * @param nextFilter
	 *            the {@link NextFilter} for this filter. You can reuse this
	 *            object until this filter is removed from the chain.
	 */
	void onPostRemove(FilterChain parent, String name, NextFilter nextFilter) throws Exception;

	/**
	 * Filters {@link IoHandler#sessionCreated(Session)} event.
	 */
	void sessionCreated(NextFilter nextFilter, Session session) throws Exception;

	/**
	 * Filters {@link IoHandler#sessionOpened(Session)} event.
	 */
	void sessionOpened(NextFilter nextFilter, Session session) throws Exception;

	/**
	 * Filters {@link IoHandler#sessionClosed(Session)} event.
	 */
	void sessionClosed(NextFilter nextFilter, Session session) throws Exception;

	/**
	 * Filters {@link IoHandler#exceptionCaught(Session,Throwable)} event.
	 */
	void exceptionCaught(NextFilter nextFilter, Session session, Throwable cause) throws Exception;

	/**
	 * Filters {@link IoHandler#messageReceived(Session,Object)} event.
	 */
	void messageReceived(NextFilter nextFilter, Session session, Object message) throws Exception;

	/**
	 * Filters {@link IoHandler#messageSent(Session,Object)} event.
	 */
	void messageSent(NextFilter nextFilter, Session session, Object message) throws Exception;

	/**
	 * Filters {@link Session#close()} method invocation.
	 */
	void filterClose(NextFilter nextFilter, Session session) throws Exception;

	/**
	 * Filters {@link Session#write(Object)} method invocation.
	 */
	void filterWrite(NextFilter nextFilter, Session session, WriteRequest writeRequest) throws Exception;

	/**
	 * Represents the next {@link IoFilter} in {@link FilterChain}.
	 */
	public interface NextFilter {
		/**
		 * Forwards <tt>sessionCreated</tt> event to next filter.
		 */
		void sessionCreated(Session session);

		/**
		 * Forwards <tt>sessionOpened</tt> event to next filter.
		 */
		void sessionOpened(Session session);

		/**
		 * Forwards <tt>sessionClosed</tt> event to next filter.
		 */
		void sessionClosed(Session session);

		/**
		 * Forwards <tt>exceptionCaught</tt> event to next filter.
		 */
		void exceptionCaught(Session session, Throwable cause);

		/**
		 * Forwards <tt>messageReceived</tt> event to next filter.
		 */
		void messageReceived(Session session, Object message);

		/**
		 * Forwards <tt>messageSent</tt> event to next filter.
		 */
		void messageSent(Session session, Object message);

		/**
		 * Forwards <tt>filterWrite</tt> event to next filter.
		 */
		void filterWrite(Session session, WriteRequest writeRequest);

		/**
		 * Forwards <tt>filterClose</tt> event to next filter.
		 */
		void filterClose(Session session);
	}

	/**
	 * Represents write request fired by {@link Session#write(Object)}.
	 */
	public static class WriteRequest {

		private final Object message;

		private final SocketAddress destination;

		/**
		 * Creates a new instance with {@link WriteFuture}.
		 */
		public WriteRequest(Object message) {
			this(message, null);
		}

		/**
		 * Creates a new instance.
		 * 
		 * @param message
		 *            a message to write
		 * @param future
		 *            a future that needs to be notified when an operation is
		 *            finished
		 * @param destination
		 *            the destination of the message. This property will be
		 *            ignored unless the transport supports it.
		 */
		public WriteRequest(Object message, SocketAddress destination) {
			if (message == null) {
				throw new NullPointerException("message");
			}

			this.message = message;
			this.destination = destination;
		}

		/**
		 * Returns a message object to be written.
		 */
		public Object getMessage() {
			return message;
		}

		/**
		 * Returne the destination of this write request.
		 * 
		 * @return <tt>null</tt> for the default destination
		 */
		public SocketAddress getDestination() {
			return destination;
		}

		public String toString() {
			return message.toString();
		}
	}
}
