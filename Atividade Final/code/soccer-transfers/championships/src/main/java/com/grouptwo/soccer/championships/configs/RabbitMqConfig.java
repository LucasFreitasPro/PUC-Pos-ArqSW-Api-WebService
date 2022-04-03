package com.grouptwo.soccer.championships.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange("events.exchange");
	}

	@Bean
	public Queue eventsDbQueue() {
		return new Queue("events.db", true);
	}

	@Bean
	public Queue eventsNfQueue() {
		return new Queue("events.nf");
	}

	@Bean
	public Binding binding1(Queue eventsDbQueue, FanoutExchange fanout) {
		return BindingBuilder.bind(eventsDbQueue).to(fanout);
	}

	@Bean
	public Binding binding2(Queue eventsNfQueue, FanoutExchange fanout) {
		return BindingBuilder.bind(eventsNfQueue).to(fanout);
	}
}
