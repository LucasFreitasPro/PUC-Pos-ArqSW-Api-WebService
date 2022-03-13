package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"playerId", "teamId", "name", "birth", "country"})
public class PlayerRegistrationResponse extends DefaultPlayerResponse {

	private LocalDate birth;

	private String country;

	public PlayerRegistrationResponse() {

	}

	public PlayerRegistrationResponse(UUID teamId, UUID playerId) {
		setTeamId(teamId);
		setPlayerId(playerId);
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "PlayerRegistrationResponse [birth=" + birth + ", country=" + country + ", " + super.toString() + "]";
	}
}
