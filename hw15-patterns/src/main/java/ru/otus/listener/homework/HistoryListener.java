package ru.otus.listener.homework;

import java.util.*;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messageMap.put(msg.getId(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messageMap.get(id));
    }
}
