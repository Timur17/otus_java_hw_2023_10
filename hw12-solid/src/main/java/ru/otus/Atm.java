package ru.otus;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exception.MyOwnRuntimeException;
import ru.otus.model.Cell;
import ru.otus.model.Denomination;
import ru.otus.service.CalculateService;

@Data
public class Atm {
    private static final Logger logger = LoggerFactory.getLogger(Atm.class);

    private final Set<Cell> cells;

    private final CalculateService calculateService;

    private static Atm instance = null;

    public Atm(Set<Cell> cells, CalculateService calculateService) {
        this.cells = cells;
        this.calculateService = calculateService;
    }
    public int showBalance() {
        return cells.stream().mapToInt(Cell::showBalance).sum();
    }

    public void putMoney(Map<Denomination, Integer> map) {

        for (Map.Entry<Denomination, Integer> entry : map.entrySet()) {
            logger.info(
                    "Trying add amount money: '{}' to cell with denomination: '{}'", entry.getValue(), entry.getKey());
            Optional<Cell> optional = findCell(entry.getKey());

            optional.ifPresentOrElse(
                    cell -> {
                        logger.info("Balance before add '{}', ", cell.showBalance());
                        calculateService.putMoney(cell, entry.getValue());
                        logger.info("Balance after add '{}'", cell.showBalance());
                    },
                    () -> logger.error("Unknown denomination  '{}'", entry.getKey()));
        }
    }

    public void getMoney(int amount) {
        logger.info("Attempt to withdraw money: '{}'", amount);
        if (showBalance() >= amount) {
            calculateService.getMoney(cells, amount);
        } else {
            throw new MyOwnRuntimeException("Atm has not enough money. Atm balance: '{}', showBalance()");
        }
    }

    private Optional<Cell> findCell(Denomination denomination) {
        for (Cell cellImpl : cells) {
            if (cellImpl.getDenomination().equals(denomination)) {
                return Optional.of(cellImpl);
            }
        }
        return Optional.empty();
    }

}
