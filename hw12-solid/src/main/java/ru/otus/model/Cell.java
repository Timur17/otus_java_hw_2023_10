package ru.otus.model;

import lombok.Data;

@Data
public class Cell {
    private final Denomination denomination;
    private int amount;

    public int showBalance() {
        return amount * denomination.getValue();
    }
}
