package com.asitc.mongodbapi.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqSender {

	@Value("${mq.application}")
	private String applicationName;

	@Value("${mq.exchange}")
	private String exchangeName;

	@Value("${mq.out-queue-key}")
	private String outQueueKey;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	// @Scheduled(fixedDelay = 3000L)
	public void sendMessage(final MqMessage message) {
		message.setApplication(this.applicationName);
		this.rabbitTemplate.convertAndSend(this.exchangeName, this.outQueueKey, message);
	}
}
