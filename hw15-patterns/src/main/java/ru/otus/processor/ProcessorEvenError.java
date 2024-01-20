package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

public class ProcessorEvenError implements Processor {
    private RuntimeException runtimeException;

    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenError(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        dateTimeProvider.memorizeSecond();
        if (dateTimeProvider.isEvenSecond()) {
            runtimeException = new RuntimeException("Exception was thrown cause method was invoked in even second");
            throw runtimeException;
        }
        return message;
    }

    public RuntimeException getRuntimeException() {
        return runtimeException;
    }
}
