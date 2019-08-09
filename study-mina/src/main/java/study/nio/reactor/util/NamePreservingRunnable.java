package study.nio.reactor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamePreservingRunnable implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(NamePreservingRunnable.class);

    private final String newName;
    private final Runnable runnable;

    public NamePreservingRunnable(Runnable runnable, String newName) {
        this.runnable = runnable;
        this.newName = newName;
    }

    public void run() {
        Thread currentThread = Thread.currentThread();
        String oldName = currentThread.getName();
        
        if (newName != null) {
            setName(currentThread, newName);
        }

        try {
            runnable.run();
        } finally {
            setName(currentThread, oldName);
        }
    }
    
    private void setName(Thread thread, String name) {
        try {
            thread.setName(name);
        } catch (Exception e) {
            // Probably SecurityException.
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to set the thread name.", e);
            }
        }
    }
}
