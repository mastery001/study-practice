package study.nio.reactor;

public class FilterAdapter implements Filter {
    public void init() throws Exception {
    }

    public void destroy() throws Exception {
    }

    public void onPreAdd(FilterChain parent, String name,
            NextFilter nextFilter) throws Exception {
    }

    public void onPostAdd(FilterChain parent, String name,
            NextFilter nextFilter) throws Exception {
    }

    public void onPreRemove(FilterChain parent, String name,
            NextFilter nextFilter) throws Exception {
    }

    public void onPostRemove(FilterChain parent, String name,
            NextFilter nextFilter) throws Exception {
    }

    public void sessionCreated(NextFilter nextFilter, Session session)
            throws Exception {
        nextFilter.sessionCreated(session);
    }

    public void sessionOpened(NextFilter nextFilter, Session session)
            throws Exception {
        nextFilter.sessionOpened(session);
    }

    public void sessionClosed(NextFilter nextFilter, Session session)
            throws Exception {
        nextFilter.sessionClosed(session);
    }

    public void exceptionCaught(NextFilter nextFilter, Session session,
            Throwable cause) throws Exception {
        nextFilter.exceptionCaught(session, cause);
    }

    public void messageReceived(NextFilter nextFilter, Session session,
            Object message) throws Exception {
        nextFilter.messageReceived(session, message);
    }

    public void messageSent(NextFilter nextFilter, Session session,
            Object message) throws Exception {
        nextFilter.messageSent(session, message);
    }

    public void filterWrite(NextFilter nextFilter, Session session,
            WriteRequest writeRequest) throws Exception {
        nextFilter.filterWrite(session, writeRequest);
    }

    public void filterClose(NextFilter nextFilter, Session session)
            throws Exception {
        nextFilter.filterClose(session);
    }
}
