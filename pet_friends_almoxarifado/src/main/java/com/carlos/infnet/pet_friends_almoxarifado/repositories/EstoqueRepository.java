package com.carlos.infnet.pet_friends_almoxarifado.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carlos.infnet.pet_friends_almoxarifado.domains.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
    Estoque findByProdutoId(Long produtoId);
}
