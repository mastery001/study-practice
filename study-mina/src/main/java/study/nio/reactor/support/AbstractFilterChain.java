package study.nio.reactor.support;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import study.nio.reactor.Filter;
import study.nio.reactor.Filter.NextFilter;
import study.nio.reactor.Filter.WriteRequest;
import study.nio.reactor.FilterAdapter;
import study.nio.reactor.FilterChain;
import study.nio.reactor.FilterLifeCycleException;
import study.nio.reactor.Session;
import study.nio.reactor.util.ByteBufferUtil;
import study.nio.reactor.util.SessionLog;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractFilterChain implements FilterChain {

	public static final String CONNECT_FUTURE = AbstractFilterChain.class.getName() + ".connectFuture";

	private final Session session;

	private final Map name2entry = new HashMap();

	private final EntryImpl head;

	private final EntryImpl tail;

	protected AbstractFilterChain(Session session) {
		if (session == null) {
			throw new NullPointerException("session");
		}

		this.session = session;
		head = new EntryImpl(null, null, "head", new HeadFilter());
		tail = new EntryImpl(head, null, "tail", new TailFilter());
		head.nextEntry = tail;
	}

	public Session getSession() {
		return session;
	}

	public Entry getEntry(String name) {
		Entry e = (Entry) name2entry.get(name);
		if (e == null) {
			return null;
		}
		return e;
	}

	public Filter get(String name) {
		Entry e = getEntry(name);
		if (e == null) {
			return null;
		}

		return e.getFilter();
	}

	public NextFilter getNextFilter(String name) {
		Entry e = getEntry(name);
		if (e == null) {
			return null;
		}

		return e.getNextFilter();
	}

	public synchronized void addFirst(String name, Filter filter) {
		checkAddable(name);
		register(head, name, filter);
	}

	public synchronized void addLast(String name, Filter filter) {
		checkAddable(name);
		register(tail.prevEntry, name, filter);
	}

	public synchronized void addBefore(String baseName, String name, Filter filter) {
		EntryImpl baseEntry = checkOldName(baseName);
		checkAddable(name);
		register(baseEntry.prevEntry, name, filter);
	}

	public synchronized void addAfter(String baseName, String name, Filter filter) {
		EntryImpl baseEntry = checkOldName(baseName);
		checkAddable(name);
		register(baseEntry, name, filter);
	}

	public synchronized Filter remove(String name) {
		EntryImpl entry = checkOldName(name);
		deregister(entry);
		return entry.getFilter();
	}

	public synchronized void clear() throws Exception {
		Iterator it = new ArrayList(name2entry.keySet()).iterator();
		while (it.hasNext()) {
			this.remove((String) it.next());
		}
	}

	private void register(EntryImpl prevEntry, String name, Filter filter) {
		EntryImpl newEntry = new EntryImpl(prevEntry, prevEntry.nextEntry, name, filter);

		try {
			filter.onPreAdd(this, name, newEntry.getNextFilter());
		} catch (Exception e) {
			throw new FilterLifeCycleException("onPreAdd(): " + name + ':' + filter + " in " + getSession(), e);
		}

		prevEntry.nextEntry.prevEntry = newEntry;
		prevEntry.nextEntry = newEntry;
		name2entry.put(name, newEntry);

		try {
			filter.onPostAdd(this, name, newEntry.getNextFilter());
		} catch (Exception e) {
			deregister0(newEntry);
			throw new FilterLifeCycleException("onPostAdd(): " + name + ':' + filter + " in " + getSession(), e);
		}
	}

	private void deregister(EntryImpl entry) {
		Filter filter = entry.getFilter();

		try {
			filter.onPreRemove(this, entry.getName(), entry.getNextFilter());
		} catch (Exception e) {
			throw new FilterLifeCycleException(
					"onPreRemove(): " + entry.getName() + ':' + filter + " in " + getSession(), e);
		}

		deregister0(entry);

		try {
			filter.onPostRemove(this, entry.getName(), entry.getNextFilter());
		} catch (Exception e) {
			throw new FilterLifeCycleException(
					"onPostRemove(): " + entry.getName() + ':' + filter + " in " + getSession(), e);
		}
	}

	private void deregister0(EntryImpl entry) {
		EntryImpl prevEntry = entry.prevEntry;
		EntryImpl nextEntry = entry.nextEntry;
		prevEntry.nextEntry = nextEntry;
		nextEntry.prevEntry = prevEntry;

		name2entry.remove(entry.name);
	}

	/**
	 * Throws an exception when the specified filter name is not registered in
	 * this chain.
	 *
	 * @return An filter entry with the specified name.
	 */
	private EntryImpl checkOldName(String baseName) {
		EntryImpl e = (EntryImpl) name2entry.get(baseName);
		if (e == null) {
			throw new IllegalArgumentException("Unknown filter name:" + baseName);
		}
		return e;
	}

	/**
	 * Checks the specified filter name is already taken and throws an exception
	 * if already taken.
	 */
	private void checkAddable(String name) {
		if (name2entry.containsKey(name)) {
			throw new IllegalArgumentException("Other filter is using the same name '" + name + "'");
		}
	}

	public void fireSessionCreated(Session session) {
		Entry head = this.head;
		callNextSessionCreated(head, session);
	}

	private void callNextSessionCreated(Entry entry, Session session) {
		try {
			entry.getFilter().sessionCreated(entry.getNextFilter(), session);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireSessionOpened(Session session) {
		Entry head = this.head;
		callNextSessionOpened(head, session);
	}

	private void callNextSessionOpened(Entry entry, Session session) {
		try {
			entry.getFilter().sessionOpened(entry.getNextFilter(), session);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireSessionClosed(Session session) {

		// And start the chain.
		Entry head = this.head;
		callNextSessionClosed(head, session);
	}

	private void callNextSessionClosed(Entry entry, Session session) {
		try {
			entry.getFilter().sessionClosed(entry.getNextFilter(), session);

		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireMessageReceived(Session session, Object message) {
		Entry head = this.head;
		callNextMessageReceived(head, session, message);
	}

	private void callNextMessageReceived(Entry entry, Session session, Object message) {
		try {
			entry.getFilter().messageReceived(entry.getNextFilter(), session, message);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireMessageSent(Session session, WriteRequest request) {

		Entry head = this.head;
		callNextMessageSent(head, session, request.getMessage());
	}

	private void callNextMessageSent(Entry entry, Session session, Object message) {
		try {
			entry.getFilter().messageSent(entry.getNextFilter(), session, message);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireExceptionCaught(Session session, Throwable cause) {
		Entry head = this.head;
		callNextExceptionCaught(head, session, cause);
	}

	private void callNextExceptionCaught(Entry entry, Session session, Throwable cause) {
		try {
			entry.getFilter().exceptionCaught(entry.getNextFilter(), session, cause);
		} catch (Throwable e) {
			SessionLog.warn(session, "Unexpected exception from exceptionCaught handler.", e);
		}
	}

	public void fireFilterWrite(Session session, WriteRequest writeRequest) {
		Entry tail = this.tail;
		callPreviousFilterWrite(tail, session, writeRequest);
	}

	private void callPreviousFilterWrite(Entry entry, Session session, WriteRequest writeRequest) {
		try {
			entry.getFilter().filterWrite(entry.getNextFilter(), session, writeRequest);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public void fireFilterClose(Session session) {
		Entry tail = this.tail;
		callPreviousFilterClose(tail, session);
	}

	private void callPreviousFilterClose(Entry entry, Session session) {
		try {
			entry.getFilter().filterClose(entry.getNextFilter(), session);
		} catch (Throwable e) {
			fireExceptionCaught(session, e);
		}
	}

	public List getAll() {
		List list = new ArrayList();
		EntryImpl e = head.nextEntry;
		while (e != tail) {
			list.add(e);
			e = e.nextEntry;
		}

		return list;
	}

	public List getAllReversed() {
		List list = new ArrayList();
		EntryImpl e = tail.prevEntry;
		while (e != head) {
			list.add(e);
			e = e.prevEntry;
		}
		return list;
	}

	public boolean contains(String name) {
		return getEntry(name) != null;
	}

	public boolean contains(Filter filter) {
		EntryImpl e = head.nextEntry;
		while (e != tail) {
			if (e.getFilter() == filter) {
				return true;
			}
			e = e.nextEntry;
		}
		return false;
	}

	public boolean contains(Class filterType) {
		EntryImpl e = head.nextEntry;
		while (e != tail) {
			if (filterType.isAssignableFrom(e.getFilter().getClass())) {
				return true;
			}
			e = e.nextEntry;
		}
		return false;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{ ");

		boolean empty = true;

		EntryImpl e = head.nextEntry;
		while (e != tail) {
			if (!empty) {
				buf.append(", ");
			} else {
				empty = false;
			}

			buf.append('(');
			buf.append(e.getName());
			buf.append(':');
			buf.append(e.getFilter());
			buf.append(')');

			e = e.nextEntry;
		}

		if (empty) {
			buf.append("empty");
		}

		buf.append(" }");

		return buf.toString();
	}

	protected void finalize() throws Throwable {
		try {
			this.clear();
		} finally {
			super.finalize();
		}
	}

	protected abstract void doWrite(Session session, WriteRequest writeRequest) throws Exception;

	protected abstract void doClose(Session session) throws Exception;

	private class HeadFilter extends FilterAdapter {
		public void sessionCreated(NextFilter nextFilter, Session session) {
			nextFilter.sessionCreated(session);
		}

		public void sessionOpened(NextFilter nextFilter, Session session) {
			nextFilter.sessionOpened(session);
		}

		public void sessionClosed(NextFilter nextFilter, Session session) {
			nextFilter.sessionClosed(session);
		}

		public void exceptionCaught(NextFilter nextFilter, Session session, Throwable cause) {
			nextFilter.exceptionCaught(session, cause);
		}

		public void messageReceived(NextFilter nextFilter, Session session, Object message) {
			nextFilter.messageReceived(session, message);
		}

		public void messageSent(NextFilter nextFilter, Session session, Object message) {
			nextFilter.messageSent(session, message);
		}

		public void filterWrite(NextFilter nextFilter, Session session, WriteRequest writeRequest) throws Exception {
			if (writeRequest.getMessage() instanceof ByteBuffer) {
				doWrite(session, writeRequest);
			} else {
				throw new IllegalStateException(
						"Write requests must be transformed to ByteBuffer" + ": " + writeRequest);
			}

		}

		public void filterClose(NextFilter nextFilter, Session session) throws Exception {
			doClose(session);
		}
	}

	private static class TailFilter extends FilterAdapter {
		public void sessionCreated(NextFilter nextFilter, Session session) throws Exception {
			session.getHandler().sessionCreated(session);
		}

		public void sessionOpened(NextFilter nextFilter, Session session) throws Exception {
			session.getHandler().sessionOpened(session);
		}

		public void sessionClosed(NextFilter nextFilter, Session session) throws Exception {
			try {
				session.getHandler().sessionClosed(session);
			} finally {
				// Remove all filters.
				session.getFilterChain().clear();
			}
		}

		public void exceptionCaught(NextFilter nextFilter, Session session, Throwable cause) throws Exception {
			session.getHandler().exceptionCaught(session, cause);
		}

		public void messageReceived(NextFilter nextFilter, Session session, Object message) throws Exception {
			try {
				session.getHandler().messageReceived(session, message);
			} finally {
				ByteBufferUtil.clearIfPossible(message);
			}
		}

		public void messageSent(NextFilter nextFilter, Session session, Object message) throws Exception {
			try {
				session.getHandler().messageSent(session, message);
			} finally {
				ByteBufferUtil.clearIfPossible(message);
			}
		}

		public void filterWrite(NextFilter nextFilter, Session session, WriteRequest writeRequest) throws Exception {
			nextFilter.filterWrite(session, writeRequest);
		}

		public void filterClose(NextFilter nextFilter, Session session) throws Exception {
			nextFilter.filterClose(session);
		}
	}

	private class EntryImpl implements Entry {
		private EntryImpl prevEntry;

		private EntryImpl nextEntry;

		private final String name;

		private final Filter filter;

		private final NextFilter nextFilter;

		private EntryImpl(EntryImpl prevEntry, EntryImpl nextEntry, String name, Filter filter) {
			if (filter == null) {
				throw new NullPointerException("filter");
			}
			if (name == null) {
				throw new NullPointerException("name");
			}

			this.prevEntry = prevEntry;
			this.nextEntry = nextEntry;
			this.name = name;
			this.filter = filter;
			this.nextFilter = new NextFilter() {
				public void sessionCreated(Session session) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextSessionCreated(nextEntry, session);
				}

				public void sessionOpened(Session session) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextSessionOpened(nextEntry, session);
				}

				public void sessionClosed(Session session) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextSessionClosed(nextEntry, session);
				}

				public void exceptionCaught(Session session, Throwable cause) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextExceptionCaught(nextEntry, session, cause);
				}

				public void messageReceived(Session session, Object message) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextMessageReceived(nextEntry, session, message);
				}

				public void messageSent(Session session, Object message) {
					Entry nextEntry = EntryImpl.this.nextEntry;
					callNextMessageSent(nextEntry, session, message);
				}

				public void filterWrite(Session session, WriteRequest writeRequest) {
					Entry nextEntry = EntryImpl.this.prevEntry;
					callPreviousFilterWrite(nextEntry, session, writeRequest);
				}

				public void filterClose(Session session) {
					Entry nextEntry = EntryImpl.this.prevEntry;
					callPreviousFilterClose(nextEntry, session);
				}
			};
		}

		public String getName() {
			return name;
		}

		public Filter getFilter() {
			return filter;
		}

		public NextFilter getNextFilter() {
			return nextFilter;
		}

		public String toString() {
			return "(" + getName() + ':' + filter + ')';
		}
	}
}
