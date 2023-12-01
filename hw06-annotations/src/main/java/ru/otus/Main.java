package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.data.ClassInfo;
import ru.otus.service.TestService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        logger.info("Enter class name with package. Example: {}", "ru.otus.test.LifeCycleTest");
        Scanner in = new Scanner(System.in);
        String className = in.nextLine();

        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("Wrong class name: '{}' ", className, e);
            return;
        }

        Constructor<?> constructor = TestService.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        ClassInfo classInfo = new ClassInfo(clazz);

        TestService testService = (TestService) constructor.newInstance();
        Method methodRun = testService.getClass().getDeclaredMethod("run", ClassInfo.class);
        methodRun.setAccessible(true);
        methodRun.invoke(testService, classInfo);
    }
}
