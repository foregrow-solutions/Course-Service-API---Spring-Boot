package com.winggs.course.modal.common.exception;

public class InvalidDataException extends RuntimeException {
    String message;

    public InvalidDataException(String message) {
        super(message);
        this.message = message;
    }
}
