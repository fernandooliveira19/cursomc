package com.fernando.oliveira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fernando.oliveira.cursomc.domain.Pagamento;
import com.fernando.oliveira.cursomc.repositories.PagamentoRepository;
import com.fernando.oliveira.cursomc.services.exception.DataIntegrityException;
import com.fernando.oliveira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repo;

	public Pagamento find(Integer id) {

		Optional<Pagamento> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! - id: " + id + " ,  Tipo: " + Pagamento.class.getName()));
	}

	@Transactional
	public Pagamento save(Pagamento obj) {
//		obj.setId(null);
		return repo.save(obj);
	}

	public Pagamento update(Pagamento obj) {
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

	public List<Pagamento> findAll() {
		List<Pagamento> lista = repo.findAll();
		return lista;
	}
	
	public Page<Pagamento> findPage(Integer page, Integer size, String direction, String orderBy){
		PageRequest list = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repo.findAll(list);
	}
	
	
}
