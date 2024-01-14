package ru.otus.model;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Atm {
    private final Set<Cell> cells;
}
