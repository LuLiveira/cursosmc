package com.lucasoliveira.cursomc.resources;

import com.lucasoliveira.cursomc.domain.Cliente;
import com.lucasoliveira.cursomc.domain.dto.ClienteDTO;
import com.lucasoliveira.cursomc.domain.dto.ClienteNewDTO;
import com.lucasoliveira.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id){

        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
        Cliente cliente = clienteService.fromDTO(clienteNewDTO);
        cliente = clienteService.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete (@PathVariable Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = clienteService.findAll();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ){
        Page<Cliente> pagi = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> pagiDto = pagi.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(pagiDto);
    }
}
