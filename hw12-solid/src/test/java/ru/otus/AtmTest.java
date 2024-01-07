package ru.otus;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.model.Denomination.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Cell;
import ru.otus.model.Denomination;
import ru.otus.service.CalculateServiceImp;

class AtmTest {
    private Set<Cell> cells;
    private final List<Denomination> expected =
            List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);

    private Atm atm;

    @BeforeEach
    void setUp() {
        cells = new TreeSet<>((o1, o2) ->
                o2.getDenomination().getValue() - o1.getDenomination().getValue());
        expected.forEach(denomination -> cells.add(new Cell(denomination)));
        atm = Atm.getInstance(cells, new CalculateServiceImp());
    }

    @Test
    void initAtmTest() {
        List<Denomination> actual =
                atm.getCells().stream().map(Cell::getDenomination).toList();
        assertTrue(expected.containsAll(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void putMoneyTest() {

        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 1);
        atm.putMoney(denominationMap);

        assertEquals(8600, atm.showBalance());
    }

    @Test
    void putMoneyTest2() {
        atm.getCells().remove(new Cell(ONE_HUNDRED));

        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        atm.putMoney(denominationMap);

        assertEquals(0, atm.showBalance());
    }

    @Test
    void getMoneyMoreThanHasAtmTest() {
        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 2);
        atm.putMoney(denominationMap);

        assertEquals(13600, atm.showBalance());

        assertEquals(13600, atm.showBalance());
        assertThrows(RuntimeException.class, () -> atm.getMoney(14000));
    }

    @Test
    void getMoneyEachDenominationOnceTest() {
        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        denominationMap.put(FIVE_HUNDRED, 2);
        denominationMap.put(ONE_THOUSAND, 2);
        denominationMap.put(TWO_THOUSAND, 2);
        denominationMap.put(FIVE_THOUSAND, 2);
        atm.putMoney(denominationMap);

        assertEquals(17200, atm.showBalance());

        atm.getMoney(8600);

        assertEquals(8600, atm.showBalance());
    }

    @Test
    void getAllMoneyFromAtmTest() {
        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        denominationMap.put(FIVE_HUNDRED, 2);
        denominationMap.put(ONE_THOUSAND, 2);
        denominationMap.put(TWO_THOUSAND, 2);
        denominationMap.put(FIVE_THOUSAND, 2);
        atm.putMoney(denominationMap);

        assertEquals(17200, atm.showBalance());

        atm.getMoney(17200);

        assertEquals(0, atm.showBalance());
    }
}
