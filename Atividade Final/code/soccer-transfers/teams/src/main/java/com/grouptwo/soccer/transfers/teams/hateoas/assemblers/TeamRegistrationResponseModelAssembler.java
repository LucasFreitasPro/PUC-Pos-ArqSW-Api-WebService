package com.grouptwo.soccer.transfers.teams.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.responses.TeamRegistrationResponse;
import com.grouptwo.soccer.transfers.teams.controllers.TeamController;

@Component
public class TeamRegistrationResponseModelAssembler implements RepresentationModelAssembler<TeamRegistrationResponse, EntityModel<TeamRegistrationResponse>> {

	@Override
	public EntityModel<TeamRegistrationResponse> toModel(TeamRegistrationResponse teamRegistrationResponse) {
		return EntityModel.of(teamRegistrationResponse,
				linkTo(methodOn(TeamController.class).getOne(teamRegistrationResponse.getTeamId())).withSelfRel(),
				linkTo(methodOn(TeamController.class).getAll()).withRel("teams"));
	}
}
