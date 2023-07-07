package com.baracho.ordemservico.resources;

import java.net.URI;
import java.util.List;
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

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.RegistroUpdate;
import com.baracho.ordemservico.domain.dtos.ChamadoDTO;
import com.baracho.ordemservico.repository.RegistroUpdateRepository;
import com.baracho.ordemservico.service.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired 
	private RegistroUpdateRepository registroUpdateRepository;
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = chamadoService.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
	
	@GetMapping("/{id}/registro") 
	public ResponseEntity<List<RegistroUpdate>> getRegistroUpdate(@PathVariable("id") Integer chamadoId){ 
		if(!chamadoService.existsChamadoById(chamadoId)) {
			return ResponseEntity.notFound().build();
		}
		
		List<RegistroUpdate> registros = registroUpdateRepository.findAllByChamadoId(chamadoId);
		
		if(registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(registros);
		
		
		
	}
	
	@GetMapping 
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		
		List<Chamado> list = chamadoService.findAll();
		
		List<ChamadoDTO> listDto = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
		
		
	}
	
	@PostMapping 
	public ResponseEntity<ChamadoDTO> create (@Valid @RequestBody ChamadoDTO objDto){
		Chamado obj = chamadoService.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{/id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}") 
	public ResponseEntity<ChamadoDTO> update (@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDto){
		Chamado newObj = chamadoService.update(id, objDto);
		
		return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	}
	
	
	@DeleteMapping(value="/{id}") 
	public ResponseEntity<ChamadoDTO> delete (@PathVariable Integer id){
		chamadoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
