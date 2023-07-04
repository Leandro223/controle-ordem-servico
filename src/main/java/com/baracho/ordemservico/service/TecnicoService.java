package com.baracho.ordemservico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.dtos.TecnicoDTO;
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

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDto) {
		objDto.setId(null);
		Tecnico newObj = new Tecnico(objDto);
		return tecnicoRepository.save(newObj);
		
		
	}

}
