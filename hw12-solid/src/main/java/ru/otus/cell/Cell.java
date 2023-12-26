package ru.otus.cell;

import lombok.Data;
import ru.otus.Denomination;

@Data
public class Cell {
    private final Denomination denomination;
    private int amount;

    public int getBalance(){
        return amount * denomination.getValue();
    }

}
