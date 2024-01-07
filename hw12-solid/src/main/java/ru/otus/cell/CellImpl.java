package ru.otus.cell;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.Denomination;

@Data
public class CellImpl implements Cell {
    private static final Logger logger = LoggerFactory.getLogger(CellImpl.class);
    private final Denomination denomination;
    private int amount;

    public int getBalance(){
        return amount * denomination.getValue();
    }

    @Override
    public int getMoney(int amount) {
        if (getDenomination().getValue() <= amount && getBalance() > 0) {
            int neededAmount = amount / getDenomination().getValue();
            if (getAmount() >= neededAmount) {
                int sumToWithdraw = neededAmount * getDenomination().getValue();
                logger.info("Sum '{}' will be withdrawn by denomination '{}'", sumToWithdraw, getDenomination());
                setAmount(getAmount() - neededAmount);
                return amount - sumToWithdraw;
            } else {
                logger.info("Sum '{}' will be withdrawn by denomination '{}'", getBalance(), getDenomination());
                setAmount(0);
                return amount - getBalance();
            }
        }
        return amount;
    }

    @Override
    public void putMoney(int amountToAdd) {
        setAmount(amount + amountToAdd);
    }

}
