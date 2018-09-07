package com.fernando.oliveira.cursomc.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fernando.oliveira.cursomc.domain.ItemPedido;
import com.fernando.oliveira.cursomc.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository repo;

	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Set<ItemPedido> itens) {
		repo.saveAll(itens);
		
	}
}
