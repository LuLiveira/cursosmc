package com.lucasoliveira.cursomc.repositories;

import com.lucasoliveira.cursomc.domain.Categoria;
import com.lucasoliveira.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    Page<Produto>search(@Param("nome") String nome, @Param("categorias") List<Categoria>categorias, Pageable pageRequest);

    /** Método alternativo para o método SEARCH acima.
        Ambos fazem a mesma coisa porém a forma abaixo dispensa a necessidade do comentário QUERY() por utilizar uma nomenclatura do Spring Data
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(Dtring nome, List<Categoria>categorias, Pageable pageRequest);
    */

}
