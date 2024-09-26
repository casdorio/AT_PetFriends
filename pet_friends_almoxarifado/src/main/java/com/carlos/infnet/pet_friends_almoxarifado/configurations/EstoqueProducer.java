package com.carlos.infnet.pet_friends_almoxarifado.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.carlos.infnet.pet_friends_almoxarifado.domains.Estoque;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    public void send(Estoque estoque) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(estoque);
        amqpTemplate.convertAndSend("estoque-exc","estoque-rk", message);
    }
}
