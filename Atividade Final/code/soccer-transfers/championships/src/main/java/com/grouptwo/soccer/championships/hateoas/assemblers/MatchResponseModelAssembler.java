package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.MatchController;
import com.grouptwo.soccer.transfers.lib.responses.MatchResponse;

@Component
public class MatchResponseModelAssembler implements RepresentationModelAssembler<MatchResponse, EntityModel<MatchResponse>> {

	private UUID championshipId;
	private UUID seasonId;

	public void setChampionshipId(UUID championshipId) {
		this.championshipId = championshipId;
	}

	public void setSeasonId(UUID seasonId) {
		this.seasonId = seasonId;
	}

	@Override
	public EntityModel<MatchResponse> toModel(MatchResponse response) {
		return EntityModel.of(response,
				linkTo(methodOn(MatchController.class).getOne(championshipId, seasonId, response.getId())).withSelfRel(),
				linkTo(methodOn(MatchController.class).getAll(championshipId, seasonId)).withRel("matches"));
	}
}
