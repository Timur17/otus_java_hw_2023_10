package ru.otus.service;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exception.MyOwnRuntimeException;
import ru.otus.model.Atm;
import ru.otus.model.Cell;

public class AtmServiceImpl implements AtmService {
    private static final Logger logger = LoggerFactory.getLogger(AtmServiceImpl.class);

    private final WithdrawMoneyService withdrawMoneyService;

    private final Atm atm;

    public AtmServiceImpl(Atm atm, WithdrawMoneyService withdrawMoneyService) {
        this.atm = atm;
        this.withdrawMoneyService = withdrawMoneyService;
    }

    @Override
    public int showBalance() {
        return atm.getCells().stream().mapToInt(Cell::showBalance).sum();
    }

    @Override
    public void putMoney(Map<Integer, Integer> map) {

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            logger.info(
                    "Trying add amount money: '{}' to cell with denomination: '{}'", entry.getValue(), entry.getKey());
            Optional<Cell> optional = findCell(entry.getKey());

            optional.ifPresentOrElse(
                    cell -> {
                        logger.info("Balance before add '{}', ", cell.showBalance());
                        cell.increaseAmount(entry.getValue());
                        logger.info("Balance after add '{}'", cell.showBalance());
                    },
                    () -> logger.error("Unknown denomination  '{}'", entry.getKey()));
        }
    }

    @Override
    public void getMoney(int amount) {
        logger.info("Attempt to withdraw money: '{}'", amount);
        if (showBalance() >= amount) {
            withdrawMoneyService.getMoney(atm.getCells(), amount);
        } else {
            throw new MyOwnRuntimeException("Atm has not enough money. Atm balance: '{}', showBalance()");
        }
    }

    private Optional<Cell> findCell(int denomination) {
        for (Cell cellImpl : atm.getCells()) {
            if (cellImpl.getDenomination() == denomination) {
                return Optional.of(cellImpl);
            }
        }
        return Optional.empty();
    }
}
