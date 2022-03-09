package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamUpdateRequest {

	@JsonProperty("new-name")
	@NotBlank
	private String newName;

	@JsonProperty("new-state")
	@NotBlank
	@Size(min = 2, max = 2, message = "Accepts only two characters")
	private String newState;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}
}
