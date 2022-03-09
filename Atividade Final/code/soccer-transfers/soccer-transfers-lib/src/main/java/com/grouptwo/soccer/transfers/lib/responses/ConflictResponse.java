package com.grouptwo.soccer.transfers.lib.responses;

public class ConflictResponse<T> {

	private String message;

	private T resource;

	public ConflictResponse(String message) {
		this.message = message;
	}

	public ConflictResponse(String message, T resource) {
		this.message = message;
		this.resource = resource;
	}

	public String getMessage() {
		return message;
	}

	public T getResource() {
		return resource;
	}
}
