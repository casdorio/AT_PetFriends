package com.carlos.infnet.pet_friends_pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.infnet.pet_friends_pedidos.domains.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
