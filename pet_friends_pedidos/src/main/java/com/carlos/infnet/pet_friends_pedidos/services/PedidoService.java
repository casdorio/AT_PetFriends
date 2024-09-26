package com.carlos.infnet.pet_friends_pedidos.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.carlos.infnet.pet_friends_pedidos.domains.Pedido;
import com.carlos.infnet.pet_friends_pedidos.domains.PedidoStatus;
import com.carlos.infnet.pet_friends_pedidos.events.StatusPedidoEvent;
import com.carlos.infnet.pet_friends_pedidos.repositories.PedidoRepository;

@Service
@Slf4j
public class PedidoService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private PedidoRepository repository;

    private static final String QUEUE_NAME = "pedido_queue";

    public Pedido obterPorId(long id) {
        return repository.getReferenceById(id);
    }

    public Pedido fecharPedido(long id) {
        Pedido pedido = repository.getReferenceById(id);
        pedido.fecharPedido();
        pedido = repository.save(pedido);
        enviar(new StatusPedidoEvent(pedido.getId(), PedidoStatus.FINALIZADO));
        return pedido;
    }

    public Pedido criarPedido(Pedido pedido) {
        Pedido retorno = null;
        if (pedido != null) {
            retorno = repository.save(pedido);
            enviar(new StatusPedidoEvent(pedido.getId(), PedidoStatus.PENDENTE));
        }
        return retorno;
    }

    public void processarEvento(StatusPedidoEvent evento) {
        switch (evento.getEstado()) {
            case PENDENTE:
            log.info("Pedido criado: " + evento.getIdPedido());
                break;
            case EM_PREPARACAO:
            log.info("Pedido em preparação: " + evento.getIdPedido());
                break;
            default:
                break;
        }
    }

    private void enviar(StatusPedidoEvent estado) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, estado);
        log.info("***** Mensagem Publicada ---> " + estado);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void receber(@Payload StatusPedidoEvent payload) {
        log.info("***** Mensagem Recebida ---> " + payload);
        this.processarEvento(payload);
    }
}