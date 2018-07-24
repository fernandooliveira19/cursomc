package com.fernando.oliveira.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	
	
	public ValidationError(Integer status, String message, Long timestamp) {
		super(status, message, timestamp);
		
	}



	public List<FieldMessage> getErros() {
		return erros;
	}



	public void addErros(String fieldName, String message) {
		this.erros.add(new FieldMessage(fieldName, message));
	}

	
}
