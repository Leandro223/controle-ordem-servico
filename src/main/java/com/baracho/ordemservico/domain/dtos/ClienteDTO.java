package com.baracho.ordemservico.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ClienteDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotNull(message = "o campo NOME é requerido ")
	protected String nome;
	
	@NotNull(message = "o campo CPF é requerido ")
	protected String cpf;
	
	@NotNull(message = "o campo EMAIL é requerido ")
	protected String email;
	
	@NotNull(message = "o campo SENHA é requerido ")
	protected String senha;
	
	protected String telefone;
	
	protected Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ClienteDTO() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(Cliente obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.telefone = obj.getTelefone();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCod()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCod());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	
	
	

}
