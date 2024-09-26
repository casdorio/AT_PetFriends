package com.carlos.infnet.pet_friends_transporte.controllers;

import org.springframework.web.bind.annotation.*;

import com.carlos.infnet.pet_friends_transporte.domains.StatusTransporte;
import com.carlos.infnet.pet_friends_transporte.domains.Transporte;
import com.carlos.infnet.pet_friends_transporte.services.TransporteService;

import java.util.List;

@RestController
@RequestMapping("/transportes")
public class TransporteController {

    private final TransporteService transporteService;

    public TransporteController(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @PostMapping
    public Transporte criar(@RequestBody Transporte transporte) {
        return transporteService.criar(transporte);
    }

    @PatchMapping("/{pedidoId}/status")
    public Transporte atualizarStatus(@PathVariable Long pedidoId, @RequestParam StatusTransporte status) {
        return transporteService.atualizarStatus(pedidoId, status);
    }

    @GetMapping
    public List<Transporte> listarTransportes() {
        return transporteService.listarTransportes();
    }
}
