package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { "player-id", "team-id", "name", "birth", "country" })
public class PlayerResponse extends DefaultPlayerResponse {

	private LocalDate birth;

	private String country;

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
		return "PlayerResponse [" + super.toString() + ", birth=" + birth + ", country=" + country + "]";
	}
}
