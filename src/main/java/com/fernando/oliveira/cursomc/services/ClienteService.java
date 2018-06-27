package com.fernando.oliveira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.cursomc.domain.Cliente;
import com.fernando.oliveira.cursomc.repositories.ClienteRepository;
import com.fernando.oliveira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! - id: "+ id +" ,  Tipo: " + Cliente.class.getName()));
	}
}
