package com.carlos.infnet.pet_friends_pedidos.repositories;

import com.carlos.infnet.pet_friends_pedidos.domains.PedidoStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PedidoStatusTypeConverter implements AttributeConverter<PedidoStatus, String> {

    @Override
    public String convertToDatabaseColumn(PedidoStatus pedidoStatus) {
        return pedidoStatus != null ? pedidoStatus.name() : null;
    }

    @Override
    public PedidoStatus convertToEntityAttribute(String pedidoStatus) {
        return pedidoStatus != null ? PedidoStatus.valueOf(pedidoStatus) : null;
    }
}

