package ru.otus;

import org.junit.jupiter.api.Test;
import ru.otus.cell.Cell;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Denomination.*;

class AtmTest {

    @Test
    void init() {
        List<Denomination> expected = List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);
        Atm atm = new Atm(expected);
        List<Denomination> actual = atm.getCells().stream().map(Cell::getDenomination).collect(Collectors.toList());
        System.out.println(actual);
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void findCellTest() {
        List<Denomination> expected = List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND);
        Atm atm = new Atm(expected);

        Optional<Cell> actualOneHundred = atm.findCell(ONE_HUNDRED);
        assertEquals(ONE_HUNDRED, actualOneHundred.orElseThrow().getDenomination());

        assertThrows(
                NoSuchElementException.class,
                () -> {
                    Optional<Cell> noSuchCell = atm.findCell(FIVE_THOUSAND);
                    assertEquals(FIVE_THOUSAND, noSuchCell.orElseThrow().getDenomination());
                }
        );
    }

    @Test
    void takeMoney() {
        List<Denomination> expected = List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);
        Atm atm = new Atm(expected);

        Optional<Cell> oneHundredCell = atm.findCell(ONE_HUNDRED);
        assertEquals(0, oneHundredCell.orElseThrow().getAmount());
        Optional<Cell> fiveHundredCell = atm.findCell(FIVE_HUNDRED);
        assertEquals(0, fiveHundredCell.orElseThrow().getAmount());
        Optional<Cell> oneThousandCell = atm.findCell(ONE_THOUSAND);
        assertEquals(0, oneThousandCell.orElseThrow().getAmount());
        Optional<Cell> twoThousandCell = atm.findCell(TWO_THOUSAND);
        assertEquals(0, twoThousandCell.orElseThrow().getAmount());
        Optional<Cell> fiveThousandCell = atm.findCell(FIVE_THOUSAND);
        assertEquals(0, fiveThousandCell.orElseThrow().getAmount());

        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 1);
        atm.takeMoney(denominationMap);

        assertEquals(1, oneHundredCell.orElseThrow().getAmount());
        assertEquals(1, fiveHundredCell.orElseThrow().getAmount());
        assertEquals(1, oneThousandCell.orElseThrow().getAmount());
        assertEquals(1, twoThousandCell.orElseThrow().getAmount());
        assertEquals(1, fiveThousandCell.orElseThrow().getAmount());
    }

    @Test
    public void showCellBalanceTest() {
        List<Denomination> expected = List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);
        Atm atm = new Atm(expected);

        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 1);
        atm.takeMoney(denominationMap);

        assertEquals(8600, atm.showBalance());
    }


}