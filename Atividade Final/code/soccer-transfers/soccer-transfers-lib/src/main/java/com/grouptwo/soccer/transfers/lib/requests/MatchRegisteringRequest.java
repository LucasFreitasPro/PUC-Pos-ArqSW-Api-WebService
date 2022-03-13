package com.grouptwo.soccer.transfers.lib.requests;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MatchRegisteringRequest {

	@NotNull
	private UUID teamIdA;

	@NotNull
	private UUID teamIdB;

	@NotBlank
	private String arena;

	@NotNull
	private LocalDateTime date;

	public UUID getTeamIdA() {
		return teamIdA;
	}

	public void setTeamIdA(UUID teamIdA) {
		this.teamIdA = teamIdA;
	}

	public UUID getTeamIdB() {
		return teamIdB;
	}

	public void setTeamIdB(UUID teamIdB) {
		this.teamIdB = teamIdB;
	}

	public String getArena() {
		return arena;
	}

	public void setArena(String arena) {
		this.arena = arena;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
