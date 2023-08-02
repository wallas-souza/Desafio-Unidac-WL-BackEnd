package com.wallas.crudspring;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wallas.crudspring.model.Cafe;
import com.wallas.crudspring.model.Colaborador;
import com.wallas.crudspring.repository.ColaboradoresRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public String PORT = System.getenv("PORT");

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDadaBase( ColaboradoresRepository colaboradoresRepository){
		return args -> {
			colaboradoresRepository.deleteAll();

			Colaborador colaborador = new Colaborador();
			colaborador.setNome("João");
			colaborador.setCpf("73244216013");
			
			Cafe cafe = new Cafe();
			cafe.setItem("ovo");
			cafe.setData(LocalDate.now());
			cafe.setColaborador(colaborador);
			colaborador.getCafes().add(cafe);

			Cafe cafe1 = new Cafe();
			cafe1.setItem("pão");
			cafe1.setData(LocalDate.now());
			cafe1.setColaborador(colaborador);
			colaborador.getCafes().add(cafe1);

			colaboradoresRepository.save(colaborador);

			Colaborador colaborador1 = new Colaborador();
			colaborador1.setNome("Wallas");
			colaborador1.setCpf("11808745442");
			
			Cafe cafe2 = new Cafe();
			cafe2.setItem("suco");
			cafe2.setData(LocalDate.now());
			cafe2.setColaborador(colaborador1);
			colaborador1.getCafes().add(cafe2);

			Cafe cafe3 = new Cafe();
			cafe3.setItem("bolo");
			cafe3.setData(LocalDate.now());
			cafe3.setColaborador(colaborador1);
			colaborador1.getCafes().add(cafe3);

			colaboradoresRepository.save(colaborador1);
		};
	}

}
