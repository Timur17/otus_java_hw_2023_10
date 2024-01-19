package ru.otus.service;

import java.util.Map;

public interface AtmService {
    int showBalance();

    void putMoney(Map<Integer, Integer> map);

    void getMoney(int amount);
}
