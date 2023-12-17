package ru.otus.entity;

import java.util.Arrays;

public class MethodEntity {

    private final String name;
    private final Class<?>[] parameters;

    public MethodEntity(String name, Class<?>[] parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodEntity that = (MethodEntity) o;

        if (!name.equals(that.name)) return false;
        return Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }
}
