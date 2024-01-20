package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class DateTimeProviderImpl implements DateTimeProvider {
    private int second;

    @Override
    public void memorizeSecond() {
        second = LocalDateTime.now().getSecond();
    }

    @Override
    public boolean isEvenSecond() {
        return second % 2 == 0;
    }

    public int getSecond() {
        return second;
    }
}
