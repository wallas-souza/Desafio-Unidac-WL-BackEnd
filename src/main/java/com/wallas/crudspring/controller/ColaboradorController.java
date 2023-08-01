package com.wallas.crudspring.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wallas.crudspring.Service.ColaboradorService;
import com.wallas.crudspring.dto.ColaboradorDTO;

import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/colaboradores")
@AllArgsConstructor
public class ColaboradorController {

    private final ColaboradorService service;
    
    @GetMapping
    public List<ColaboradorDTO> list(){
        return this.service.list();
    }

    @GetMapping("/{id}")
    public ColaboradorDTO findById(@PathVariable @NotNull @Positive Long id){
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColaboradorDTO criar(@RequestBody @Valid @NotNull ColaboradorDTO colaborador){
        return service.criar(colaborador);
    }

    @PutMapping("/{id}")
    public ColaboradorDTO update(@PathVariable @Valid @NotNull @Positive Long id, @RequestBody ColaboradorDTO colaborador){
        return service.update(id, colaborador);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id){
       service.delete(id);
    }
}
