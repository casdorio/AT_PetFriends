package com.carlos.infnet.pet_friends_transporte.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carlos.infnet.pet_friends_transporte.domains.StatusTransporte;
import com.carlos.infnet.pet_friends_transporte.domains.Transporte;
import com.carlos.infnet.pet_friends_transporte.repositories.TransporteRepository;


@Service
public class TransporteService {
    private final TransporteRepository transporteRepository;

    public TransporteService(TransporteRepository transporteRepository) {
        this.transporteRepository = transporteRepository;
    }

    public Transporte criar(Transporte transporte) {
        return transporteRepository.save(transporte);
    }

    public Transporte atualizarStatus(Long pedidoId, StatusTransporte status) {
        Transporte entrega = transporteRepository.findByPedidoId(pedidoId);
        if (entrega == null) {
            throw new IllegalArgumentException("Entrega não encontrada para o pedido: " + pedidoId);
        }
        entrega.setStatus(status);
        return transporteRepository.save(entrega);
    }

    public List<Transporte> listarTransportes() {
        return transporteRepository.findAll();
    }
}
