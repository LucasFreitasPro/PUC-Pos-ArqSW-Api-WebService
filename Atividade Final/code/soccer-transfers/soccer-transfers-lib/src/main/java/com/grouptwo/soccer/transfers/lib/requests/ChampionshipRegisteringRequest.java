package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChampionshipRegisteringRequest {

	@NotBlank
	private String name;

	@JsonProperty("division-id")
	@NotNull
	private UUID divisionId;

	@NotBlank
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(UUID divisionId) {
		this.divisionId = divisionId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
