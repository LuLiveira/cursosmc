package com.lucasoliveira.cursomc.services;


import com.lucasoliveira.cursomc.domain.ItemPedido;
import com.lucasoliveira.cursomc.domain.PagamentoComBoleto;
import com.lucasoliveira.cursomc.domain.Pedido;
import com.lucasoliveira.cursomc.domain.enums.EstadoPagamento;
import com.lucasoliveira.cursomc.repositories.ItemPedidoRepository;
import com.lucasoliveira.cursomc.repositories.PagamentoRepository;
import com.lucasoliveira.cursomc.repositories.PedidoRepository;
import com.lucasoliveira.cursomc.repositories.ProdutoRepository;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
            itemPedido.setPreco(produtoRepository.findOne(itemPedido.getProduto().getId()).getPreco());
            itemPedido.setPedido(pedido);
        }
        itemPedidoRepository.save(pedido.getItens());

        return pedido;
    }
}
