package com.grouptwo.soccer.transfers.lib.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubstitutionEventResponse {

	@JsonProperty("player-id-in")
	private UUID playerIdIn;

	@JsonProperty("player-id-out")
	private UUID playerIdOut;

	@JsonProperty("team-id")
	private UUID teamId;

	public UUID getPlayerIdIn() {
		return playerIdIn;
	}

	public void setPlayerIdIn(UUID playerIdIn) {
		this.playerIdIn = playerIdIn;
	}

	public UUID getPlayerIdOut() {
		return playerIdOut;
	}

	public void setPlayerIdOut(UUID playerIdOut) {
		this.playerIdOut = playerIdOut;
	}

	public UUID getTeamId() {
		return teamId;
	}

	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}
}
