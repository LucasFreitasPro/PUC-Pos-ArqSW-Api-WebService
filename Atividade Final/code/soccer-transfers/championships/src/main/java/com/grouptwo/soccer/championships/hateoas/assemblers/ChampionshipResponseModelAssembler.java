package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.ChampionshipController;
import com.grouptwo.soccer.championships.controllers.SeasonController;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;

@Component
public class ChampionshipResponseModelAssembler implements RepresentationModelAssembler<ChampionshipResponse, EntityModel<ChampionshipResponse>> {

	private final SeasonResponseModelAssembler seasonResponseModelAssembler;

	public ChampionshipResponseModelAssembler(SeasonResponseModelAssembler seasonResponseModelAssembler) {
		this.seasonResponseModelAssembler = seasonResponseModelAssembler;
	}

	@Override
	public EntityModel<ChampionshipResponse> toModel(ChampionshipResponse response) {
		Link withSelfRel = linkTo(methodOn(ChampionshipController.class).getOne(response.getId())).withSelfRel();
		Link withRel = linkTo(methodOn(ChampionshipController.class).getAll()).withRel("championships");

		if (response.getSeasons() != null) {
			response.setSeasonsCollectionModel(CollectionModel.of(response.getSeasons().stream().map(seasonResponseModelAssembler::toModel).collect(Collectors.toList())));
		}

		if (response.getAddLinkToRegisterSeason() != null && response.getAddLinkToRegisterSeason()) {
			return EntityModel.of(response, withSelfRel, withRel, linkTo(methodOn(SeasonController.class).register(response.getId(), null, null)).withRel("start a season"));
		} else {
			return EntityModel.of(response, withSelfRel, withRel);
		}
	}
}
