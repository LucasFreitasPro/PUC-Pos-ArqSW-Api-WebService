package com.grouptwo.soccer.transfers.lib.responses;

public class TeamRegistrationResponse extends DefaultTeamResponse {

	public TeamRegistrationResponse(String name, String state, String country) {
		setName(name);
		setState(state);
		setCountry(country);
	}

	@Override
	public String toString() {
		return "TeamRegistrationResponse [" + super.toString() + "]";
	}
}
