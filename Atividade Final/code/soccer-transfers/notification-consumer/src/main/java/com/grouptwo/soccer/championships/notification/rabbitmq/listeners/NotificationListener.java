package com.grouptwo.soccer.championships.notification.rabbitmq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.dtos.EventDTO;

@Component
public class NotificationListener {

	@RabbitListener(queues = "events.nf")
	public void lisener(EventDTO event) {
		System.out.println("events.nf: " + event);
	}
}
