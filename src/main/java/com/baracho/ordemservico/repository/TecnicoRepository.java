package com.baracho.ordemservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.baracho.ordemservico.domain.Tecnico;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
