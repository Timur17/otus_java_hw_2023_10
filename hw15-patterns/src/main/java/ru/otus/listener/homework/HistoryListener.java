package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private List<Message> messageList = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        messageList.add(msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        for (Message msg : messageList) {
            if (msg.getId() == id) {
                return Optional.of(msg);
            }
        }
        return Optional.empty();
    }
}
