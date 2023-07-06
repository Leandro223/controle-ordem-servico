package com.baracho.ordemservico.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Pessoa;
import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.dtos.ClienteDTO;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.repository.PessoaRepository;
import com.baracho.ordemservico.repository.ClienteRepository;
import com.baracho.ordemservico.service.exceptions.DataIntegrityViolationException;
import com.baracho.ordemservico.service.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {
	
	@Autowired 
	private ClienteRepository clienteRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private ChamadoRepository chamadoRepository;
	
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado para o id: " + id));
		
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		Cliente newObj = new Cliente(objDto);
		validarPorCPFeEMAIL(objDto);
		return clienteRepository.save(newObj);
		
		
	}

	private void validarPorCPFeEMAIL(ClienteDTO objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
		}
		
		obj = pessoaRepository.findByEmail(objDto.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema.");
		}
		
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDto) {
		objDto.setId(id);
		Cliente oldObj = findById(id);
		
		if(!objDto.getSenha().equals(oldObj.getSenha())) 
			objDto.setSenha(encoder.encode(objDto.getSenha()));
		
		validarPorCPFeEMAIL(objDto);
		oldObj = new Cliente(objDto);
		return clienteRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		for(Chamado chamado : obj.getChamados()) {
			if(chamado.getStatus() != Status.ABERTO && chamado.getStatus() != Status.ANDAMENTO) {
				chamadoRepository.deleteById(chamado.getId());
			}else {
				throw new DataIntegrityViolationException("O cliente tem chamado em andamento e não pode ser excluído!");
			}
		}
		
		clienteRepository.deleteById(id);
		
		
	}

}
