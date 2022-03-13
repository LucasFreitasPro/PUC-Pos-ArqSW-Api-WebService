package com.grouptwo.soccer.transfers.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.controllers.TransferController;
import com.grouptwo.soccer.transfers.lib.responses.TransferResponse;

@Component
public class TransferResponseModelAssembler implements RepresentationModelAssembler<TransferResponse, EntityModel<TransferResponse>> {

	@Override
	public EntityModel<TransferResponse> toModel(TransferResponse entity) {
		return EntityModel.of(entity,
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransferController.class).getAllFromTeam(entity.getOriginTeamId())).withRel("get all transfers from origin team"),
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransferController.class).getAllToTeam(entity.getDestinyTeamId())).withRel("get all transfers from destiny team"),
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransferController.class).getAllPlayerTransfers(entity.getPlayerId())).withRel("get all player transfers")
				);
	}
}
