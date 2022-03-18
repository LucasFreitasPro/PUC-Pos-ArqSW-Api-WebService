package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class SubstitutionEventRegisteringRequest {

	@NotNull
	private UUID playerIdIn;

	@NotNull
	private UUID playerIdOut;

	@NotNull
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
