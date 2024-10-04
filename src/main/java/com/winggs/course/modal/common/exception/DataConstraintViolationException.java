package com.winggs.course.modal.common.exception;

public class DataConstraintViolationException extends RuntimeException {
    String message;

    public DataConstraintViolationException(String message) {
        super(message);
        this.message = message;
    }
}
