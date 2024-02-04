package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorChangeFields implements Processor {
    @Override
    public Message process(Message message) {
        String copyField11 = message.getField11();
        String copyField12 = message.getField12();
        return message.toBuilder().field11(copyField12).field12(copyField11).build();
    }
}
