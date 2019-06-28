package com.lucasoliveira.cursomc.services;


import com.lucasoliveira.cursomc.domain.Cliente;
import com.lucasoliveira.cursomc.domain.ItemPedido;
import com.lucasoliveira.cursomc.domain.PagamentoComBoleto;
import com.lucasoliveira.cursomc.domain.Pedido;
import com.lucasoliveira.cursomc.domain.enums.EstadoPagamento;
import com.lucasoliveira.cursomc.repositories.*;
import com.lucasoliveira.cursomc.security.UserSpringSecurity;
import com.lucasoliveira.cursomc.services.exception.AuthorizationException;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id){

        Pedido pedido = pedidoRepository.findOne(id);
        if(pedido == null){
            throw new ObjectNotFoundException("Objeto não encontrado id: " + id + " Tipo: " + Pedido.class.getName());
        }
        return pedido;
    }

    public Pedido insert(Pedido pedido){
        //pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteRepository.findOne(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for(ItemPedido itemPedido : pedido.getItens()){
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoRepository.findOne(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(pedido);
        }
        itemPedidoRepository.save(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);

        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSpringSecurity user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteRepository.findOne(user.getId());
         return pedidoRepository.findByCliente(cliente, pageRequest);

    }
}
