package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventResponse {

	@JsonIgnore
	private UUID id;

	@JsonProperty("event-name")
	private String eventName;

	private String half;

	@JsonProperty("time-in-half")
	private String timeInHalf;

	@JsonProperty("player-id")
	private UUID playerId;

	@JsonProperty("created-at")
	private LocalDateTime createdAt;

	@JsonProperty("substitution-event")
	private SubstitutionEventResponse substitutionEvent;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SubstitutionEventResponse getSubstitutionEvent() {
		return substitutionEvent;
	}

	public void setSubstitutionEvent(SubstitutionEventResponse substitutionEvent) {
		this.substitutionEvent = substitutionEvent;
	}
}
