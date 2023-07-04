package com.baracho.ordemservico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baracho.ordemservico.domain.Chamado;
import com.baracho.ordemservico.domain.dtos.ChamadoDTO;
import com.baracho.ordemservico.service.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = chamadoService.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}

}
