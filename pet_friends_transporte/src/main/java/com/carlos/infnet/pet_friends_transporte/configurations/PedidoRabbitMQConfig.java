package com.carlos.infnet.pet_friends_transporte.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.carlos.infnet.pet_friends_transporte.services.PedidoReceiver;

@Configuration
public class PedidoRabbitMQConfig {

    public static final String NOME_QUEUE_PEDIDOS = "queue.transportes";

    @Bean
    public Queue queueTransportes() {
        return new Queue(NOME_QUEUE_PEDIDOS, true);
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory,
                                                           MessageListenerAdapter adapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(NOME_QUEUE_PEDIDOS);
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(PedidoReceiver pedidoReceiver) {
        return new MessageListenerAdapter(pedidoReceiver, "receberEventoPedido");
    }
}
