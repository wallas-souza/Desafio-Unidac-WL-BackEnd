package com.wallas.crudspring.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wallas.crudspring.dto.CafeDTO;
import com.wallas.crudspring.dto.ColaboradorDTO;
import com.wallas.crudspring.dto.mapper.ColaboradorMapper;
import com.wallas.crudspring.exception.RecordAlreadyExistsException;
import com.wallas.crudspring.exception.RecordAlreadyExistsItemDataException;
import com.wallas.crudspring.exception.RecordAlreadyExistsNameException;
import com.wallas.crudspring.exception.RecordNotFoundException;
import com.wallas.crudspring.model.Colaborador;
import com.wallas.crudspring.repository.ColaboradoresRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class ColaboradorService {

    private final ColaboradoresRepository repository;
    private final ColaboradorMapper mapper;
    private final String mensagem = "Colaborador não encontrado com o id: ";

    @Transactional(readOnly = true)
    public List<ColaboradorDTO> list() {
        return repository.listWithNativeQuery().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public ColaboradorDTO findById(@NotNull @Positive Long id) {
        return repository.findByIdWithNativeQuery(id).map(mapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(mensagem + id));
    }

    public ColaboradorDTO criar(@Valid @NotNull ColaboradorDTO colaboradorDTO) {
        Colaborador colaborador = mapper.toEntity(colaboradorDTO);
        if(this.verificarColaborador(colaborador).isPresent()){
        throw new RecordAlreadyExistsNameException(
                    "Não pode criar colaborador com um nome já cadastrado. Tente excluir o seu nome da tabela primeiro.");
        }
        
        Optional<Colaborador> exisColaborador = this.verificarCPF(colaborador);
        if (exisColaborador.isPresent()) {
            throw new RecordAlreadyExistsException(
                    "Não pode criar colaborador com um CPF já cadastrado. Tente excluir o seu CPF da tabela primeiro.");
        }
        Optional<Colaborador> existCafe = this.verificarCafeData(colaboradorDTO);
        if (existCafe.isPresent()) {
            throw new RecordAlreadyExistsItemDataException(
                "Não pode criar colaborador com itens do café repetido. Tente outro item ou outro dia da tabela.");
        }

        return mapper.toDTO(repository.save(mapper.toEntity(colaboradorDTO)));
    }

    public ColaboradorDTO update(@NotNull @Positive Long id, @Valid @NotNull ColaboradorDTO colaboradorDTO) {
        Colaborador cola = mapper.toEntity(colaboradorDTO);
        Optional<Colaborador> exisColaborador = this.verificarCPFIgnoreId(cola);
        
        if(this.verificarColaboradorIgnoreId(cola).isPresent()){
        throw new RecordAlreadyExistsNameException(
                    "Não pode criar colaborador com um nome já cadastrado. Tente excluir o seu nome da tabela primeiro.");
        }
        
        if (exisColaborador.isPresent()) {
            throw new RecordAlreadyExistsException(
                    "Não pode criar colaborador com um CPF já cadastrado. Tente excluir o seu CPF da tabela primeiro.");
        }

        Optional<Colaborador> existCafe = this.verificarCafeDataIgnoreId(colaboradorDTO);
        if (existCafe.isPresent()) {
            throw new RecordAlreadyExistsItemDataException(
                "Não pode criar colaborador com itens do café repetido. Tente outro item ou outro dia da tabela.");
        }

        return repository.findByIdWithNativeQuery(id)
                .map(recordFound -> {
                    Colaborador colaborador = mapper.toEntity(colaboradorDTO);
                    recordFound.setNome(colaboradorDTO.getNome());
                    recordFound.setCpf(colaboradorDTO.getCpf());
                    recordFound.getCafes().clear();
                    colaborador.getCafes().forEach(recordFound.getCafes()::add);
                    return mapper.toDTO(repository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(mensagem + id));

    }

    public void delete(@NotNull @Positive Long id) {
        repository.findById(id).orElseThrow(() -> new RecordNotFoundException(mensagem + id));
        repository.softDeleteWithNativeQuery(id);
    }

    public Optional<Colaborador> verificarColaboradorIgnoreId(Colaborador colaborador) {
        return repository.findByNomeAndStatusIgnoringId(colaborador.getNome(), colaborador.getId());
    }

    public Optional<Colaborador> verificarColaborador(Colaborador colaborador) {
        return repository.findByNomeAndStatus(colaborador.getNome());
    }

    public Optional<Colaborador> verificarCPF(Colaborador colaborador) {
        return repository.findByCpfAndStatus(colaborador.getCpf());
    }

    public Optional<Colaborador> verificarCPFIgnoreId(Colaborador colaborador) {
        return repository.findByCpfAndStatusIgnoringId(colaborador.getCpf(), colaborador.getId());
    }
    
    public Optional<Colaborador> verificarCafeData(ColaboradorDTO colaboradorDTO) {
        List<CafeDTO> cafes = colaboradorDTO.getCafes();
        for (CafeDTO cafe : cafes) {
            String item = cafe.getItem();
            LocalDate date = cafe.getData();

            List<Colaborador> colaboradoresWithSameItemAndDate = repository.findColaboradoresByItemAndDate(item, date);
            if (!colaboradoresWithSameItemAndDate.isEmpty()) {
                return Optional.of(colaboradoresWithSameItemAndDate.get(0));
            }
        }
        return Optional.empty();
    }

    public Optional<Colaborador> verificarCafeDataIgnoreId(ColaboradorDTO colaboradorDTO) {
        List<CafeDTO> cafes = colaboradorDTO.getCafes();
        for (CafeDTO cafe : cafes) {
            String item = cafe.getItem();
            LocalDate date = cafe.getData();

            List<Colaborador> colaboradoresWithSameItemAndDate = repository.findColaboradoresByItemAndDateIgnoringId(item, date,colaboradorDTO.get_id());
            if (!colaboradoresWithSameItemAndDate.isEmpty()) {
                return Optional.of(colaboradoresWithSameItemAndDate.get(0));
            }
        }
        return Optional.empty();
    }

}
