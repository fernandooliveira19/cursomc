package com.fernando.oliveira.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import com.fernando.oliveira.cursomc.domain.enums.StatusPagamento;

@Entity
public class PagamentoComCartao extends Pagamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer numeroParcelas;
	
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, Pedido pedido, StatusPagamento status, Integer numeroParcelas) {
		super(id, pedido, status);
		this.setNumeroParcelas(numeroParcelas);
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	
	
	

}
