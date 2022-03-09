package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerUpdateTeamRequest {

	@JsonProperty("new-team-name")
	@NotBlank
	private String newTeamName;

	public String getNewTeamName() {
		return newTeamName;
	}

	public void setNewTeamName(String newTeamName) {
		this.newTeamName = newTeamName;
	}
}
