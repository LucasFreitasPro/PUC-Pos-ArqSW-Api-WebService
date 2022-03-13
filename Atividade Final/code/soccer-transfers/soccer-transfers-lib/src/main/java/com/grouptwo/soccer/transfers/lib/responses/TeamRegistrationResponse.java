package com.grouptwo.soccer.transfers.lib.responses;

import java.util.UUID;

public class TeamRegistrationResponse extends DefaultTeamResponse {

	public TeamRegistrationResponse(UUID teamId, String name, String state, String country) {
		setTeamId(teamId);
		setName(name);
		setState(state);
		setCountry(country);
	}

	@Override
	public String toString() {
		return "TeamRegistrationResponse [" + super.toString() + "]";
	}
}
