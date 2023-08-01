package com.wallas.crudspring.exception;

public class RecordAlreadyExistsItemDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsItemDataException(String message) {
        super(message);
    }
}
