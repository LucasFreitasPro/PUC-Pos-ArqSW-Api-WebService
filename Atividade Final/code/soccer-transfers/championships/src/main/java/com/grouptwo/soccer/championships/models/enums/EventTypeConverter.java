package com.grouptwo.soccer.championships.models.enums;

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
		for (EventType et : EventType.values()) {
			if (et.getCode().equalsIgnoreCase(code)) {
				return et;
			}
		}
		return null;
	}
}
