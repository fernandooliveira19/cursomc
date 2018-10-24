package com.fernando.oliveira.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.fernando.oliveira.cursomc.domain.enums.Perfil;
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

@Service
public class DBService {
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
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantializeDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Perfumaria");
		Categoria cat5 = new Categoria(null, "Brinquedos");
		Categoria cat6 = new Categoria(null, "Auto");
		Categoria cat7 = new Categoria(null, "Peixes");
		Categoria cat8 = new Categoria(null, "Padaria");
		Categoria cat9 = new Categoria(null, "Açougue");
		Categoria cat10 = new Categoria(null, "Eletros");
		
		
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "TV", 999.00);
		Produto p5 = new Produto(null, "Radio", 400.00);
		Produto p6 = new Produto(null, "Aspirador", 280.00);
		Produto p7 = new Produto(null, "Shampoo", 15.00);
		Produto p8 = new Produto(null, "Carne", 20.00);
		Produto p9 = new Produto(null, "Bicicleta", 180.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
//		cat3.getProdutos().addAll(Arrays.asList(p2));
		cat4.getProdutos().addAll(Arrays.asList(p7));
		cat5.getProdutos().addAll(Arrays.asList(p9));
//		cat6.getProdutos().addAll(Arrays.asList(p2));
//		cat7.getProdutos().addAll(Arrays.asList(p2));
//		cat8.getProdutos().addAll(Arrays.asList(p2));
		cat9.getProdutos().addAll(Arrays.asList(p8));
		cat10.getProdutos().addAll(Arrays.asList(p4,p5,p6));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p4.getCategorias().addAll(Arrays.asList(cat10));
		p5.getCategorias().addAll(Arrays.asList(cat10));
		p6.getCategorias().addAll(Arrays.asList(cat10));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat9));
		p9.getCategorias().addAll(Arrays.asList(cat5));
		
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3, cat4,cat5,cat6, cat7,cat8, cat9,cat10));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Guaruja", est2);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
		Cliente cliente = new Cliente(null, "Fernando", "fer.a.oliveira19@gmail.com", "29683018882", TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cliente.getTelefones().add("20354885");
		cliente.addPerfil(Perfil.ADMIN);
		
		Cliente cliente2 = new Cliente(null, "Giovanna", "gigiovanna1209@gmail.com", "30466163843", TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cliente2.getTelefones().add("983116777");
		
		
		Endereco end1 = new Endereco(null, "Rua Rio de Janeiro", "50", "apto 617", "Centro", "11410310", c3, cliente );
		Endereco end2 = new Endereco(null, "Rua Vicente Aprígio", "600", "casa 4", "Jardim Aurora", "08431090", c3, cliente );
		
		clienteRepository.saveAll(Arrays.asList(cliente, cliente2));
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
