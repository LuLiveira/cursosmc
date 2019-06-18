package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.domain.Categoria;
import com.lucasoliveira.cursomc.repositories.CategoriaRepository;
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

    public Categoria update(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void delete (Integer id){
        try {
            categoriaRepository.delete(id);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }
}
