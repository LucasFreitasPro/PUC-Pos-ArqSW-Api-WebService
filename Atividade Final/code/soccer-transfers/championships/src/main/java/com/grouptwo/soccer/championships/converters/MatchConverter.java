package com.grouptwo.soccer.championships.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.transfers.lib.requests.MatchRegisteringRequest;

@Component
public class MatchConverter {

	public Match fromRequestToEntity(MatchRegisteringRequest request) {
		Match entity = new Match();
		BeanUtils.copyProperties(request, entity);
		return entity;
	}

	public MatchRegisteringRequest fromEntityToRequest(Match entity) {
		MatchRegisteringRequest request = new MatchRegisteringRequest();
		BeanUtils.copyProperties(entity, request);
		return request;
	}
}
