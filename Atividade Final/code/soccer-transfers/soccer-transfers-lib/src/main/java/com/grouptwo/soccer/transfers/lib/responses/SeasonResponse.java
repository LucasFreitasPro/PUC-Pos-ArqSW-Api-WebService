package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SeasonResponse {

	private UUID id;

	private Short year;

	@JsonProperty("started-at")
	private LocalDateTime startedAt;

	@JsonProperty("ended-at")
	private LocalDateTime endedAt;

	@JsonIgnore
	private Boolean deleted;

	private List<MatchResponse> matches;

	@JsonIgnore
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<MatchResponse> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchResponse> matches) {
		this.matches = matches;
	}

	public Boolean getAddLinkToEndPath() {
		return addLinkToEndPath;
	}

	public void setAddLinkToEndPath(Boolean addLinkToEndPath) {
		this.addLinkToEndPath = addLinkToEndPath;
	}
}
