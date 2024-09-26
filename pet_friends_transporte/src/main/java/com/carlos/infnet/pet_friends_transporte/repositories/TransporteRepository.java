package com.carlos.infnet.pet_friends_transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.infnet.pet_friends_transporte.domains.Transporte;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long>{
    Transporte findByPedidoId(Long pedidoId);
}
