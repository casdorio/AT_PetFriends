package com.carlos.infnet.pet_friends_pedidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.infnet.pet_friends_pedidos.domains.Pedido;
import com.carlos.infnet.pet_friends_pedidos.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService service;
    
    @GetMapping("/{id}")
    public Pedido obterPorId(@PathVariable(value = "id") long id){ 
        return service.obterPorId(id);
    }
    
    @PatchMapping("/fechar-pedido/{id}")
    public Pedido fecharPedido(@PathVariable(value = "id") long id){ 
        return service.fecharPedido(id);
    }
}
