package com.grouptwo.soccer.transfers.teams.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.responses.PlayerRegistrationResponse;
import com.grouptwo.soccer.transfers.teams.controllers.PlayerController;

@Component
public class PlayerRegistrationResponseModelAssembler implements RepresentationModelAssembler<PlayerRegistrationResponse, EntityModel<PlayerRegistrationResponse>> {

	@Override
	public EntityModel<PlayerRegistrationResponse> toModel(PlayerRegistrationResponse playerRegistrationResponse) {
		return EntityModel.of(playerRegistrationResponse,
				linkTo(methodOn(PlayerController.class).getOne(playerRegistrationResponse.getTeamName(), playerRegistrationResponse.getName())).withSelfRel(),
				linkTo(methodOn(PlayerController.class).getAll(playerRegistrationResponse.getTeamName())).withRel("players"));
	}
}
