package com.grouptwo.soccer.transfers.lib.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerUpdateRequest extends PlayerDefaultRequest {

	@JsonProperty("team-name")
	private String teamName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return "PlayerUpdateRequest [teamName=" + teamName + ", " + super.toString() + "]";
	}
}
