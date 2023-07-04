package com.baracho.ordemservico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Pessoa;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.dtos.TecnicoDTO;
import com.baracho.ordemservico.repository.PessoaRepository;
import com.baracho.ordemservico.repository.TecnicoRepository;
import com.baracho.ordemservico.service.exceptions.DataIntegrityViolationException;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired 
	private TecnicoRepository tecnicoRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
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
		validarPorCPFeEMAIL(objDto);
		return tecnicoRepository.save(newObj);
		
		
	}

	private void validarPorCPFeEMAIL(TecnicoDTO objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
		}
		
		obj = pessoaRepository.findByEmail(objDto.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema.");
		}
		
	}

}
