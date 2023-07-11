package com.baracho.ordemservico.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
	
	public List<Map<String, Integer>> getClientesPorMesReal() {
	    List<Cliente> clientes = clienteRepository.findAll();
	    List<Month> meses = Arrays.asList(Month.values());

	    return meses.stream().map(mes -> {
	      int clientesNoMes = (int) clientes.stream()
	          .filter(cliente -> cliente.getDataCriacao().getMonth() == mes)
	          .count();

	      return Map.of("mes", mes.getValue(), "clientes", clientesNoMes);
	    }).collect(Collectors.toList());
	  }
	
	public List<Map<String, Integer>> getClientesPorMesTeste() {
	    // Gera uma lista fictícia de clientes com datas aleatórias
	    List<Map<String, LocalDate>> clientes = gerarClientesFicticios();

	    List<Month> meses = Arrays.asList(Month.values());

	    return meses.stream().map(mes -> {
	        int clientesNoMes = (int) clientes.stream()
	                .filter(cliente -> cliente.get("dataCriacao").getMonth() == mes)
	                .count();

	        return Map.of("mes", mes.getValue(), "clientes", clientesNoMes);
	    }).collect(Collectors.toList());
	}

	private List<Map<String, LocalDate>> gerarClientesFicticios() {
	    List<Map<String, LocalDate>> clientes = new ArrayList<>();
	    Random random = new Random();

	    // Gera 100 clientes com datas fictícias nos últimos 12 meses
	    for (int i = 0; i < 1000; i++) {
	        int monthValue = random.nextInt(12) + 1; // Gera um valor aleatório entre 1 e 12
	        int year = 2023; // Define o ano fictício
	        int day = random.nextInt(28) + 1; // Gera um valor aleatório entre 1 e 28

	        Month month = Month.of(monthValue);
	        LocalDate dataCriacao = LocalDate.of(year, month, day);
	        Map<String, LocalDate> cliente = new HashMap<>();
	        cliente.put("dataCriacao", dataCriacao);
	        clientes.add(cliente);
	    }

	    return clientes;
	}
	


}
