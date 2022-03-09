package com.grouptwo.soccer.transfers.lib.responses;

import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamResponse extends DefaultTeamResponse {

	@JsonIgnore
	private Set<PlayerResponse> players;

	@JsonProperty("players")
	private CollectionModel<EntityModel<PlayerResponse>> playersCollectionModel;

	@JsonIgnore
	private boolean deleted;

	public Set<PlayerResponse> getPlayers() {
		return players;
	}

	public void setPlayers(Set<PlayerResponse> players) {
		this.players = players;
	}

	public CollectionModel<EntityModel<PlayerResponse>> getPlayersCollectionModel() {
		return playersCollectionModel;
	}

	public void setPlayersCollectionModel(CollectionModel<EntityModel<PlayerResponse>> playersCollectionModel) {
		this.playersCollectionModel = playersCollectionModel;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "TeamResponse [" + super.toString() + ", players=" + players + "]";
	}
}
