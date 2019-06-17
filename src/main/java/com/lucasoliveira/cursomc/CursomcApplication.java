package com.lucasoliveira.cursomc;

import com.lucasoliveira.cursomc.domain.*;
import com.lucasoliveira.cursomc.domain.enums.TipoCliente;
import com.lucasoliveira.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

		//Instanciando as categorias
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		//Instanciando os produtos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2));

		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1, cat2));
		p3.setCategorias(Arrays.asList(cat1));

		categoriaRepository.save(Arrays.asList(cat1, cat2));
		produtoRepository.save(Arrays.asList(p1, p2, p3));

		//Instanciando os estados
		Estado est1 = new Estado("Minas Gerais");
		Estado est2 = new Estado("São Paulo");

		//Instanciando as cidades
		Cidade c1 = new Cidade("Uberlândia", est1);
		Cidade c2 = new Cidade("São Paulo", est2);
		Cidade c3 = new Cidade("Campinas", est2);

		est1.setCidade(Arrays.asList(c1));
		est2.setCidade(Arrays.asList(c2, c3));

		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente("Maria da Silva", "maria@gmail.com", "36378988809", TipoCliente.PESSSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("333333333", "444444444"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "11089200", cli1, c1 );
		Endereco e2 = new Endereco(null,"Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		//cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.setEnderecos(Arrays.asList(e1, e2));

		clienteRepository.save(cli1);
		enderecoRepository.save(Arrays.asList(e1, e2));

	}
}
