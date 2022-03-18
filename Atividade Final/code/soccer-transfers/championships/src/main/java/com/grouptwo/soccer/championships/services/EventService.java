package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.EventConverter;
import com.grouptwo.soccer.championships.models.Event;
import com.grouptwo.soccer.championships.models.enums.EventType;
import com.grouptwo.soccer.championships.repositories.EventRepository;
import com.grouptwo.soccer.transfers.lib.responses.EventResponse;

@Service
public class EventService {

	private final EventRepository eventRepository;

	private final EventConverter eventConverter;

	public EventService(EventRepository eventRepository, EventConverter eventConverter) {
		this.eventRepository = eventRepository;
		this.eventConverter = eventConverter;
	}

	public EventConverter getEventConverter() {
		return eventConverter;
	}

	public List<EventResponse> findAll(UUID championshipId, UUID seasonId, UUID matchId) {
		List<Event> all = this.eventRepository.findAll(championshipId, seasonId, matchId);
		return all != null && !all.isEmpty() ? all.stream().map(this.eventConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	public List<EventResponse> findAllByEventType(UUID championshipId, UUID seasonId, UUID matchId, EventType eventType) {
		List<Event> all = this.eventRepository.findAllByEventType(championshipId, seasonId, matchId, eventType);
		return all != null && !all.isEmpty() ? all.stream().map(this.eventConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	public List<EventResponse> findAllByEventTypeAndPlayerId(UUID championshipId, UUID seasonId, UUID matchId, EventType eventType, UUID playerId) {
		List<Event> all = this.eventRepository.findAllByEventTypeAndPlayerId(championshipId, seasonId, matchId, eventType, playerId);
		return all != null && !all.isEmpty() ? all.stream().map(this.eventConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	@Transactional
	public EventResponse save(Event event) {
		Event saved = this.eventRepository.save(event);
		return saved != null ? this.eventConverter.fromEntityToResponse(saved) : null ;
	}
}
