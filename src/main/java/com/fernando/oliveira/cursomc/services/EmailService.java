package com.fernando.oliveira.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.fernando.oliveira.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
