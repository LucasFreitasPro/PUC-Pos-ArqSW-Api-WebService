package com.grouptwo.soccer.transfers.teams.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.teams.controllers.TeamController;

@Component
public class TeamResponseModelAssembler implements RepresentationModelAssembler<TeamResponse, EntityModel<TeamResponse>> {

	@Override
	public EntityModel<TeamResponse> toModel(TeamResponse teamResponse) {
		return EntityModel.of(teamResponse,
				linkTo(methodOn(TeamController.class).getOne(teamResponse.getName())).withSelfRel(),
				linkTo(methodOn(TeamController.class).getAll()).withRel("teams"));
	}
}
