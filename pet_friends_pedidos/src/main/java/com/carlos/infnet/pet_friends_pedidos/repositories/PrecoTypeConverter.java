package com.carlos.infnet.pet_friends_pedidos.repositories;

import java.math.BigDecimal;

import com.carlos.infnet.pet_friends_pedidos.domains.Preco;

import jakarta.persistence.AttributeConverter;

public class PrecoTypeConverter  implements AttributeConverter<Preco, BigDecimal> {
@Override
    public BigDecimal convertToDatabaseColumn(Preco preco) {
        return preco.getQuantia();
    }

    @Override
    public Preco convertToEntityAttribute(BigDecimal quantia) {
        return new Preco(quantia);
    }
}
