package ru.otus.processor;

import java.time.LocalDateTime;
import ru.otus.model.Message;

public class ProcessorEvenError implements Processor {
    private int seconds;
    private RuntimeException runtimeException;

    @Override
    public Message process(Message message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        seconds = localDateTime.getSecond();
        if (seconds % 2 == 0) {
            runtimeException = new RuntimeException("Exception was thrown cause method was invoked in even second");
            throw runtimeException;
        }
        return message;
    }

    public int getSeconds() {
        return seconds;
    }

    public RuntimeException getRuntimeException() {
        return runtimeException;
    }
}
