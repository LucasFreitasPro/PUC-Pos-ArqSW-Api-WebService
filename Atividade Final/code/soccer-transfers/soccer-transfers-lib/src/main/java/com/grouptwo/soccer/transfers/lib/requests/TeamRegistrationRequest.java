package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TeamRegistrationRequest {

	@NotBlank
	private String name;

	@NotBlank
	@Size(min = 2, max = 2, message = "Accepts only two characters")
	private String state;

	@NotBlank
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "TeamRegistrationRequest [name=" + name + ", state=" + state + ", country=" + country + "]";
	}
}
