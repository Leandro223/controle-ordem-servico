package com.baracho.ordemservico.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baracho.ordemservico.domain.Cliente;
import com.baracho.ordemservico.domain.dtos.ClienteDTO;
import com.baracho.ordemservico.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired 
	private ClienteService clienteService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = clienteService.findById(id);
		
		return ResponseEntity.ok().body(new ClienteDTO(obj));
		
		
	}
	
	@GetMapping() 
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping 
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDto){
		Cliente newObj = clienteService.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto){
		Cliente obj = clienteService.update(id, objDto);
		
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}") 
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/clientes-por-mes")
	  public ResponseEntity<List<Map<String, Integer>>> getClientesPorMes() {
	    List<Map<String, Integer>> clientesPorMes = clienteService.getClientesPorMesTeste();
	    return ResponseEntity.ok().body(clientesPorMes);
	  }

}
