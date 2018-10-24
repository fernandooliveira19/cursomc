package com.fernando.oliveira.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.cursomc.domain.Cidade;
import com.fernando.oliveira.cursomc.domain.Cliente;
import com.fernando.oliveira.cursomc.domain.Endereco;
import com.fernando.oliveira.cursomc.domain.dto.ClienteDTO;
import com.fernando.oliveira.cursomc.domain.dto.ClienteNewDTO;
import com.fernando.oliveira.cursomc.domain.enums.TipoCliente;
import com.fernando.oliveira.cursomc.repositories.CidadeRepository;
import com.fernando.oliveira.cursomc.repositories.ClienteRepository;
import com.fernando.oliveira.cursomc.repositories.EnderecoRepository;
import com.fernando.oliveira.cursomc.services.exception.DataIntegrityException;
import com.fernando.oliveira.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! - id: "+ id +" ,  Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possivel excluir um cliente, pois tem pedidos relacionados");
		}
		
	}

	public List<Cliente> findAll() {
		List<Cliente> lista = repo.findAll();
		return lista;
	}
	
	public Page<Cliente> findPage(Integer page, Integer size, String direction, String orderBy){
		PageRequest list = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return repo.findAll(list);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public Cliente fromNewDTO(ClienteNewDTO dto) {
		
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()), bCryptPasswordEncoder.encode(dto.getSenha()));
		Cidade cid = cidadeRepository.getOne(dto.getIdCidade());
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(),dto.getBairro(),dto.getCep(),cid, cli);
		
		cli.getTelefones().add(dto.getTelefone1());
		
		if(dto.getTelefone2() != null) {
			cli.getTelefones().add(dto.getTelefone2());
			
		}
		if(dto.getTelefone3() != null) {
			cli.getTelefones().add(dto.getTelefone3());
			
		}
		
		cli.getEnderecos().add(end);
		
		return cli;
	}

}
