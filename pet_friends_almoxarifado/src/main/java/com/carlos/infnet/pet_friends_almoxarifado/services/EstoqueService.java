package com.carlos.infnet.pet_friends_almoxarifado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.carlos.infnet.pet_friends_almoxarifado.configurations.PedidoRabbitMQConfig;
import com.carlos.infnet.pet_friends_almoxarifado.domains.Estoque;
import com.carlos.infnet.pet_friends_almoxarifado.domains.MovimentoEstoque;
import com.carlos.infnet.pet_friends_almoxarifado.domains.Quantidade;
import com.carlos.infnet.pet_friends_almoxarifado.domains.TipoMovimento;
import com.carlos.infnet.pet_friends_almoxarifado.repositories.EstoqueRepository;
import com.carlos.infnet.pet_friends_almoxarifado.repositories.MovimentoEstoqueRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper
    
    ;
    public Estoque criar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Estoque atualizarQuantidade(Long produtoId, Quantidade quantidade, TipoMovimento tipoMovimento) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        if (estoque == null) {
            throw new IllegalArgumentException("Produto não encontrado no estoque");
        }

        Quantidade novaQuantidade;
        if (tipoMovimento == TipoMovimento.ENTRADA) {
            novaQuantidade = estoque.getQuantidade().adicionar(quantidade);
        } else {
            novaQuantidade = estoque.getQuantidade().subtrair(quantidade);
            if (novaQuantidade.getValor().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Estoque insuficiente");
            }
        }

        estoque.setQuantidade(novaQuantidade);
        estoqueRepository.save(estoque);

        MovimentoEstoque movimento = new MovimentoEstoque(estoque, quantidade, tipoMovimento);
        movimentoEstoqueRepository.save(movimento);

        enviarMensagemAtualizacaoEstoque(estoque);

        return estoque;
    }

    public List<Estoque> obterTodosEstoques() {
        return estoqueRepository.findAll();
    }

    private void enviarMensagemAtualizacaoEstoque(Estoque estoque) {
        try {
            String mensagem = objectMapper.writeValueAsString(estoque);
            rabbitTemplate.convertAndSend(PedidoRabbitMQConfig.NOME_QUEUE_PEDIDOS, mensagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar a mensagem de atualização de estoque", e);
        }
    }
}
