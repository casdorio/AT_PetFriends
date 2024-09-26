package com.carlos.infnet.pet_friends_pedidos.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Preco implements Serializable {

    private final BigDecimal quantia;

    public Preco somar(Preco outro) {
        if (outro == null) {
            throw new IllegalArgumentException("Outro valor não pode ser nulo");
        }
        return new Preco(this.quantia.add(outro.getQuantia()));
    }

    public Preco subtrair(Preco outro) {
        if (outro == null) {
            throw new IllegalArgumentException("Outro valor não pode ser nulo");
        }
        return new Preco(this.quantia.subtract(outro.getQuantia()));
    }
}
