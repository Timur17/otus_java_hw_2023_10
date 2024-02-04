package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class DateTimeProviderImpl implements DateTimeProvider {

    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
