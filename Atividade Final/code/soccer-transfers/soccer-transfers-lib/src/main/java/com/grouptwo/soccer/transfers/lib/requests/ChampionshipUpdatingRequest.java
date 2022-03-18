package com.grouptwo.soccer.transfers.lib.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChampionshipUpdatingRequest {

	@JsonProperty("new-name")
	private String newName;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
