package com.baracho.ordemservico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.repository.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired 
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		
		return obj.orElse(null);
		
	}

}
