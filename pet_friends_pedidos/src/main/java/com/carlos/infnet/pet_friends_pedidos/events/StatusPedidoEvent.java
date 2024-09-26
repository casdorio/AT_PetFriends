package com.carlos.infnet.pet_friends_pedidos.events;

import java.io.Serializable;
import java.util.Date;

import com.carlos.infnet.pet_friends_pedidos.domains.PedidoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusPedidoEvent implements Serializable {
    private Long idPedido;
    private PedidoStatus estado;
    private Date momento = new Date(); // Inicializa com a data atual

    public StatusPedidoEvent(Long idPedido, PedidoStatus estado) {
        this.idPedido = idPedido;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "StatusPedidoEvent{" +
                "idPedido=" + idPedido +
                ", estado=" + estado +
                ", momento=" + momento +
                '}';
    }
}
