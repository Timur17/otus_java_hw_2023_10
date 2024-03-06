package ru.otus.exception;

public class DiException extends RuntimeException {
    public DiException(Exception ex) {
        super(ex);
    }

    public DiException(String msg) {
        super(msg);
    }
}
