package com.grouptwo.soccer.transfers.teams.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.requests.TeamRegistrationRequest;
import com.grouptwo.soccer.transfers.lib.requests.TeamUpdateRequest;
import com.grouptwo.soccer.transfers.teams.controllers.TeamController;

@Component
public class TeamUpdateRequestModelAssembler implements RepresentationModelAssembler<TeamUpdateRequest, EntityModel<TeamUpdateRequest>> {

	@Override
	public EntityModel<TeamUpdateRequest> toModel(TeamUpdateRequest entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(TeamController.class).register(new TeamRegistrationRequest(), null)).withRel("create a team"));
	}
}
