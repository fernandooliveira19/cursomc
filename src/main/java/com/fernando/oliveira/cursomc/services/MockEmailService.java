package com.fernando.oliveira.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email");
		LOG.info("remetente: " +msg.getFrom());
		
		String[] to = msg.getTo();
		if(to != null) {
			
			LOG.info("destinatario: " +to[0]);
		}
		
		
		LOG.info("texto email: " +msg.getText());
		
	}

	
	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email HTML");
//		LOG.info("remetente: " +msg.getFrom());
		
//		String[] to = msg.getTo();
//		if(to != null) {
//			
//			LOG.info("destinatario: " +to[0]);
//		}
//		
//		
//		LOG.info("texto email: " +msg.getText());
		
	}

}
