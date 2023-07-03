package com.baracho.ordemservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baracho.ordemservico.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
