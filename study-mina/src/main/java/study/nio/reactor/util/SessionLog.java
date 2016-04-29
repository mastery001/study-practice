package study.nio.reactor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.nio.reactor.Session;

public class SessionLog {
    /**
     * Session attribute key: prefix string
     */
    public static final String PREFIX = SessionLog.class.getName() + ".prefix";

    /**
     * Session attribute key: {@link Logger}
     */
    public static final String LOGGER = SessionLog.class.getName() + ".logger";

    private static Class<?> getClass(Session session) {
        return session.getHandler().getClass();
    }

    public static void debug(Session session, String message) {
        Logger log = getLogger(session);
        if (log.isDebugEnabled()) {
            log.debug(String.valueOf(session.getAttribute(PREFIX)) + message);
        }
    }

    public static void debug(Session session, String message, Throwable cause) {
        Logger log = getLogger(session);
        if (log.isDebugEnabled()) {
            log.debug(String.valueOf(session.getAttribute(PREFIX)) + message,
                    cause);
        }
    }

    public static void info(Session session, String message) {
        Logger log = getLogger(session);
        if (log.isInfoEnabled()) {
            log.info(String.valueOf(session.getAttribute(PREFIX)) + message);
        }
    }

    public static void info(Session session, String message, Throwable cause) {
        Logger log = getLogger(session);
        if (log.isInfoEnabled()) {
            log.info(String.valueOf(session.getAttribute(PREFIX)) + message,
                    cause);
        }
    }

    public static void warn(Session session, String message) {
        Logger log = getLogger(session);
        if (log.isWarnEnabled()) {
            log.warn(String.valueOf(session.getAttribute(PREFIX)) + message);
        }
    }

    public static void warn(Session session, String message, Throwable cause) {
        Logger log = getLogger(session);
        if (log.isWarnEnabled()) {
            log.warn(String.valueOf(session.getAttribute(PREFIX)) + message,
                    cause);
        }
    }

    public static void error(Session session, String message) {
        Logger log = getLogger(session);
        if (log.isErrorEnabled()) {
            log.error(String.valueOf(session.getAttribute(PREFIX)) + message);
        }
    }

    public static void error(Session session, String message, Throwable cause) {
        Logger log = getLogger(session);
        if (log.isErrorEnabled()) {
            log.error(String.valueOf(session.getAttribute(PREFIX)) + message,
                    cause);
        }
    }

    public static boolean isDebugEnabled(Session session) {
        return getLogger(session).isDebugEnabled();
    }

    public static boolean isInfoEnabled(Session session) {
        return getLogger(session).isInfoEnabled();
    }

    public static boolean isWarnEnabled(Session session) {
        return getLogger(session).isWarnEnabled();
    }

    public static boolean isErrorEnabled(Session session) {
        return getLogger(session).isErrorEnabled();
    }

    private static Logger getLogger(Session session) {
        Logger log = (Logger) session.getAttribute(LOGGER);
        if (log == null) {
            log = LoggerFactory.getLogger(getClass(session));
            String prefix = (String) session.getAttribute(PREFIX);
            if (prefix == null) {
                prefix = "[" + session.getRemoteAddress() + "] ";
                session.setAttribute(PREFIX, prefix);
            }

            session.setAttribute(LOGGER, log);
        }

        return log;
    }
}
