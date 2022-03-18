package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

public class DivisionRegisteringRequest {

	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
