package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.SeasonController;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;

@Component
public class SeasonResponseModelAssembler implements RepresentationModelAssembler<SeasonResponse, EntityModel<SeasonResponse>> {

	@Override
	public EntityModel<SeasonResponse> toModel(SeasonResponse response) {
		Link withSelfRel = linkTo(methodOn(SeasonController.class).getOne(response.getChampionId(), response.getId())).withSelfRel();
		Link withRel = linkTo(methodOn(SeasonController.class).getAll(response.getChampionId())).withRel("seasons");

		if (response.getAddLinkToEndPath() != null && response.getAddLinkToEndPath()) {
			return EntityModel.of(response, withSelfRel, withRel,
					linkTo(methodOn(SeasonController.class).end(response.getChampionId(), response.getId())).withRel("end the season"));
		} else {
			return EntityModel.of(response, withSelfRel, withRel);
		}
	}
}
