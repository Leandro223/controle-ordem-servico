package com.baracho.ordemservico.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.RegistroUpdate;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.dtos.ChamadoDTO;
import com.baracho.ordemservico.domain.enums.Prioridade;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.repository.RegistroUpdateRepository;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;



@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired 
	private RegistroUpdateRepository registroUpdateRepository;

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

	public Chamado update(Integer id, @Valid ChamadoDTO objDto) {
		objDto.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDto);
		
		Chamado updatedChamado = chamadoRepository.save(oldObj);

		//criar e salvar registros update 
		List<RegistroUpdate> registros = new ArrayList<>();
		
		RegistroUpdate registro = new RegistroUpdate();
		
		registro.setChamadoId(updatedChamado.getId());
		
		//Obter o usuario atenticado do contexto segurança  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String responsavel = authentication.getName();
		
		registro.setResponsavel(responsavel);
		registro.setDataAtualizacao(LocalDateTime.now());
		registros.add(registro);
		
		registroUpdateRepository.saveAll(registros);
		
		return updatedChamado;
	}

	private Chamado newChamado(ChamadoDTO objDto) {

		Tecnico tecnico = tecnicoService.findById(objDto.getTecnico());

		Cliente cliente = clienteService.findById(objDto.getCliente());

		Chamado chamado = new Chamado();

		if (objDto.getId() != null) {
			chamado.setId(objDto.getId());
		}

		if (objDto.getStatus().equals(2)) {
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

	public void delete(Integer id) {
		Optional<Chamado> chamadoOpt = chamadoRepository.findById(id); 
		 
		if(chamadoOpt.isPresent()) {
			Chamado chamado = chamadoOpt.get();
			
			if(chamado.getStatus() != Status.ABERTO && chamado.getStatus() != Status.ANDAMENTO) {
				chamadoRepository.deleteById(id);
				
			}else {
				throw new DataIntegrityViolationException("Não é possivel remover o chamado. O status é ABERTO ou ANDAMENTO");
				
				}
			
		}else {
			throw new ObjectnotFoundException("Chamado não é encontrado");
			
		}

	}

	public boolean existsChamadoById(Integer id) {
		
		return chamadoRepository.existsById(id);
	}

}
