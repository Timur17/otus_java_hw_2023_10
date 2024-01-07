package ru.otus.cell;

public interface Cell {
    int getBalance();

    int getMoney(int amount);

    void putMoney(int amountToAdd);
}
