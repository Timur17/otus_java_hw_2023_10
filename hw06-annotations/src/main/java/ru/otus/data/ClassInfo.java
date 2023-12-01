package ru.otus.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClassInfo {
    private final Class<?> clazz;
    private List<Method> testMethods = new ArrayList<>();
    private List<Method> beforeEachMethods = new ArrayList<>();
    private List<Method> afterEachMethods = new ArrayList<>();
    private List<Method> beforeAllMethods = new ArrayList<>();
    private List<Method> afterAllMethods = new ArrayList<>();
    private Map<String, String> result = new HashMap<>();
}
