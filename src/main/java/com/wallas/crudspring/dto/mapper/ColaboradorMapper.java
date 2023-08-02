package com.wallas.crudspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wallas.crudspring.dto.CafeDTO;
import com.wallas.crudspring.dto.ColaboradorDTO;
import com.wallas.crudspring.model.Cafe;
import com.wallas.crudspring.model.Colaborador;

@Component
public class ColaboradorMapper {
    
    public ColaboradorDTO toDTO(Colaborador colaborador){
        if(colaborador == null){
            return null;
        }
        List<CafeDTO> cafes = colaborador.getCafes()
            .stream()
            .map(cafe -> new CafeDTO(cafe.getId(), cafe.getItem(), cafe.getData()))
            .collect(Collectors.toList());
        return new ColaboradorDTO(
            colaborador.getId(),
            colaborador.getNome(),
            colaborador.getCpf(),
            cafes
        );
    }

    public Colaborador toEntity(ColaboradorDTO colaboradorDTO){
        
        if(colaboradorDTO == null){
            return null;
        }
        
        Colaborador colaborador = new Colaborador();
        if(colaboradorDTO.get_id() != null){
            colaborador.setId(colaboradorDTO.get_id());
        }
        colaborador.setNome(colaboradorDTO.getNome());
        colaborador.setCpf(colaboradorDTO.getCpf());

        List<Cafe> cafes = colaboradorDTO.getCafes().stream().map(cafeDTO ->{
            Cafe cafe = new Cafe();
            cafe.setId(cafeDTO.getId());
            cafe.setItem(cafeDTO.getItem());
            cafe.setData(cafeDTO.getData());
            cafe.setColaborador(colaborador);
            return cafe;
        }).collect(Collectors.toList());
        colaborador.setCafes(cafes);

        return colaborador;
    }  

}
