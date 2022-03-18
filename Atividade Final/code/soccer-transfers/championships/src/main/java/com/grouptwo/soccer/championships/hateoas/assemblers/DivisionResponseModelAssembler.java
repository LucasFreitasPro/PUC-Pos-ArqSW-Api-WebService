package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.DivisionController;
import com.grouptwo.soccer.transfers.lib.responses.DivisionResponse;

@Component
public class DivisionResponseModelAssembler implements RepresentationModelAssembler<DivisionResponse, EntityModel<DivisionResponse>> {

	@Override
	public EntityModel<DivisionResponse> toModel(DivisionResponse response) {
		return EntityModel.of(response,
				linkTo(methodOn(DivisionController.class).getOne(response.getId())).withSelfRel(),
				linkTo(methodOn(DivisionController.class).getAll()).withRel("divisions"));
	}
}
