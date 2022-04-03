package com.grouptwo.soccer.championships.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "match_substitution_event")
public class SubstitutionEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private UUID playerIdIn;

	@Column(nullable = false)
	private UUID playerIdOut;

	@Column(nullable = false)
	private UUID teamId;

	@OneToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	private Event event;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getPlayerIdIn() {
		return playerIdIn;
	}

	public void setPlayerIdIn(UUID playerIdIn) {
		this.playerIdIn = playerIdIn;
	}

	public UUID getPlayerIdOut() {
		return playerIdOut;
	}

	public void setPlayerIdOut(UUID playerIdOut) {
		this.playerIdOut = playerIdOut;
	}

	public UUID getTeamId() {
		return teamId;
	}

	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
