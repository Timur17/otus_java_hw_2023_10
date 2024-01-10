package ru.otus.model;

import java.util.Set;
import lombok.Data;

@Data
public class Atm {
    private final Set<Cell> cells;
}
