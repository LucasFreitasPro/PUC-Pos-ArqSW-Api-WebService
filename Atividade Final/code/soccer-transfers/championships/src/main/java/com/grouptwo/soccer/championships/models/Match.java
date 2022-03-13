package com.grouptwo.soccer.championships.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "match")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private UUID aTeamId;

	@Column(nullable = false)
	private UUID bTeamId;

	@Column(nullable = false)
	private String arena;

	@Column(nullable = false)
	private LocalDateTime date;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "match", fetch = FetchType.EAGER)
	private List<Event> events;

	@ManyToOne
	@JoinColumn(name = "season_id", referencedColumnName = "id", nullable = false)
	private Season season;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getaTeamId() {
		return aTeamId;
	}

	public void setaTeamId(UUID aTeamId) {
		this.aTeamId = aTeamId;
	}

	public UUID getbTeamId() {
		return bTeamId;
	}

	public void setbTeamId(UUID bTeamId) {
		this.bTeamId = bTeamId;
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

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}
}
