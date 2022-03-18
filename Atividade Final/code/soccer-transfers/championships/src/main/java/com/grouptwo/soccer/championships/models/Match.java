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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "match", uniqueConstraints = @UniqueConstraint(columnNames = { "team_id_a", "team_id_b", "arena", "date" }))
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "team_id_a", nullable = false)
	private UUID teamIdA;

	@Column(name = "team_id_b", nullable = false)
	private UUID teamIdB;

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

	public Match() {

	}

	public Match(UUID id) {
		this.id = id;
	}

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
