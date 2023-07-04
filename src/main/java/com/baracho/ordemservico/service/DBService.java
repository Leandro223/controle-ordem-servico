package com.baracho.ordemservico.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.enums.Perfil;
import com.baracho.ordemservico.domain.enums.Prioridade;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.repository.ClienteRepository;
import com.baracho.ordemservico.repository.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired 
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void intanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Leandro Baracho", "40279862024", "leandro@gmail.com", "123" );
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "13987854789", "torvalds@gmail.com", "123"); 
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1 );
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
