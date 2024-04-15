package ru.otus;

import io.grpc.ServerBuilder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.server.GenDigitService;

public class ServerRunner {
    public static final int SERVER_PORT = 8191;
    private static final Logger log = LoggerFactory.getLogger(ServerRunner.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        GenDigitService service = new GenDigitService();
        var server = ServerBuilder.forPort(SERVER_PORT).addService(service).build();
        server.start();
        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
