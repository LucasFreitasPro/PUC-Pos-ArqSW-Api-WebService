package com.grouptwo.soccer.championships.events.consumer.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Event;
import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.championships.models.SubstitutionEvent;
import com.grouptwo.soccer.championships.models.enums.EventType;
import com.grouptwo.soccer.championships.models.enums.Half;
import com.grouptwo.soccer.championships.services.EventService;
import com.grouptwo.soccer.championships.services.SubstitutionEventService;
import com.grouptwo.soccer.transfers.lib.dtos.EventDTO;

@Component
public class EventsListener {

	private final EventService eventService;

	private final SubstitutionEventService substitutionEventService;

	public EventsListener(EventService eventService, SubstitutionEventService substitutionEventService) {
		this.eventService = eventService;
		this.substitutionEventService = substitutionEventService;
	}

	@RabbitListener(queues = "events.db")
	public void listener(EventDTO dto) {
		System.out.println("Processing payload: " + dto);

		Event entity = new Event();
		entity.setCreatedAt(dto.getCreatedAt());
		entity.setEventType(EventType.fromDesc(dto.getEventName()));
		entity.setHalf(Half.fromDesc(dto.getHalfName()));
		entity.setMatch(new Match(dto.getMatchId()));
		entity.setPlayerId(dto.getPlayerId());
		entity.setStoppageTime(dto.getStoppageTime());
		entity.setTimeInHalf(dto.getTimeInHalf());
		 this.eventService.save(entity);

		if (dto.getSubstitutionEvent() != null) {
			SubstitutionEvent substitutionEvent = new SubstitutionEvent();
			substitutionEvent.setEvent(entity);
			substitutionEvent.setPlayerIdIn(dto.getSubstitutionEvent().getPlayerIdIn());
			substitutionEvent.setPlayerIdOut(dto.getSubstitutionEvent().getPlayerIdOut());
			substitutionEvent.setTeamId(dto.getSubstitutionEvent().getTeamId());
			 this.substitutionEventService.save(substitutionEvent);
		}
	}
}
