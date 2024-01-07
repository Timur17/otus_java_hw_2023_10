package ru.otus.model;

import lombok.Getter;

@Getter
public enum Denomination {
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int value;

    Denomination(int denomination) {
        this.value = denomination;
    }
}
