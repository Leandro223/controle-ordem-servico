package com.baracho.ordemservico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baracho.ordemservico.domain.RegistroUpdate;

public interface RegistroUpdateRepository extends JpaRepository<RegistroUpdate, Long> {
	
	List<RegistroUpdate> findAllByChamadoId(Integer chamadoId);

}
