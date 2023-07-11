package com.baracho.ordemservico.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.Tecnico;
import com.baracho.ordemservico.domain.enums.Perfil;
import com.baracho.ordemservico.domain.enums.Prioridade;
import com.baracho.ordemservico.domain.enums.Status;
import com.baracho.ordemservico.repository.ChamadoRepository;
import com.baracho.ordemservico.repository.PessoaRepository;

@Service
public class DBService {
	
	@Autowired 
	private ChamadoRepository chamadoRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Leandro Baracho", "550.482.150-95", "leandro@gmail.com", encoder.encode("123"), "55 11 98456-4937");
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56", "stallman@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "271.068.470-54", "shannon@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com", encoder.encode("123"), "55 11 98456-4937");

		Cliente cli1 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein1@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@mail2.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli3 = new Cliente(null, "Charles Darwin", "792.043.830-62", "darwin@mail3.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@mail4.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@mail5.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli6 = new Cliente(null, "Albert Einstein", "174.308.040-98", "einstei6n@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli7 = new Cliente(null, "Marie Curie", "528.876.480-80", "curie@ma7il.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli8 = new Cliente(null, "Charles Darwin", "419.819.180-86", "darwin@m8ail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli9 = new Cliente(null, "Stephen Hawking", "624.138.590-98", "hawking@9mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli10 = new Cliente(null, "Max Planck", "194.656.450-87", "planck@mai10l.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli11 = new Cliente(null, "Albert Einstein", "357.231.000-84", "einstei11n@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli12 = new Cliente(null, "Marie Curie", "291.746.670-76", "curie@mai12l.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli13 = new Cliente(null, "Charles Darwin", "492.830.610-47", "darwin@m13ail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli14 = new Cliente(null, "Stephen Hawking", "386.929.490-60", "hawking@m14ail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli15 = new Cliente(null, "Max Planck", "973.522.190-05", "planck@ma15il.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli16 = new Cliente(null, "Albert Einstein", "968.336.120-05", "einst12ein@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli17 = new Cliente(null, "Marie Curie", "374.835.600-53", "curi14e@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli18 = new Cliente(null, "Charles Darwin", "530.513.620-26", "dar14win@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli19 = new Cliente(null, "Stephen Hawking", "032.791.930-28", "hawk12ing@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli20 = new Cliente(null, "Max Planck", "968.477.480-01", "pla45nck@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli21 = new Cliente(null, "Albert Einstein", "989.851.510-45", "einstein@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli22 = new Cliente(null, "Marie Curie", "872.366.290-57", "curi54e@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli23 = new Cliente(null, "Charles Darwin", "392.469.410-98", "darw78in@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli24 = new Cliente(null, "Stephen Hawking", "738.693.360-64", "hawk78ing@mail.com", encoder.encode("123"), "55 11 98456-4937");
		Cliente cli25 = new Cliente(null, "Max Planck", "018.749.310-38", "planc54k@mail.com", encoder.encode("123"), "55 11 98456-4937");
 
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Teste chamado 1", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 2", "Teste chamado 2", tec1, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 3", "Teste chamado 3", tec2, cli3);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 4", "Teste chamado 4", tec3, cli3);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 5", "Teste chamado 5", tec2, cli1);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 7", "Teste chamado 6", tec1, cli5);

		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5, cli6, cli7, cli8, cli9, cli10, cli11, cli12, cli13, cli14, cli15, cli16, cli17, cli18, cli19, cli20, cli21, cli22, cli23, cli24, cli25));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
	}
}
