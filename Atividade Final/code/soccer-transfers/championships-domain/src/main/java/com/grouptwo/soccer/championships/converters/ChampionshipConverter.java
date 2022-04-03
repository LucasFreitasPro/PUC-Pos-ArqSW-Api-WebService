package com.grouptwo.soccer.championships.converters;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.models.Championship;
import com.grouptwo.soccer.transfers.lib.requests.ChampionshipRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;

@Component
public class ChampionshipConverter {

	private final SeasonConverter seasonConverter;

	private final DivisionConverter divisionConverter;

	public ChampionshipConverter(SeasonConverter seasonConverter, DivisionConverter divisionConverter) {
		this.seasonConverter = seasonConverter;
		this.divisionConverter = divisionConverter;
	}

	public Championship fromRequestToEntity(ChampionshipRegisteringRequest request) {
		Championship entity = new Championship();
		BeanUtils.copyProperties(request, entity);
		entity.setDeleted(Boolean.FALSE);
		return entity;
	}

	public ChampionshipRegisteringRequest fromEntityToRequest(Championship entity) {
		ChampionshipRegisteringRequest request = new ChampionshipRegisteringRequest();
		BeanUtils.copyProperties(entity, request);
		return request;
	}

	public ChampionshipResponse fromEntityToResponse(Championship entity) {
		ChampionshipResponse response = new ChampionshipResponse();
		BeanUtils.copyProperties(entity, response);
		if (entity.getSeasons() != null) {
			response.setSeasons(entity.getSeasons().stream().map(seasonConverter::fromEntityToResponse).collect(Collectors.toSet()));
		}
		if (entity.getDivision() != null) {
			response.setDivision(this.divisionConverter.fromEntityToResponse(entity.getDivision()));
		}
		return response;
	}

	public Championship fromResponseToEntity(ChampionshipResponse response) {
		Championship entity = new Championship();
		BeanUtils.copyProperties(response, entity);
		return entity;
	}
}
