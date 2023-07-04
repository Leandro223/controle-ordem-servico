package com.baracho.ordemservico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {
	
	@Autowired 
	private ChamadoRepository chamadoRepository;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado para o id: " + id));
	}

	public List<Chamado> findAll() {
		
		return chamadoRepository.findAll();
	}

}
