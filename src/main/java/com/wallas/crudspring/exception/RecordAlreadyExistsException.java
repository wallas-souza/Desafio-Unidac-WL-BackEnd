package com.wallas.crudspring.exception;

public class RecordAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}