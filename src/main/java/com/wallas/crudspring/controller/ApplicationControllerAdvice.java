package com.wallas.crudspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wallas.crudspring.exception.RecordAlreadyExistsException;
import com.wallas.crudspring.exception.RecordAlreadyExistsItemDataException;
import com.wallas.crudspring.exception.RecordAlreadyExistsNameException;
import com.wallas.crudspring.exception.RecordNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRecordAlreadyExistsException(RecordAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RecordAlreadyExistsItemDataException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public String handleRecordAlreadyExistsItemDataException(RecordAlreadyExistsItemDataException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(RecordAlreadyExistsNameException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleRecordAlreadyExistsNameException(RecordAlreadyExistsNameException ex) {
        return ex.getMessage();
    }  
}
