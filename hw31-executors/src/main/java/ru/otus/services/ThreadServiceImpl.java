package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Counter;

public class ThreadServiceImpl implements ThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);
    private boolean isFirstThreadOrder = true;

    private final Counter counterFirst;
    private final Counter conterSecond;

    public ThreadServiceImpl(Counter counterFirst, Counter counterSecond) {
        this.counterFirst = counterFirst;
        this.conterSecond = counterSecond;
    }

    @Override
    public synchronized void actionFirstTread() {
        while (!isFirstThreadOrder) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                logger.warn("Error:", e);
                Thread.currentThread().interrupt();
            }
        }
        isFirstThreadOrder = false;
        counterFirst.executeLogic();
        logger.info("Count: {}", counterFirst.getValue());
        this.notifyAll();
    }

    @Override
    public synchronized void actionSecondTread() {
        while (isFirstThreadOrder) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                logger.warn("Error:", e);
                Thread.currentThread().interrupt();
            }
        }
        isFirstThreadOrder = true;
        conterSecond.executeLogic();
        logger.info("Count: {}", conterSecond.getValue());
        this.notifyAll();
    }
}
