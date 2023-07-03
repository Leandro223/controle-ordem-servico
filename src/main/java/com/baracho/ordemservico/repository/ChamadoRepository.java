package com.baracho.ordemservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baracho.ordemservico.domain.Chamado;



public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
