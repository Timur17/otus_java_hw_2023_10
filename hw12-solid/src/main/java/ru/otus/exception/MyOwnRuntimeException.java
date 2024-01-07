package ru.otus.exception;

public class MyOwnRuntimeException extends RuntimeException {
    public MyOwnRuntimeException(String message) {
        super(message);
    }
}
