package com.carlos.infnet.pet_friends_almoxarifado.domains;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Quantidade {

    private BigDecimal valor;

    public Quantidade adicionar(Quantidade outra) {
        return new Quantidade(this.valor.add(outra.valor));
    }

    public Quantidade subtrair(Quantidade outra) {
        return new Quantidade(this.valor.subtract(outra.valor));
    }
}

