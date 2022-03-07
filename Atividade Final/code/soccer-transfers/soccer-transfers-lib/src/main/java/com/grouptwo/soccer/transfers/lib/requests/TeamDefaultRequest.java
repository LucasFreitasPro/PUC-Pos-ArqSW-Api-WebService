package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TeamDefaultRequest {

	@JsonProperty("name")
	@NotBlank(message = "Name must not be null or empty")
	protected String newName;

	@NotBlank(message = "State must not be null or empty")
	@Size(min = 2, max = 2, message = "State accepts only two characters")
	protected String state;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
