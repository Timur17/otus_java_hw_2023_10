package ru.otus.service;

import java.util.Set;
import ru.otus.model.Cell;

public interface CalculateService {

    void getMoney(Set<Cell> cells, int amount);

    void putMoney(Cell cell, int amountToAdd);
}
