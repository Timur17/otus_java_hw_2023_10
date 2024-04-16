package ru.otus.service.client;

import static ru.otus.ClientRunner.SERVER_HOST;
import static ru.otus.ClientRunner.SERVER_PORT;

import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.DigitServiceGrpc;
import ru.otus.protobuf.ValueRange;

public class GRPCClientService implements Runnable {
    private final AtomicLong generatedValue;
    private static final Logger log = LoggerFactory.getLogger(GRPCClientService.class);

    public GRPCClientService(AtomicLong generatedValue) {
        this.generatedValue = generatedValue;
    }

    @Override
    public void run() {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = DigitServiceGrpc.newBlockingStub(channel);

        log.warn("Server is ready");
        var digits = stub.generateDigits(
                ValueRange.newBuilder().setFirstValue(0).setLastValue(30).build());
        digits.forEachRemaining(d -> {
            log.info("new value: '{};", d.getGenValue());
            generatedValue.set(d.getGenValue());
        });
    }
}
