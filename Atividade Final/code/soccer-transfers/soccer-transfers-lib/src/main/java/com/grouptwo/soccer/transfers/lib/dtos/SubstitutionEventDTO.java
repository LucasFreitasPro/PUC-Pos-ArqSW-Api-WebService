package com.grouptwo.soccer.transfers.lib.dtos;

import java.io.Serializable;
import java.util.UUID;

public class SubstitutionEventDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID playerIdIn;

	private UUID playerIdOut;

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

	@Override
	public String toString() {
		return " [playerIdIn=" + playerIdIn + ", playerIdOut=" + playerIdOut + ", teamId=" + teamId + "]";
	}
}
