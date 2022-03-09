package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDate;

public class PlayerRegistrationResponse extends DefaultPlayerResponse {

	private LocalDate birth;

	private String country;

	public PlayerRegistrationResponse() {

	}

	public PlayerRegistrationResponse(String teamName, String name) {
		setTeamName(teamName);
		setName(name);
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
