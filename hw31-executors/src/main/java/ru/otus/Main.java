package ru.otus;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Counter;
import ru.otus.services.ThreadService;
import ru.otus.services.ThreadServiceImpl;
import ru.otus.threads.FirstThread;
import ru.otus.threads.SecondThread;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        long timeoutForLoop = 500;
        long shutdownTimeout = 12;
        ThreadService threadService = new ThreadServiceImpl();
        Counter firstCounter = new Counter(true);
        Counter secondCounter = new Counter(false);
        var executor = Executors.newFixedThreadPool(2);
        executor.submit(new FirstThread(threadService, timeoutForLoop, "FIRST", firstCounter, secondCounter));
        executor.submit(new SecondThread(threadService, timeoutForLoop, "SECOND", secondCounter, firstCounter));

        try {
            if (!executor.awaitTermination(shutdownTimeout, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            logger.warn("Error: ", ex);
            Thread.currentThread().interrupt();
        } finally {
            executor.close();
        }
    }
}
