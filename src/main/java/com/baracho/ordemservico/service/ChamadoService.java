package com.baracho.ordemservico.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.dtos.ChamadoDTO;
import com.baracho.ordemservico.domain.enums.Prioridade;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {
	
	@Autowired 
	private ChamadoRepository chamadoRepository;
	
	@Autowired 
	private ClienteService clienteService;
	
	@Autowired 
	private TecnicoService tecnicoService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado para o id: " + id));
	}

	public List<Chamado> findAll() {
		
		return chamadoRepository.findAll();
	}

	public Chamado create(ChamadoDTO objDto) {
		
		
		return chamadoRepository.save(newChamado(objDto));
	}

	private Chamado newChamado(ChamadoDTO objDto) {
		
		Tecnico tecnico = tecnicoService.findById(objDto.getTecnico());
		
		Cliente cliente = clienteService.findById(objDto.getCliente());
		
		Chamado chamado = new Chamado();
		
		if(objDto.getId() != null) {
			chamado.setId(objDto.getId());
		}
		
		if(objDto.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(objDto.getPrioridade()));
		chamado.setStatus(Status.toEnum(objDto.getStatus()));
		chamado.setTitulo(objDto.getTitulo());
		chamado.setObservacoes(objDto.getObservacoes());
		
		return chamado;
	}

}
