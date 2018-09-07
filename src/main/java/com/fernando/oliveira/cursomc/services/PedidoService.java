package com.fernando.oliveira.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernando.oliveira.cursomc.domain.ItemPedido;
import com.fernando.oliveira.cursomc.domain.Pagamento;
import com.fernando.oliveira.cursomc.domain.PagamentoComBoleto;
import com.fernando.oliveira.cursomc.domain.Pedido;
import com.fernando.oliveira.cursomc.domain.Produto;
import com.fernando.oliveira.cursomc.domain.enums.StatusPagamento;
import com.fernando.oliveira.cursomc.repositories.PedidoRepository;
import com.fernando.oliveira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! - id: "+ id +" ,  Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setStatus(StatusPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		Pagamento pagamento = pagamentoService.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			
			if(ip.getProduto() != null) {
				Produto produto = produtoService.find(ip.getProduto().getId());
				ip.setPreco(produto.getPreco());
			}
			ip.setPedido(obj);
		}
		
		itemPedidoService.save(obj.getItens());
		obj.setPagamento(pagamento);
		
		return obj;
	}
}
