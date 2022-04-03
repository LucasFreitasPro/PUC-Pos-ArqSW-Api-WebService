package com.grouptwo.soccer.championships.notification.configs;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public CachingConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

	@Bean
	public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
