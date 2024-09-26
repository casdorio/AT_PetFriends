package com.carlos.infnet.pet_friends_almoxarifado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.infnet.pet_friends_almoxarifado.domains.MovimentoEstoque;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long>{

}
