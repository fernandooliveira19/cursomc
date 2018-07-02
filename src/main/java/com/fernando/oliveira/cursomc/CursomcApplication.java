package com.fernando.oliveira.cursomc;

import java.text.SimpleDateFormat;
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
import com.fernando.oliveira.cursomc.domain.ItemPedido;
import com.fernando.oliveira.cursomc.domain.Pagamento;
import com.fernando.oliveira.cursomc.domain.PagamentoComBoleto;
import com.fernando.oliveira.cursomc.domain.Pedido;
import com.fernando.oliveira.cursomc.domain.Produto;
import com.fernando.oliveira.cursomc.domain.enums.StatusPagamento;
import com.fernando.oliveira.cursomc.domain.enums.TipoCliente;
import com.fernando.oliveira.cursomc.repositories.CategoriaRepository;
import com.fernando.oliveira.cursomc.repositories.CidadeRepository;
import com.fernando.oliveira.cursomc.repositories.ClienteRepository;
import com.fernando.oliveira.cursomc.repositories.EnderecoRepository;
import com.fernando.oliveira.cursomc.repositories.EstadoRepository;
import com.fernando.oliveira.cursomc.repositories.ItemPedidoRepository;
import com.fernando.oliveira.cursomc.repositories.PagamentoRepository;
import com.fernando.oliveira.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32:00"), end1, cliente);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35:00"), end2,  cliente);
		
		Pagamento pag1 = new PagamentoComBoleto(null, ped1, StatusPagamento.PENDENTE, sdf.parse("30/11/2017 00:00:00"),sdf.parse("30/12/2017 10:32:00"));
		Pagamento pag2 = new PagamentoComBoleto(null, ped2, StatusPagamento.QUITADO, sdf.parse("20/01/2017 00:00:00"),sdf.parse("20/22/2017 10:32:00"));
		
		ped1.setPagamento(pag1);
		ped2.setPagamento(pag2);
		
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		ItemPedido ip1 = new ItemPedido(p2, ped1, 15.00, 2, 1200.00);
		ItemPedido ip2 = new ItemPedido(p3, ped2, 150.00, 6, 800.00);
		ItemPedido ip3 = new ItemPedido(p1, ped1, 180.00, 4, 2700.00);
		
		ped1.getItens().addAll(Arrays.asList(ip2,ip3));
		ped2.getItens().addAll(Arrays.asList(ip1,ip3));
		
		p1.getItens().addAll(Arrays.asList(ip3,ip1));
		p2.getItens().addAll(Arrays.asList(ip2,ip3));
		p3.getItens().addAll(Arrays.asList(ip1,ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}
}
