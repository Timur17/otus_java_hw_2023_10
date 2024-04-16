package ru.otus.service.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.DigitServiceGrpc;
import ru.otus.protobuf.GenValue;
import ru.otus.protobuf.ValueRange;

public class GenDigitService extends DigitServiceGrpc.DigitServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(GenDigitService.class);

    @Override
    public void generateDigits(ValueRange request, StreamObserver<GenValue> responseObserver) {
        for (long i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            responseObserver.onNext(GenValue.newBuilder().setGenValue(i).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("Error occurred: ", e);
                Thread.currentThread().interrupt();
            }
        }
        responseObserver.onCompleted();
    }
}
