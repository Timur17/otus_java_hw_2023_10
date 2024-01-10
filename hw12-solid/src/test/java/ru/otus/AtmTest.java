package ru.otus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Atm;
import ru.otus.model.Cell;
import ru.otus.service.AtmServiceImpl;
import ru.otus.service.WithdrawMoneyServiceImpl;

class AtmTest {

    private final int ONE_HUNDRED = 100;

    private final int FIVE_HUNDRED = 500;

    private final int ONE_THOUSAND = 1000;

    private final int TWO_THOUSAND = 2000;

    private final int FIVE_THOUSAND = 5000;

    private Set<Cell> cells;

    private final List<Integer> expected =
            List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);

    private AtmServiceImpl atmService;
    private Atm atm;

    @BeforeEach
    void setUp() {
        cells = new TreeSet<>((o1, o2) -> o2.getDenomination() - o1.getDenomination());
        expected.forEach(denomination -> cells.add(new Cell(denomination)));
        atm = new Atm(cells);
        atmService = new AtmServiceImpl(atm, new WithdrawMoneyServiceImpl());
    }

    @Test
    void initAtmTest() {
        List<Integer> actual =
                atm.getCells().stream().map(Cell::getDenomination).toList();
        assertTrue(expected.containsAll(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void putMoneyTest() {

        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 1);
        atmService.putMoney(denominationMap);

        assertEquals(8600, atmService.showBalance());
    }

    @Test
    void putMoneyTest2() {
        atm.getCells().remove(new Cell(ONE_HUNDRED));

        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        atmService.putMoney(denominationMap);

        assertEquals(0, atmService.showBalance());
    }

    @Test
    void getMoneyMoreThanHasAtmTest() {
        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 2);
        atmService.putMoney(denominationMap);

        assertEquals(13600, atmService.showBalance());

        assertEquals(13600, atmService.showBalance());
        assertThrows(RuntimeException.class, () -> atmService.getMoney(14000));
    }

    @Test
    void getMoneyEachDenominationOnceTest() {
        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        denominationMap.put(FIVE_HUNDRED, 2);
        denominationMap.put(ONE_THOUSAND, 2);
        denominationMap.put(TWO_THOUSAND, 2);
        denominationMap.put(FIVE_THOUSAND, 2);
        atmService.putMoney(denominationMap);

        assertEquals(17200, atmService.showBalance());

        atmService.getMoney(8600);

        assertEquals(8600, atmService.showBalance());
    }

    @Test
    void getAllMoneyFromAtmTest() {
        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 2);
        denominationMap.put(FIVE_HUNDRED, 2);
        denominationMap.put(ONE_THOUSAND, 2);
        denominationMap.put(TWO_THOUSAND, 2);
        denominationMap.put(FIVE_THOUSAND, 2);
        atmService.putMoney(denominationMap);

        assertEquals(17200, atmService.showBalance());

        atmService.getMoney(17200);

        assertEquals(0, atmService.showBalance());
    }
}
