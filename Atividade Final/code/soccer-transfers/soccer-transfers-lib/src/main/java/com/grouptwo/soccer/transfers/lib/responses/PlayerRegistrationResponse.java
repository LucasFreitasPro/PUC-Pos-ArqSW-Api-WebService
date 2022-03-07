package com.grouptwo.soccer.transfers.lib.responses;

public class PlayerRegistrationResponse extends DefaultPlayerResponse {

	public PlayerRegistrationResponse() {

	}

	public PlayerRegistrationResponse(String teamName, String name) {
		setTeamName(teamName);
		setName(name);
	}

	@Override
	public String toString() {
		return "PlayerRegistrationResponse [" + super.toString() + "]";
	}
}
