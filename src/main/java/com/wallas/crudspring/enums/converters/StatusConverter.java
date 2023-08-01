package com.wallas.crudspring.enums.converters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.wallas.crudspring.enums.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status,String> {

    @Override
    public String convertToDatabaseColumn(Status status) {  
        if(status == null){
            return null;
        }
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }

        return Stream.of(Status.values())
            .filter(c -> c.getValue().equals(value))
            .findFirst()
            .orElseThrow(IllegalArgumentException:: new);
    }
    
}
