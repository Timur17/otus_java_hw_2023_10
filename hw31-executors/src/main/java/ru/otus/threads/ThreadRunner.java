package ru.otus.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Counter;
import ru.otus.services.ThreadService;

public class ThreadRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ThreadRunner.class);
    private final ThreadService threadService;
    private final String threadName;

    private final long timeout;

    private final Counter counter;

    private final Counter counterAnother;

    public ThreadRunner(
            ThreadService threadService, long timeout, String threadName, Counter counter, Counter counterAnother) {
        this.threadService = threadService;
        this.threadName = threadName;
        this.timeout = timeout;
        this.counter = counter;
        this.counterAnother = counterAnother;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        while (!Thread.currentThread().isInterrupted()) {
            threadService.action(counter, counterAnother);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                logger.info("Thread interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
