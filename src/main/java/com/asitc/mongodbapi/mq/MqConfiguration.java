package com.asitc.mongodbapi.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfiguration {

	@Value("${mq.exchange}")
	private String exchangeName;

	@Value("${mq.queue}")
	private String queueName;

	@Bean
	Queue queue() {
		return new Queue(this.queueName);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(this.exchangeName);
	}

	@Bean
	Binding binding(final Queue queue, final DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(this.queueName);
	}

	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// @Bean
	// SimpleMessageListenerContainer container(final ConnectionFactory
	// connectionFactory,
	// /* final MessageListenerAdapter listenerAdapter, */ final
	// MessageConverter messageConverter) {
	// final SimpleMessageListenerContainer container = new
	// SimpleMessageListenerContainer();
	// container.setConnectionFactory(connectionFactory);
	// container.setQueueNames(this.queueName);
	// // container.setMessageListener(listenerAdapter);
	// container.setMessageConverter(messageConverter);
	// return container;
	// }

	// @Bean
	// MessageListenerAdapter listenerAdapter(final MqReceiver receiver) {
	// return new MessageListenerAdapter(receiver, "receiveMessage");
	// }
}
