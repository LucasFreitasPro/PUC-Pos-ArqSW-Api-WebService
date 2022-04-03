package com.grouptwo.soccer.championships.converters;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Event;
import com.grouptwo.soccer.transfers.lib.requests.EventRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.EventResponse;

@Component
public class EventConverter {

	private final SubstitutionEventConverter substitutionEventConverter;

	public EventConverter(SubstitutionEventConverter substitutionEventConverter) {
		this.substitutionEventConverter = substitutionEventConverter;
	}

	public EventResponse fromEntityToResponse(Event entity) {
		EventResponse response = new EventResponse();
		BeanUtils.copyProperties(entity, response);
		if (entity.getSubstitutionEvent() != null) {
			response.setSubstitutionEvent(this.substitutionEventConverter.fromEntityToResponse(entity.getSubstitutionEvent()));
		}
		return response;
	}

	public Event fromRequestToEntity(@Valid EventRegisteringRequest request) {
		Event entity = new Event();
		BeanUtils.copyProperties(request, entity);
		entity.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
		return entity;
	}

	public Event fromResponseToEntity(EventResponse response) {
		Event entity = new Event();
		BeanUtils.copyProperties(response, entity);
		return entity;
	}
}
