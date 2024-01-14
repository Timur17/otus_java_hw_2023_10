package ru.otus.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Cell {
    private final int denomination;
    @Setter(AccessLevel.NONE)
    private int amount;

    public int showBalance() {
        return amount * denomination;
    }
}
