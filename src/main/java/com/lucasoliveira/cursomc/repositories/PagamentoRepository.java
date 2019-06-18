package com.lucasoliveira.cursomc.repositories;

import com.lucasoliveira.cursomc.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository  extends JpaRepository<Pagamento, Integer> {
}
