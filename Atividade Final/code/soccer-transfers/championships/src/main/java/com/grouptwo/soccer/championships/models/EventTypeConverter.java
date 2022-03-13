package com.grouptwo.soccer.championships.models;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EventTypeConverter implements AttributeConverter<EventType, String> {

	@Override
	public String convertToDatabaseColumn(EventType eventType) {
		return eventType == null ? null : eventType.getCode();
	}

	@Override
	public EventType convertToEntityAttribute(String code) {
		return Stream.of(EventType.values())
				.filter(e -> e.getCode().equals(code))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
