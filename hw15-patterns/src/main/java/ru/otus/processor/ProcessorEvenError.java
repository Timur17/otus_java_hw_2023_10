package ru.otus.processor;

import java.time.LocalDateTime;
import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

public class ProcessorEvenError implements Processor {
    private RuntimeException runtimeException;
    private DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        dateTimeProvider = LocalDateTime::now;
        if (isEvenSecond()) {
            runtimeException = new RuntimeException("Exception was thrown cause method was invoked in even second");
            throw runtimeException;
        }
        return message;
    }

    public int getSeconds() {
        return dateTimeProvider.getDate().getSecond();
    }

    public boolean isEvenSecond() {
        return dateTimeProvider.getDate().getSecond() % 2 == 0;
    }

    public RuntimeException getRuntimeException() {
        return runtimeException;
    }
}
