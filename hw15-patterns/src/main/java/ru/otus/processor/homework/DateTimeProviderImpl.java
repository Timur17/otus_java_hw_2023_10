package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class DateTimeProviderImpl implements DateTimeProvider {
    private LocalDateTime localDateTime;

    @Override
    public LocalDateTime getDate() {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
            return LocalDateTime.now();
        }
        return localDateTime;
    }
}
