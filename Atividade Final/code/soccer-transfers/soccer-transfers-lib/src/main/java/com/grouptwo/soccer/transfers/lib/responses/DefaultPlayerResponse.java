package com.grouptwo.soccer.transfers.lib.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class DefaultPlayerResponse {

	@JsonProperty("player-id")
	private UUID playerId;

	@JsonProperty("team-id")
	private UUID teamId;

	private String name;

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public UUID getTeamId() {
		return teamId;
	}

	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DefaultPlayerResponse [playerId=" + playerId + ", teamId=" + teamId + ", name=" + name + "]";
	}
}
