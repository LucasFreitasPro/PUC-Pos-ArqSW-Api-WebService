package com.grouptwo.soccer.championships.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "season")
public class Season {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private Short year;

	@Column(nullable = false)
	private LocalDateTime startedAt;

	@Column(nullable = true)
	private LocalDateTime endedAt;

	@ManyToOne
	@JoinColumn(name = "championship_id", referencedColumnName = "id", nullable = false)
	private Championship championship;

	@OneToMany(mappedBy = "season")
	private List<Match> matches;

	@Transient
	private Boolean addLinkToEndPath;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Short getYear() {
		return year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(LocalDateTime endedAt) {
		this.endedAt = endedAt;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public Boolean getAddLinkToEndPath() {
		return addLinkToEndPath;
	}

	public void setAddLinkToEndPath(Boolean addLinkToEndPath) {
		this.addLinkToEndPath = addLinkToEndPath;
	}
}
