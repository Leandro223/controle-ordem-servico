package com.baracho.ordemservico.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Pessoa;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.dtos.TecnicoDTO;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
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
	
	@Autowired 
	private ChamadoRepository chamadoRepository;
	
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado para o id: " + id));
		
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
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

	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		objDto.setId(id);
		Tecnico oldObj = findById(id);
		
		if(!objDto.getSenha().equals(oldObj.getSenha())) 
			objDto.setSenha(encoder.encode(objDto.getSenha()));
		
		validarPorCPFeEMAIL(objDto);
		oldObj = new Tecnico(objDto);
		return tecnicoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		for(Chamado chamado : obj.getChamados()) {
			if(chamado.getStatus() != Status.ABERTO && chamado.getStatus() != Status.ANDAMENTO) {
				chamadoRepository.deleteById(chamado.getId());
			}else {
				throw new DataIntegrityViolationException("O tecnico tem chamado em andamento e não pode ser excluído!");
			}
		}
		
		tecnicoRepository.deleteById(id);
		
		
	}

}
