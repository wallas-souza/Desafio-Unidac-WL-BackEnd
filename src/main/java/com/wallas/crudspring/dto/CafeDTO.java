package com.wallas.crudspring.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CafeDTO {
    
    private Long id;
    private String item;
    private LocalDate data;
    
}
