package com.wallas.crudspring.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wallas.crudspring.model.Colaborador;

@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaborador, Long> {

        @Query(value = "SELECT * FROM colaborador WHERE status = 'Ativo'", nativeQuery = true)
        List<Colaborador> listWithNativeQuery();

        @Query(value = "SELECT * FROM colaborador WHERE status = 'Ativo' AND id = ?1", nativeQuery = true)
        Optional<Colaborador> findByIdWithNativeQuery(Long id);

        @Modifying
        @Transactional
        @Query(value = "UPDATE colaborador SET nome = ?2, cpf = ?3, data = ?4, status = ?5 WHERE id = ?1", nativeQuery = true)
        void updateWithNativeQuery(Long id, String nome, String cpf, LocalDate data, String status);

        @Modifying
        @Transactional
        @Query(value = "UPDATE colaborador SET status = 'Inativo' WHERE id = ?1", nativeQuery = true)
        void softDeleteWithNativeQuery(Long id);

        @Query(value = "SELECT * FROM colaborador WHERE cpf = ?1 AND status = 'Ativo'", nativeQuery = true)
        Optional<Colaborador> findByCpfAndStatus(String cpf);

        @Query(value = "SELECT c FROM Colaborador c JOIN c.cafes ca " +
                        "WHERE c.status = 'Ativo' AND ca.item = ?1 AND ca.data = ?2")
        List<Colaborador> findColaboradoresByItemAndDate(String item, LocalDate date);

        @Query(value = "SELECT c FROM Colaborador c JOIN c.cafes ca " +
                        "WHERE c.status = 'Ativo' AND ca.item = ?1 AND ca.data = ?2 AND c.id <> ?3")
        List<Colaborador> findColaboradoresByItemAndDateIgnoringId(String item, LocalDate date, Long idToIgnore);

        @Query(value = "SELECT * FROM colaborador WHERE cpf = ?1 AND status = 'Ativo' AND id <> ?2", nativeQuery = true)
        Optional<Colaborador> findByCpfAndStatusIgnoringId(String cpf, Long idToIgnore);

        @Query(value = "SELECT c FROM Colaborador c WHERE nome = ?1 AND status = 'Ativo'")
        Optional<Colaborador> findByNomeAndStatus(String nome);

        @Query(value = "SELECT c FROM Colaborador c WHERE nome = ?1 AND status = 'Ativo' AND id <> ?2")
        Optional<Colaborador> findByNomeAndStatusIgnoringId(String nome, Long id);
}
