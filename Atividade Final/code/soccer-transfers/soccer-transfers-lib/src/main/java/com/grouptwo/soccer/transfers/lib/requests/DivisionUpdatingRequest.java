package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DivisionUpdatingRequest {

	@JsonProperty("new-name")
	@NotBlank
	private String newName;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
