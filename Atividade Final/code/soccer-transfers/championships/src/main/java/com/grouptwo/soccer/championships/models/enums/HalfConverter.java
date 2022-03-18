package com.grouptwo.soccer.championships.models.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class HalfConverter implements AttributeConverter<Half, String> {

	@Override
	public String convertToDatabaseColumn(Half half) {
		return half == null ? null : half.getCode();
	}

	@Override
	public Half convertToEntityAttribute(String code) {
		for (Half h : Half.values()) {
			if (h.getCode().equalsIgnoreCase(code)) {
				return h;
			}
		}
		return null;
	}
}
