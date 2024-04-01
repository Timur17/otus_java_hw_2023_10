package ru.otus.services;

import ru.otus.model.Counter;

public interface ThreadService {
    void action(Counter counter, Counter counterAnother);
}
