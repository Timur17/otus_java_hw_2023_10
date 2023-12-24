package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface testLoggingInterface = Ioc.createMyClass();
        testLoggingInterface.calculation(6);
        testLoggingInterface.calculation(6, 7);
        testLoggingInterface.calcul(6, 7);
        testLoggingInterface.calculation(6, 7, "TestString");
    }
}
