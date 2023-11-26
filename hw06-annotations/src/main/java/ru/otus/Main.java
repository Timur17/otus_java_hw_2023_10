package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.TestService;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
                    ClassNotFoundException {
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
        TestService testService = new TestService(clazz);
        testService.paresMethodsAnnotations();
        testService.executeTestMethods();
        testService.printResult();
    }
}
