package com.grouptwo.soccer.transfers.lib.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"teamId", "name", "state", "country", "players"})
public abstract class DefaultTeamResponse {

	@JsonProperty("team-id")
	private UUID teamId;

	private String name;

	private String state;

	private String country;

	public UUID getTeamId() {
		return teamId;
	}

	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}

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
		return "name=" + name + ", state=" + state + ", country=" + country;
	}
}
