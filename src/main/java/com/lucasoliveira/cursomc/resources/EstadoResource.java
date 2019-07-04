package com.lucasoliveira.cursomc.resources;

import com.lucasoliveira.cursomc.domain.Cidade;
import com.lucasoliveira.cursomc.domain.Estado;
import com.lucasoliveira.cursomc.domain.dto.CidadeDTO;
import com.lucasoliveira.cursomc.domain.dto.EstadoDTO;
import com.lucasoliveira.cursomc.services.CidadeService;
import com.lucasoliveira.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> estadoDTOList = list.stream().map(x -> new EstadoDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadoDTOList);
    }

    @RequestMapping(value = "/{id}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer id){
        List<Cidade> list = cidadeService.findByEstado(id);
        List<CidadeDTO> cidadeDTOList = list.stream().map(x -> new CidadeDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadeDTOList);

    }
}
