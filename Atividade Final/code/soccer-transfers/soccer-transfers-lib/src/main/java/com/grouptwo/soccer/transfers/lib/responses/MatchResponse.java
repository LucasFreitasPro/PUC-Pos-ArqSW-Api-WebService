package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchResponse {

	private UUID id;

	@JsonProperty("team-id-a")
	private UUID teamIdA;

	@JsonProperty("team-id-b")
	private UUID teamIdB;

	private String arena;

	private LocalDateTime date;

	@JsonProperty("created-at")
	private LocalDateTime createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
