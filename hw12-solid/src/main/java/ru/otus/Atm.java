package ru.otus;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cell.Cell;

import java.util.*;

@Data
public class Atm {
    private static final Logger logger = LoggerFactory.getLogger(Atm.class);
    private Set<Cell> cells = new HashSet<>();

    public Atm(List<Denomination> denominations) {
        denominations.forEach(denomination -> {
            Cell cell = new Cell(denomination);
            cells.add(cell);
        });
    }

    public Optional<Cell> findCell(Denomination denomination) {
        for (Cell cell : cells) {
            if (cell.getDenomination().equals(denomination)) {
                return Optional.of(cell);
            }
        }
        return Optional.empty();
    }

    public void takeMoney(Map<Denomination, Integer> map) {

        for (Map.Entry<Denomination, Integer> entry : map.entrySet()) {
            logger.info("Trying add amount money: '{}' to cell with denomination: '{}'",
                    entry.getValue(), entry.getKey());
            Optional<Cell> optinalCell = findCell(entry.getKey());

            optinalCell.ifPresent(cell -> {
                int newSumToAdd = entry.getValue();
                int currentSum = cell.getAmount();
                logger.info("Balance before add '{}', ", cell.getBalance());
                cell.setAmount(currentSum + newSumToAdd);
                logger.info("Balance after add '{}'", cell.getBalance());
            });
        }
    }

    public int showBalance(){
        return cells.stream().mapToInt(Cell::getBalance).sum();
    }

}
