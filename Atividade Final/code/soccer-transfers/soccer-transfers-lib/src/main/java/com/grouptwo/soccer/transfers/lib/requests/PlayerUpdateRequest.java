package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerUpdateRequest {

	@JsonProperty("new-name")
	@NotBlank
	private String newName;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	@Override
	public String toString() {
		return "PlayerUpdateRequest [newName=" + newName + "]";
	}
}
