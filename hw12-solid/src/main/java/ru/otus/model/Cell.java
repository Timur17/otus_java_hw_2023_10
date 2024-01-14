package ru.otus.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Cell {
    private final int denomination;
    private int amount;

    public int increaseAmount(int value) {
        amount = amount + value;
        return amount;
    }

    public int reduceAmount(int value) {
        amount = amount - value;
        return amount;
    }

    public int setAmountZero() {
        amount = 0;
        return 0;
    }

    public int showBalance() {
        return amount * denomination;
    }
}
