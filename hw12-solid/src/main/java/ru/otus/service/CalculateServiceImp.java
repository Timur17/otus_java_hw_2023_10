package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Cell;

import java.util.Set;

public class CalculateServiceImp implements CalculateService {

    private static final Logger logger = LoggerFactory.getLogger(CalculateServiceImp.class);

    @Override
    public void getMoney(Set<Cell> cells, int amount) {
        for (Cell cell : cells) {
            logger.info("Atm has '{}' denomination '{}'", cell.getAmount(), cell.getDenomination());
            logger.info("Remains to withdraw money: '{}'", amount);
            if (cell.getDenomination().getValue() <= amount && cell.showBalance() > 0) {
                int neededAmount = amount / cell.getDenomination().getValue();
                if (cell.getAmount() >= neededAmount) {
                    int sumToWithdraw = neededAmount * cell.getDenomination().getValue();
                    logger.info("Sum '{}' will be withdrawn by denomination '{}'", sumToWithdraw, cell.getDenomination());
                    amount = amount - sumToWithdraw;
                    cell.setAmount(cell.getAmount() - neededAmount);
                } else {
                    logger.info("Sum '{}' will be withdrawn by denomination '{}'", cell.showBalance(), cell.getDenomination());
                    amount = amount - cell.showBalance();
                    cell.setAmount(0);
                }
            }
        }
    }

    @Override
    public void putMoney(Cell cell, int amountToAdd) {
        cell.setAmount(cell.getAmount() + amountToAdd);
    }

}
