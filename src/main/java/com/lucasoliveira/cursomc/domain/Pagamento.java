package com.lucasoliveira.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasoliveira.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private Integer estadoPagamento;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento(){}

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        super();
        this.id = id;
                              //se estadoPagamento == null então estadoPagamento recebe null senão estadoPagamento recebe estado.getCod();
        this.estadoPagamento = (estadoPagamento == null) ? null : estado.getCod();
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estadoPagamento);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estadoPagamento = estado.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
