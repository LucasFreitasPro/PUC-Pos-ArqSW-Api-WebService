package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.grouptwo.soccer.championships.models.Event;
import com.grouptwo.soccer.championships.repositories.EventRepository;

@Service
public class EventService {

	private EventRepository eventRepository;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public List<Event> findAll() {
		return this.eventRepository.findAll();
	}

	public Optional<Event> findById(UUID id) {
		return this.eventRepository.findById(id);
	}
}
