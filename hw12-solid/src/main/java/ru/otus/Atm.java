package ru.otus;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cell.CellImpl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
public class Atm {
    private static final Logger logger = LoggerFactory.getLogger(Atm.class);

    private final Set<CellImpl> cell;

    public Atm(Set<CellImpl> cell) {
        this.cell = cell;
    }

    public Optional<CellImpl> findCell(Denomination denomination) {
        for (CellImpl cellImpl : cell) {
            if (cellImpl.getDenomination().equals(denomination)) {
                return Optional.of(cellImpl);
            }
        }
        return Optional.empty();
    }

    public void putMoney(Map<Denomination, Integer> map) {

        for (Map.Entry<Denomination, Integer> entry : map.entrySet()) {
            logger.info("Trying add amount money: '{}' to cell with denomination: '{}'",
                    entry.getValue(), entry.getKey());
            Optional<CellImpl> optinalCell = findCell(entry.getKey());

            optinalCell.ifPresentOrElse(cell -> {
                logger.info("Balance before add '{}', ", cell.getBalance());
                cell.putMoney(entry.getValue());
                logger.info("Balance after add '{}'", cell.getBalance());
            }, () -> logger.error("Unknown denomination  '{}'", entry.getKey()));
        }
    }

    public int showBalance() {
        return cell.stream().mapToInt(CellImpl::getBalance).sum();
    }

    public void getMoney(int amount) {
        logger.info("Attempt to withdraw money: '{}'", amount);
        if (showBalance() >= amount) {
            for (CellImpl cellImpl : cell) {
                logger.info("Atm has '{}' denomination '{}'", cellImpl.getAmount(), cellImpl.getDenomination());
                logger.info("Remains to withdraw money: '{}'", amount);
                amount = cellImpl.getMoney(amount);
//                logger.info("Atm has '{}' denomination '{}'", cell.getAmount(), cell.getDenomination());
//                logger.info("Remains to withdraw money: '{}'", amount);
//                if (cell.getDenomination().getValue() <= amount && cell.getBalance() > 0) {
//                    int neededAmount = amount / cell.getDenomination().getValue();
//                    if (cell.getAmount() >= neededAmount) {
//                        int sumToWithdraw = neededAmount * cell.getDenomination().getValue();
//                        logger.info("Sum '{}' will be withdrawn by denomination '{}'", sumToWithdraw, cell.getDenomination());
//                        amount = amount - sumToWithdraw;
//                        cell.setAmount(cell.getAmount() - neededAmount);
//                    } else {
//                        logger.info("Sum '{}' will be withdrawn by denomination '{}'", cell.getBalance(), cell.getDenomination());
//                        amount = amount - cell.getBalance();
//                        cell.setAmount(0);
//                    }
//                }
            }
        } else {
            throw new RuntimeException("Atm has not enough money. Atm balance: '{}', showBalance()");
        }
    }
}
