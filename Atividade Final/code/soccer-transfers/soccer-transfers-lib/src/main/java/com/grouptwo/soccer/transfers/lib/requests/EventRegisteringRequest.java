package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

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

	private SubstitutionEventRegisteringRequest substitution;

	@JsonProperty("stoppage-time")
	private StoppageTimeEventRegisteringRequest stoppageTime;

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

	public SubstitutionEventRegisteringRequest getSubstitution() {
		return substitution;
	}

	public void setSubstitution(SubstitutionEventRegisteringRequest substitution) {
		this.substitution = substitution;
	}

	public StoppageTimeEventRegisteringRequest getStoppageTime() {
		return stoppageTime;
	}

	public void setStoppageTime(StoppageTimeEventRegisteringRequest stoppageTime) {
		this.stoppageTime = stoppageTime;
	}
}
