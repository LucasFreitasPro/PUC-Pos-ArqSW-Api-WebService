package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerUpdateTeamRequest {

	@JsonProperty("new-team-id")
	@NotNull
	private UUID newTeamId;

	public PlayerUpdateTeamRequest() {

	}

	public PlayerUpdateTeamRequest(UUID newTeamId) {
		this.newTeamId = newTeamId;
	}

	public UUID getNewTeamId() {
		return newTeamId;
	}
}
