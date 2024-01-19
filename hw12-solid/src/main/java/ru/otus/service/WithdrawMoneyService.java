package ru.otus.service;

import java.util.Set;
import ru.otus.model.Cell;

public interface WithdrawMoneyService {

    void getMoney(Set<Cell> cells, int amount);
}
