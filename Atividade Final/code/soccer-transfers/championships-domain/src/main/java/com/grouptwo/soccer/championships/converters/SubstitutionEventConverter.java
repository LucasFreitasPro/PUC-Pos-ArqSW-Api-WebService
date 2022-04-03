package com.grouptwo.soccer.championships.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.SubstitutionEvent;
import com.grouptwo.soccer.transfers.lib.responses.SubstitutionEventResponse;

@Component
public class SubstitutionEventConverter {

	public SubstitutionEventResponse fromEntityToResponse(SubstitutionEvent entity) {
		SubstitutionEventResponse response = new SubstitutionEventResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	public SubstitutionEvent fromEntityToResponse(SubstitutionEventResponse response) {
		SubstitutionEvent entity = new SubstitutionEvent();
		BeanUtils.copyProperties(response, entity);
		return entity;
	}
}
