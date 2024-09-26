package com.carlos.infnet.pet_friends_almoxarifado.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.carlos.infnet.pet_friends_almoxarifado.domains.Quantidade;
import com.carlos.infnet.pet_friends_almoxarifado.domains.TipoMovimento;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PedidoEventReceiver {

    private final EstoqueService estoqueService;
    private final ObjectMapper objectMapper;

    public PedidoEventReceiver(EstoqueService estoqueService, ObjectMapper objectMapper) {
        this.estoqueService = estoqueService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "pedidos.queue")
    public void receberEventoPedido(String message) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> evento = objectMapper.readValue(message, Map.class);

            Long produtoId = Long.valueOf(evento.get("produtoId").toString());
            Double quantidade = Double.valueOf(evento.get("quantidade").toString());
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);

            estoqueService.atualizarQuantidade(produtoId, new Quantidade(quantidadeBigDecimal), TipoMovimento.SAIDA);
            log.info("Pedido recebido: Produto ID: {}, Quantidade: {}", produtoId, quantidadeBigDecimal);
        } catch (Exception e) {
            log.error("Erro no pedido: {}", e.getMessage(), e);
        }
    }
}
