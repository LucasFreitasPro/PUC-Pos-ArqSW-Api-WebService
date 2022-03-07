package com.grouptwo.soccer.transfers.lib.responses;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamResponse extends DefaultTeamResponse {

	private Set<PlayerResponse> players;

	@JsonIgnore
	private boolean deleted;

	public Set<PlayerResponse> getPlayers() {
		return players;
	}

	public void setPlayers(Set<PlayerResponse> players) {
		this.players = players;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "TeamResponse [" + super.toString() + ", players=" + players + "]";
	}
}
