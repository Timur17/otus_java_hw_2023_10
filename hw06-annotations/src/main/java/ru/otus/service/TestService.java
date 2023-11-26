package ru.otus.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test.LifeCycleTest;
import ru.otus.test.annotations.*;

public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> beforeEachMethods = new ArrayList<>();
    private final List<Method> afterEachMethods = new ArrayList<>();

    private final List<Method> beforeAllMethods = new ArrayList<>();
    private final List<Method> afterAllMethods = new ArrayList<>();

    private final Class<?> clazz;

    private final Map<String, String> result = new HashMap<>();

    public TestService(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void paresMethodsAnnotations() {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
                result.put(method.getName(), "FAILED");
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEachMethods.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                afterEachMethods.add(method);
            } else if (method.isAnnotationPresent(BeforeAll.class)) {
                beforeAllMethods.add(method);
            } else if (method.isAnnotationPresent(AfterAll.class)) {
                afterAllMethods.add(method);
            }
        }
        logger.info("-------------Amount tests-------------");
        logger.info("Class '{}' has: ", clazz.getName());
        logger.info("- '{}' Test Methods ", testMethods.size());
        logger.info("- '{}' BeforeEach Methods ", beforeEachMethods.size());
        logger.info("- '{}' AfterEachMethods Methods ", afterEachMethods.size());
        logger.info("- '{}' BeforeAll Methods ", beforeAllMethods.size());
        logger.info("- '{}' AfterAll Methods ", afterAllMethods.size());
    }

    public void executeTestMethods()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.info("-------------Tests started-------------");

        boolean resultStaticTests;
        resultStaticTests = executeStaticMethods(beforeAllMethods);
        if (!resultStaticTests) {
            ignoreTest();
            return;
        }

        for (Method testMethod : testMethods) {
            LifeCycleTest lifeCycleTest =
                    (LifeCycleTest) clazz.getDeclaredConstructor().newInstance();
            try {
                executeListMethods(beforeEachMethods, lifeCycleTest);
                testMethod.invoke(lifeCycleTest);
                executeListMethods(afterEachMethods, lifeCycleTest);
                result.put(testMethod.getName(), "SUCCESS");
            } catch (Exception e) {
                logger.error("Exception happened during executing method '{}'", testMethod.getName());
            }
        }

        resultStaticTests = executeStaticMethods(afterAllMethods);
        if (!resultStaticTests) {
            ignoreTest();
        }
    }

    public void printResult() {
        logger.info("-------------Print result-------------");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            logger.info("Test '{}' is '{}'", entry.getKey(), entry.getValue());
        }
    }

    private void ignoreTest() {
        for (Method testMethod : testMethods) {
            result.put(testMethod.getName(), "IGNORED");
        }
    }

    private boolean executeStaticMethods(List<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(null);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private void executeListMethods(List<Method> methods, Object object)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }
}
