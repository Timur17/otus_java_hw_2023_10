package ru.otus.service;

import ru.otus.model.Cell;

import java.util.Set;

public interface CalculateService {

    void getMoney(Set<Cell> cells, int amount);

    void putMoney(Cell cell, int amountToAdd);
}
