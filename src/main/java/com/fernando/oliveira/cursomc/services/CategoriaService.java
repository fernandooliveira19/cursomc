package com.fernando.oliveira.cursomc.services;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.cursomc.domain.Categoria;
import com.fernando.oliveira.cursomc.repositories.CategoriaRepository;
import com.fernando.oliveira.cursomc.services.exception.DataIntegrityException;
import com.fernando.oliveira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {

		Optional<Categoria> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! - id: " + id + " ,  Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possivel excluir uma categoria, pois tem um produto associado");
		}
		
	}
}
