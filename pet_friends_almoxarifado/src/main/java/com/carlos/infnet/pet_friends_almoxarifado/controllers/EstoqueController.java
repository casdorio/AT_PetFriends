package com.carlos.infnet.pet_friends_almoxarifado.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.carlos.infnet.pet_friends_almoxarifado.domains.Estoque;
import com.carlos.infnet.pet_friends_almoxarifado.domains.Quantidade;
import com.carlos.infnet.pet_friends_almoxarifado.domains.TipoMovimento;
import com.carlos.infnet.pet_friends_almoxarifado.services.EstoqueService;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public Estoque criar(@RequestBody Estoque estoque) {
        return estoqueService.criar(estoque);
    }

    @PatchMapping("/{produtoId}/quantidade")
    public Estoque atualizarQuantidade(
            @PathVariable Long produtoId,
            @RequestParam BigDecimal quantidade,
            @RequestParam TipoMovimento tipoMovimento) {
        return estoqueService.atualizarQuantidade(produtoId, new Quantidade(quantidade), tipoMovimento);
    }

    @GetMapping
    public List<Estoque> listarEstoques() {
        return estoqueService.obterTodosEstoques();
    }
}