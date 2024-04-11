package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Counter;

public class ThreadServiceImpl implements ThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);

    @Override
    public synchronized void action(Counter counter, Counter counterAnother) {
        while (!counter.isOrder()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                logger.warn("Error:", e);
                Thread.currentThread().interrupt();
            }
        }
        counter.changeOrder();
        counterAnother.changeOrder();
        counter.executeLogic();
        logger.info("Count: {}", counter.getValue());
        this.notifyAll();
    }
}
