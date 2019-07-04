package com.lucasoliveira.cursomc.repositories;

import com.lucasoliveira.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    /**
     * Método que retorna todos os estados ordenados por Nome.
     * @return
     */
    @Transactional(readOnly = true)
    public List<Estado>  findAllByOrderByNome();
}
