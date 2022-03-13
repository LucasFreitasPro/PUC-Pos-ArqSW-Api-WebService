package com.grouptwo.soccer.championships.converters;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Season;
import com.grouptwo.soccer.transfers.lib.requests.SeasonRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;

@Component
public class SeasonConverter {

	public Season fromRequestToEntity(SeasonRegisteringRequest request) {
		Season entity = new Season();
		BeanUtils.copyProperties(request, entity);
		entity.setStartedAt(LocalDateTime.now(ZoneId.of("UTC")));
		return entity;
	}

	public SeasonRegisteringRequest fromEntityToRequest(Season entity) {
		SeasonRegisteringRequest request = new SeasonRegisteringRequest();
		BeanUtils.copyProperties(entity, request);
		return request;
	}

	public SeasonResponse fromEntityToResponse(Season entity) {
		SeasonResponse response = new SeasonResponse();
		BeanUtils.copyProperties(entity, response);
		response.setChampionId(entity.getChampionship().getId());
		return response;
	}

	public Season fromResponseToEntity(SeasonResponse response) {
		Season entity = new Season();
		BeanUtils.copyProperties(response, entity);
		return entity;
	}
}
