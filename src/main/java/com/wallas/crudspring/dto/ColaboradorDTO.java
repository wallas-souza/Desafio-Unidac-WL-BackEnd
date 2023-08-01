package com.wallas.crudspring.dto;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ColaboradorDTO {
    
    private Long _id;

    @NotBlank
    @NotNull
    @Length(min = 4, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @CPF
    @Column(name = "cpf", length = 20, nullable = false)
    private String cpf;

    private List<CafeDTO> cafes;

}
