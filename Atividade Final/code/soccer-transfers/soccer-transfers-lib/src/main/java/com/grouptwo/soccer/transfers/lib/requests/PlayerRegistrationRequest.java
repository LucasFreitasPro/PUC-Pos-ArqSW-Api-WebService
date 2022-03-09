package com.grouptwo.soccer.transfers.lib.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PlayerRegistrationRequest {

	@NotBlank
	private String name;

	@NotNull
	private LocalDate birth;

	@NotBlank
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "PlayerRegistrationRequest [name=" + name + ", birth=" + birth + ", country=" + country + "]";
	}
}
