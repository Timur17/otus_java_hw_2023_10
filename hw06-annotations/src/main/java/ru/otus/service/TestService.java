package ru.otus.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.data.ClassInfo;
import ru.otus.test.LifeCycleTest;
import ru.otus.test.annotations.*;

public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @SuppressWarnings("java:S1144")
    private void run(ClassInfo classInfo)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        paresMethodsAnnotations(classInfo);
        executeTestMethods(classInfo);
        printResult(classInfo.getResult());
    }

    private void paresMethodsAnnotations(ClassInfo classInfo) {

        Method[] methods = classInfo.getClazz().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                classInfo.getTestMethods().add(method);
                classInfo.getResult().put(method.getName(), "FAILED");
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                classInfo.getBeforeEachMethods().add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                classInfo.getAfterEachMethods().add(method);
            } else if (method.isAnnotationPresent(BeforeAll.class)) {
                classInfo.getBeforeAllMethods().add(method);
            } else if (method.isAnnotationPresent(AfterAll.class)) {
                classInfo.getAfterAllMethods().add(method);
            }
        }
        logger.info("-------------Amount tests-------------");
        logger.info("Class '{}' has: ", classInfo.getClazz().getName());
        logger.info("- '{}' Test Methods ", classInfo.getTestMethods().size());
        logger.info(
                "- '{}' BeforeEach Methods ", classInfo.getBeforeEachMethods().size());
        logger.info(
                "- '{}' AfterEachMethods Methods ",
                classInfo.getAfterEachMethods().size());
        logger.info("- '{}' BeforeAll Methods ", classInfo.getBeforeAllMethods().size());
        logger.info("- '{}' AfterAll Methods ", classInfo.getAfterAllMethods().size());
    }

    private void executeTestMethods(ClassInfo classInfo)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.info("-------------Tests started-------------");

        boolean resultStaticTests;
        resultStaticTests = executeStaticMethods(classInfo.getBeforeAllMethods());
        if (!resultStaticTests) {
            ignoreTest(classInfo.getResult(), classInfo.getTestMethods());
            return;
        }

        for (Method testMethod : classInfo.getTestMethods()) {
            LifeCycleTest lifeCycleTest = (LifeCycleTest)
                    classInfo.getClazz().getDeclaredConstructor().newInstance();
            try {
                executeListMethods(classInfo.getBeforeEachMethods(), lifeCycleTest);
                testMethod.invoke(lifeCycleTest);
                executeListMethods(classInfo.getAfterEachMethods(), lifeCycleTest);
                classInfo.getResult().put(testMethod.getName(), "SUCCESS");
            } catch (Exception e) {
                logger.error("Exception happened during executing method '{}'", testMethod.getName());
            }
        }

        resultStaticTests = executeStaticMethods(classInfo.getAfterAllMethods());
        if (!resultStaticTests) {
            ignoreTest(classInfo.getResult(), classInfo.getTestMethods());
        }
    }

    private void printResult(Map<String, String> result) {
        logger.info("-------------Print result-------------");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            logger.info("Test '{}' is '{}'", entry.getKey(), entry.getValue());
        }
    }

    private void ignoreTest(Map<String, String> result, List<Method> methods) {
        for (Method testMethod : methods) {
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
