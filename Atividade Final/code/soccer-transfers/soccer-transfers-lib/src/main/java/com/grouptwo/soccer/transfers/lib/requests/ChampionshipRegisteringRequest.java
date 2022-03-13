package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChampionshipRegisteringRequest {

	@NotBlank
	private String name;

	@JsonProperty("division-name")
	@NotBlank
	private String divisionName;

	@NotBlank
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
