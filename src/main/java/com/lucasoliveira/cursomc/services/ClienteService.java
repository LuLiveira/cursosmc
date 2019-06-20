package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.domain.Cliente;
import com.lucasoliveira.cursomc.domain.dto.ClienteDTO;
import com.lucasoliveira.cursomc.repositories.ClienteRepository;
import com.lucasoliveira.cursomc.services.exception.DataIntegrityException;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente find(Integer id){

        Cliente cliente = clienteRepository.findOne(id);
        if(cliente == null){
            throw new ObjectNotFoundException("Objeto não encontrado id: " + id + " Tipo: " + Cliente.class.getName());
        }

        return cliente;
    }

    public Cliente insert (Cliente cliente){

        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente){

        Cliente newCliente = find(cliente.getId());
        updateData(newCliente, cliente);

        return clienteRepository.save(newCliente);
    }

    public void delete (Integer id){
        try {
            clienteRepository.delete(id);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir o cliente");
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void updateData(Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
