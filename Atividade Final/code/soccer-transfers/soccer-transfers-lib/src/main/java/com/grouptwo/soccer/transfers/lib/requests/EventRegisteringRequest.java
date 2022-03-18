package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventRegisteringRequest {

	@JsonProperty("player-id")
	private UUID playerId;

	@JsonProperty("half")
	@NotBlank
	private String half;

	@JsonProperty("time-in-half")
	@NotBlank
	private String timeInHalf;

	@JsonProperty("during-stoppage-time")
	@NotNull
	private Boolean duringStoppageTime;

	private SubstitutionEventRegisteringRequest substitution;

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public String getHalf() {
		return half;
	}

	public void setHalf(String half) {
		this.half = half;
	}

	public String getTimeInHalf() {
		return timeInHalf;
	}

	public void setTimeInHalf(String timeInHalf) {
		this.timeInHalf = timeInHalf;
	}

	public Boolean getDuringStoppageTime() {
		return duringStoppageTime;
	}

	public void setDuringStoppageTime(Boolean duringStoppageTime) {
		this.duringStoppageTime = duringStoppageTime;
	}

	public SubstitutionEventRegisteringRequest getSubstitution() {
		return substitution;
	}

	public void setSubstitution(SubstitutionEventRegisteringRequest substitution) {
		this.substitution = substitution;
	}
}
