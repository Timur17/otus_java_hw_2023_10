package ru.otus;

import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.client.Counter;
import ru.otus.service.client.GRPCClientService;

public class ClientRunner {
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8191;
    private static final Logger log = LoggerFactory.getLogger(ClientRunner.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("numbers Client is starting...");
        final AtomicLong generatedValue = new AtomicLong(0);

        GRPCClientService client = new GRPCClientService(generatedValue);
        Thread clientThread = new Thread(client);

        Counter counter = new Counter(generatedValue);
        clientThread.start();
        counter.compute();
        clientThread.join();
    }
}
