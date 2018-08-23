package com.fernando.oliveira.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.cursomc.domain.Produto;
import com.fernando.oliveira.cursomc.domain.dto.ProdutoDTO;
import com.fernando.oliveira.cursomc.resources.utils.URL;
import com.fernando.oliveira.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource{

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Produto obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name="nome", defaultValue="" ) String nome,
			@RequestParam(name="categorias", defaultValue="" ) String categorias,
			@RequestParam(name="page", defaultValue="0" ) Integer page, 
			@RequestParam(name="size", defaultValue="24" ) Integer size,
			@RequestParam(name="direction", defaultValue="ASC" ) String direction,
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		
		Page<Produto> list = service.search(nomeDecoded, ids,page, size, direction, orderBy);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}

}
