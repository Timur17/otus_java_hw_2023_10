package ru.otus.model;

public enum Denomination {
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int denomination;

    Denomination(int denomination) {
        this.denomination = denomination;
    }

    public int getValue(){
        return this.denomination;
    }
}
