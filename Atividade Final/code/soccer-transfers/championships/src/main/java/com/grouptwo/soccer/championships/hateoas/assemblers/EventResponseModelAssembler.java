package com.grouptwo.soccer.championships.hateoas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.championships.controllers.EventsController;
import com.grouptwo.soccer.transfers.lib.responses.EventResponse;

@Component
public class EventResponseModelAssembler implements RepresentationModelAssembler<EventResponse, EntityModel<EventResponse>> {

	private UUID championshipId;
	private UUID seasonId;
	private UUID matchId;

	public void setChampionshipId(UUID championshipId) {
		this.championshipId = championshipId;
	}

	public void setSeasonId(UUID seasonId) {
		this.seasonId = seasonId;
	}

	public void setMatchId(UUID matchId) {
		this.matchId = matchId;
	}

	@Override
	public EntityModel<EventResponse> toModel(EventResponse response) {
		return EntityModel.of(response,
				linkTo(methodOn(EventsController.class).getAllByEventType(championshipId, seasonId, matchId, response.getEventName()) ).withSelfRel(),
				linkTo(methodOn(EventsController.class).getAll(championshipId, seasonId, matchId)).withRel("events"));
	}
}
