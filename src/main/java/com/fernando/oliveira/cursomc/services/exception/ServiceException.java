package com.fernando.oliveira.cursomc.services.exception;

public class ServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String message(String message) {
		return super.getMessage();
	}

}
