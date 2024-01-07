package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.cell.CellImpl;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Denomination.*;

class AtmTest {
    private Set<CellImpl> cellImpls;
    private final List<Denomination> expected = List.of(ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, TWO_THOUSAND, FIVE_THOUSAND);

    private Atm atm;
    @BeforeEach
    void setUp() {
        cellImpls = new TreeSet<>
                ((o1, o2) -> o2.getDenomination().getValue() - o1.getDenomination().getValue());
        expected.forEach(denomination -> {
            CellImpl cellImpl = new CellImpl(denomination);
            cellImpls.add(cellImpl);
        });
        atm = new Atm(cellImpls);
    }

    @Test
    void initAtmTest() {
        List<Denomination> actual = atm.getCell().stream().map(CellImpl::getDenomination).collect(Collectors.toList());
        System.out.println(actual);
        System.out.println(expected);
        assertTrue(expected.containsAll(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void findCellTest() {
        Optional<CellImpl> actualOneHundred = atm.findCell(ONE_HUNDRED);
        assertEquals(ONE_HUNDRED, actualOneHundred.orElseThrow().getDenomination());

    }

    @Test
    void findNotExistCellCellTest() {
        atm.getCell().remove(new CellImpl(FIVE_THOUSAND));

        assertThrows(
                NoSuchElementException.class,
                () -> {
                    Optional<CellImpl> noSuchCell = atm.findCell(FIVE_THOUSAND);
                    assertEquals(FIVE_THOUSAND, noSuchCell.orElseThrow().getDenomination());
                }
        );
    }



    @Test
    void putMoneyTest() {
        Optional<CellImpl> oneHundredCell = atm.findCell(ONE_HUNDRED);
        assertEquals(0, oneHundredCell.orElseThrow().getAmount());
        Optional<CellImpl> fiveHundredCell = atm.findCell(FIVE_HUNDRED);
        assertEquals(0, fiveHundredCell.orElseThrow().getAmount());
        Optional<CellImpl> oneThousandCell = atm.findCell(ONE_THOUSAND);
        assertEquals(0, oneThousandCell.orElseThrow().getAmount());
        Optional<CellImpl> twoThousandCell = atm.findCell(TWO_THOUSAND);
        assertEquals(0, twoThousandCell.orElseThrow().getAmount());
        Optional<CellImpl> fiveThousandCell = atm.findCell(FIVE_THOUSAND);
        assertEquals(0, fiveThousandCell.orElseThrow().getAmount());

        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 1);
        atm.putMoney(denominationMap);

        assertEquals(1, oneHundredCell.orElseThrow().getAmount());
        assertEquals(1, fiveHundredCell.orElseThrow().getAmount());
        assertEquals(1, oneThousandCell.orElseThrow().getAmount());
        assertEquals(1, twoThousandCell.orElseThrow().getAmount());
        assertEquals(1, fiveThousandCell.orElseThrow().getAmount());
    }

    @Test
    public void getMoneyMoreThanHasAtmTest() {
        Map<Denomination, Integer> denominationMap = new HashMap<>();
        denominationMap.put(ONE_HUNDRED, 1);
        denominationMap.put(FIVE_HUNDRED, 1);
        denominationMap.put(ONE_THOUSAND, 1);
        denominationMap.put(TWO_THOUSAND, 1);
        denominationMap.put(FIVE_THOUSAND, 2);
        atm.putMoney(denominationMap);

        assertEquals(13600, atm.showBalance());


        assertEquals(13600, atm.showBalance());
        assertThrows(RuntimeException.class,
                () -> atm.getMoney(14000));
    }


    @Test
    public void getMoneyEachDenominationOnceTest() {
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
    public void getAllMoneyFromAtmTest() {
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