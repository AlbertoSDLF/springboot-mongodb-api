package com.asitc.mongodbapi.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.asitc.mongodbapi.repository.car.Car;
import com.asitc.mongodbapi.repository.car.CarRepository;
import com.asitc.mongodbapi.repository.person.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqListener {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private PersonRepository personRepository;

	@Value("${mq.application}")
	private String applicationName;

	@RabbitListener(queues = "neo4j.queue")
	public void receiveMessage(final MqMessage message) {
		log.info(message.toString());
		final Iterable<Car> cars = this.carRepository.findAll();
		log.info(Long.toString(cars.spliterator().getExactSizeIfKnown()));
		if (this.applicationName != message.getApplication()) {
		}
	}
}
