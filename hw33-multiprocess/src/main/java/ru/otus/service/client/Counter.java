package ru.otus.service.client;

import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {
    private static final Logger log = LoggerFactory.getLogger(Counter.class);
    private final AtomicLong generatedValue;

    private long currentValue = 0;

    public Counter(AtomicLong generatedValue) {
        this.generatedValue = generatedValue;
    }

    public void compute() {
        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + generatedValue.getAndSet(0) + 1;
            log.info("currentValue: '{}'", currentValue);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
