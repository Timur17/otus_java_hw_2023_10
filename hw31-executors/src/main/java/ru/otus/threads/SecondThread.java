package ru.otus.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.services.ThreadService;

public class SecondThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SecondThread.class);
    private final ThreadService threadService;
    private final String threadName;

    private final long timeout;

    public SecondThread(ThreadService threadService, long timeout, String threadName) {
        this.threadService = threadService;
        this.threadName = threadName;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        while (!Thread.currentThread().isInterrupted()) {
            threadService.actionSecondTread();
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                logger.info("Thread interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
