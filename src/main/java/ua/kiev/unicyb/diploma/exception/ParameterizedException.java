package ua.kiev.unicyb.diploma.exception;

import org.springframework.stereotype.Component;

public class ParameterizedException extends RuntimeException {
    public ParameterizedException(String message) {
        super(message);
    }
}
