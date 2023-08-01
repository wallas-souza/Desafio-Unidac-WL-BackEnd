package com.wallas.crudspring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallas.crudspring.enums.Status;
import com.wallas.crudspring.enums.converters.StatusConverter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colaborador")
public class Colaborador {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 4, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @CPF
    @Column(name = "cpf", length = 20, nullable = false)
    private String cpf;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ATIVO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "colaborador")
    private List<Cafe> cafes = new ArrayList<>();
}
