package study.nio.reactor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionMonitor {
	
	private final Logger log = LoggerFactory
            .getLogger(ExceptionMonitor.class);

	
	private static ExceptionMonitor instance = new ExceptionMonitor();

	/**
	 * Returns the current exception monitor.
	 */
	public static ExceptionMonitor getInstance() {
		return instance;
	}

	private ExceptionMonitor() {
		
	}
	public static void setInstance(ExceptionMonitor monitor) {
		if (monitor != null) {
			instance = monitor;
		}
	}

	public void exceptionCaught(Throwable cause) {
		if (log.isWarnEnabled()) {
			log.warn("Unexpected exception.", cause);
		}
	}
}
