package ru.antonov.jdbc.exceptions;

public class ApplicationInitializationException extends RuntimeException {
    public ApplicationInitializationException() {
    }
    public ApplicationInitializationException(String message) {
        super(message);
    }
}

