package com.grouptwo.soccer.transfers.teams.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.teams.controllers.PlayerController;

@Component
public class PlayerResponseModelAssembler implements RepresentationModelAssembler<PlayerResponse, EntityModel<PlayerResponse>> {

	@Override
	public EntityModel<PlayerResponse> toModel(PlayerResponse playerResponse) {
		return EntityModel.of(playerResponse,
				linkTo(methodOn(PlayerController.class).getOne(playerResponse.getTeamId(), playerResponse.getPlayerId())).withSelfRel(),
				linkTo(methodOn(PlayerController.class).getAll(playerResponse.getTeamId())).withRel("players"));
	}
}
