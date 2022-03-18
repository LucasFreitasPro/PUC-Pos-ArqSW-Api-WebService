package com.grouptwo.soccer.championships.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Division;
import com.grouptwo.soccer.transfers.lib.requests.DivisionRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.DivisionResponse;

@Component
public class DivisionConverter {

	public DivisionResponse fromEntityToResponse(Division entity) {
		DivisionResponse response = new DivisionResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	public Division fromRequestToEntity(DivisionRegisteringRequest request) {
		Division entity = new Division();
		BeanUtils.copyProperties(request, entity);
		return entity;
	}

	public Division fromResponseToEntity(DivisionResponse response) {
		Division entity = new Division();
		BeanUtils.copyProperties(response, entity);
		return entity;
	}
}
