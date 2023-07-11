package com.baracho.ordemservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baracho.ordemservico.domain.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	

}
