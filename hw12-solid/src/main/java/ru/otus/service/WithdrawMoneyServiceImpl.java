package ru.otus.service;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Cell;

public class WithdrawMoneyServiceImpl implements WithdrawMoneyService {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawMoneyServiceImpl.class);

    @Override
    public void getMoney(Set<Cell> cells, int amount) {
        for (Cell cell : cells) {
            logger.info("Atm has '{}' denomination '{}'", cell.getAmount(), cell.getDenomination());
            logger.info("Remains to withdraw money: '{}'", amount);
            if (cell.getDenomination() <= amount && cell.showBalance() > 0) {
                int neededAmount = amount / cell.getDenomination();
                if (cell.getAmount() >= neededAmount) {
                    int sumToWithdraw = neededAmount * cell.getDenomination();
                    logger.info(
                            "Sum '{}' will be withdrawn by denomination '{}'", sumToWithdraw, cell.getDenomination());
                    amount = amount - sumToWithdraw;
                    cell.reduceAmount(neededAmount);
                } else {
                    logger.info(
                            "Sum '{}' will be withdrawn by denomination '{}'",
                            cell.showBalance(),
                            cell.getDenomination());
                    amount = amount - cell.showBalance();
                    cell.setAmountZero();
                }
            }
        }
    }
}
