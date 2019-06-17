package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.domain.Categoria;
import com.lucasoliveira.cursomc.domain.Cliente;
import com.lucasoliveira.cursomc.repositories.CategoriaRepository;
import com.lucasoliveira.cursomc.repositories.ClienteRepository;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Integer id){

        Cliente cliente = clienteRepository.findOne(id);
        if(cliente == null){
            throw new ObjectNotFoundException("Objeto não encontrado id: " + id + " Tipo: " + Cliente.class.getName());
        }

        return cliente;
    }
}
