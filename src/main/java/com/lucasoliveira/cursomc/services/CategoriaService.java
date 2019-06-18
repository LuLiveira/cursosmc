package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.domain.Categoria;
import com.lucasoliveira.cursomc.repositories.CategoriaRepository;
import com.lucasoliveira.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id){

        Categoria categoria = categoriaRepository.findOne(id);
        if(categoria == null){
            throw new ObjectNotFoundException("Objeto não encontrado id: " + id + " Tipo: " + Categoria.class.getName());
        }
        return categoria;
    }

    public Categoria insert (Categoria categoria){
        return categoriaRepository.save(categoria);
    }
}
