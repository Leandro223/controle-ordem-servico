package com.baracho.ordemservico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.repository.TecnicoRepository;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired 
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado para o id: " + id));
		
	}

}
