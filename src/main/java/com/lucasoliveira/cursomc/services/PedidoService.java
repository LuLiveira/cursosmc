package com.lucasoliveira.cursomc.services;


import com.lucasoliveira.cursomc.domain.Pedido;
import com.lucasoliveira.cursomc.repositories.PedidoRepository;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido find(Integer id){

        Pedido pedido = pedidoRepository.findOne(id);
        if(pedido == null){
            throw new ObjectNotFoundException("Objeto não encontrado id: " + id + " Tipo: " + Pedido.class.getName());
        }

        return pedido;
    }
}
