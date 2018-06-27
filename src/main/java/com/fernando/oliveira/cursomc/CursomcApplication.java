package com.fernando.oliveira.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fernando.oliveira.cursomc.domain.Categoria;
import com.fernando.oliveira.cursomc.domain.Cidade;
import com.fernando.oliveira.cursomc.domain.Cliente;
import com.fernando.oliveira.cursomc.domain.Endereco;
import com.fernando.oliveira.cursomc.domain.Estado;
import com.fernando.oliveira.cursomc.domain.Produto;
import com.fernando.oliveira.cursomc.domain.enums.TipoCliente;
import com.fernando.oliveira.cursomc.repositories.CategoriaRepository;
import com.fernando.oliveira.cursomc.repositories.CidadeRepository;
import com.fernando.oliveira.cursomc.repositories.ClienteRepository;
import com.fernando.oliveira.cursomc.repositories.EnderecoRepository;
import com.fernando.oliveira.cursomc.repositories.EstadoRepository;
import com.fernando.oliveira.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Guaruja", est2);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
		Cliente cliente = new Cliente(null, "Fernando", "fer.a.oliveira19@gmail.com", "29683018882", TipoCliente.PESSOA_FISICA);
		
		cliente.getTelefones().add("20354885");
		
		Endereco end1 = new Endereco(null, "Rua Rio de Janeiro", "50", "apto 617", "Centro", "11410310", c3, cliente );
		Endereco end2 = new Endereco(null, "Rua Vicente Aprígio", "600", "casa 4", "Jardim Aurora", "08431090", c3, cliente );
		
		clienteRepository.saveAll(Arrays.asList(cliente));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
	}
}
