package com.wallas.crudspring.exception;

public class RecordAlreadyExistsNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsNameException(String message) {
        super(message);
    }
}
