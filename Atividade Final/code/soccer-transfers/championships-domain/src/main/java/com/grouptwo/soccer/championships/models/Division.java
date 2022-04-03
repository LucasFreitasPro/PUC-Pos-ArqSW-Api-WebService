package com.grouptwo.soccer.championships.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Division {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "division")
	private List<Championship> championships;

	public Division() {
	}

	public Division(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Championship> getChampionships() {
		return championships;
	}

	public void setChampionships(List<Championship> championships) {
		this.championships = championships;
	}
}
