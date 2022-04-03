package com.grouptwo.soccer.transfers.lib.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventName;

	private UUID playerId;

	private String halfName;

	private String timeInHalf;

	private Short stoppageTime;

	private LocalDateTime createdAt;

	private SubstitutionEventDTO substitutionEvent;

	private UUID matchId;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public String getHalfName() {
		return halfName;
	}

	public void setHalfName(String halfName) {
		this.halfName = halfName;
	}

	public String getTimeInHalf() {
		return timeInHalf;
	}

	public void setTimeInHalf(String timeInHalf) {
		this.timeInHalf = timeInHalf;
	}

	public Short getStoppageTime() {
		return stoppageTime;
	}

	public void setStoppageTime(Short stoppageTime) {
		this.stoppageTime = stoppageTime;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SubstitutionEventDTO getSubstitutionEvent() {
		return substitutionEvent;
	}

	public void setSubstitutionEvent(SubstitutionEventDTO substitutionEvent) {
		this.substitutionEvent = substitutionEvent;
	}

	public UUID getMatchId() {
		return matchId;
	}

	public void setMatchId(UUID matchId) {
		this.matchId = matchId;
	}

	@Override
	public String toString() {
		return "EventDTO [eventName=" + eventName + ", playerId=" + playerId + ", halfName=" + halfName + ", timeInHalf=" + timeInHalf + ", stoppageTime=" + stoppageTime + ", createdAt=" + createdAt
				+ ", substitutionEvent=" + substitutionEvent + ", matchId=" + matchId + "]";
	}
}
