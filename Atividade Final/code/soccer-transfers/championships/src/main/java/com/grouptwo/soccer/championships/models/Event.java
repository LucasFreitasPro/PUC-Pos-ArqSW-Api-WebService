package com.grouptwo.soccer.championships.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.grouptwo.soccer.championships.models.enums.EventType;
import com.grouptwo.soccer.championships.models.enums.Half;

@Entity
@Table(name = "match_event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, length = 2)
	private EventType eventType;

	private UUID playerId;

	// acrescimo

	@Column(nullable = false, length = 2)
	private Half half;

	private String timeInHalf;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false)
	private Match match;

	@OneToOne(mappedBy = "event", fetch = FetchType.EAGER)
	private SubstitutionEvent substitutionEvent;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
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

	public Half getHalf() {
		return half;
	}

	public void setHalf(Half half) {
		this.half = half;
	}

	public SubstitutionEvent getSubstitutionEvent() {
		return substitutionEvent;
	}

	public void setSubstitutionEvent(SubstitutionEvent substitutionEvent) {
		this.substitutionEvent = substitutionEvent;
	}

	public String getTimeInHalf() {
		return timeInHalf;
	}

	public void setTimeInHalf(String timeInHalf) {
		this.timeInHalf = timeInHalf;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
}
