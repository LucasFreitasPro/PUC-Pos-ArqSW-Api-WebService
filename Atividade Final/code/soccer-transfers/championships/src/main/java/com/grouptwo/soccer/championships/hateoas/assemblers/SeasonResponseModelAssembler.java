package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.SeasonController;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;

@Component
public class SeasonResponseModelAssembler implements RepresentationModelAssembler<SeasonResponse, EntityModel<SeasonResponse>> {

	private UUID championshipId;

	public void setChampionshipId(UUID championshipId) {
		this.championshipId = championshipId;
	}

	@Override
	public EntityModel<SeasonResponse> toModel(SeasonResponse response) {
		Link withSelfRel = linkTo(methodOn(SeasonController.class).getOne(championshipId, response.getId())).withSelfRel();
		Link withRel = linkTo(methodOn(SeasonController.class).getAll(championshipId)).withRel("seasons");

		if (response.getAddLinkToEndPath() != null && response.getAddLinkToEndPath()) {
			return EntityModel.of(response, withSelfRel, withRel, linkTo(methodOn(SeasonController.class).end(championshipId, response.getId())).withRel("end the season"));
		} else {
			return EntityModel.of(response, withSelfRel, withRel);
		}
	}
}
