package com.grouptwo.soccer.championships.events.consumer.configs;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

//@Configuration
public class RabbitMqConfig {

//	@Bean
	public CachingConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

//	@Bean
	public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
