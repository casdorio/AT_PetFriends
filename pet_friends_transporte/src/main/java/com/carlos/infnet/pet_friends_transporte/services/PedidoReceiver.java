package com.carlos.infnet.pet_friends_transporte.services;

import org.springframework.stereotype.Service;
import com.carlos.infnet.pet_friends_transporte.domains.Endereco;
import com.carlos.infnet.pet_friends_transporte.domains.Transporte;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PedidoReceiver {

    private final TransporteService transporteService;
    private final ObjectMapper objectMapper;

    public PedidoReceiver(TransporteService transporteService, ObjectMapper objectMapper) {
        this.transporteService = transporteService;
        this.objectMapper = objectMapper;
    }

    public void receberEventoPedido(String message) {
        try {
            Map<String, Object> evento = objectMapper.readValue(message, new TypeReference<>() {});
            validarEvento(evento);
            Transporte transporte = criarTransporte(evento);
            transporteService.criar(transporte);
            log.info("Transporte criado: {}", evento);
        } catch (Exception e) {
            log.error("Erro ao processar o pedido: {}", e.getMessage(), e);
        }
    }
    
    private void validarEvento(Map<String, Object> evento) {
        if (evento.get("pedidoId") == null || evento.get("rua") == null ||
            evento.get("numero") == null || evento.get("cidade") == null ||
            evento.get("estado") == null || evento.get("cep") == null) {
            throw new IllegalArgumentException("Dados do evento inválidos: " + evento);
        }
    }

    private Transporte criarTransporte(Map<String, Object> evento) {
        Long pedidoId = Long.valueOf(evento.get("pedidoId").toString());
        Endereco enderecoEntrega = new Endereco(
            evento.get("rua").toString(),
            evento.get("numero").toString(),
            evento.get("cidade").toString(),
            evento.get("estado").toString(),
            evento.get("cep").toString()
        );
        return new Transporte(pedidoId, enderecoEntrega);
    }


    
}
