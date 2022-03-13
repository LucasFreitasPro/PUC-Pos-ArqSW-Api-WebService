package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;

public class SeasonRegisteringRequest {

	@NotBlank
	private Short year;

	public Short getYear() {
		return year;
	}

	public void setYear(Short year) {
		this.year = year;
	}
}
