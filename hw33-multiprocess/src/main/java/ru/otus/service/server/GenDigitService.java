package ru.otus.service.server;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.DigitServiceGrpc;
import ru.otus.protobuf.GenValue;
import ru.otus.protobuf.ValueRange;

public class GenDigitService extends DigitServiceGrpc.DigitServiceImplBase {

    @Override
    public void generateDigits(ValueRange request, StreamObserver<GenValue> responseObserver) {
        for (long i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            responseObserver.onNext(GenValue.newBuilder().setGenValue(i).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
}
