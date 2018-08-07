package com.fernando.oliveira.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fernando.oliveira.cursomc.domain.Cliente;
import com.fernando.oliveira.cursomc.domain.dto.ClienteNewDTO;
import com.fernando.oliveira.cursomc.domain.enums.TipoCliente;
import com.fernando.oliveira.cursomc.repositories.ClienteRepository;
import com.fernando.oliveira.cursomc.resources.exceptions.FieldMessage;
import com.fernando.oliveira.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	
	}
	
	@Override
	public boolean isValid(ClienteNewDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		
		
		if(dto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isCPF(dto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(dto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isCNPJ(dto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cliente = repo.findByEmail(dto.getEmail());
		
		if(cliente != null) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getFieldName()).addConstraintViolation();
			
		}
		
		return list.isEmpty();
	}

}
