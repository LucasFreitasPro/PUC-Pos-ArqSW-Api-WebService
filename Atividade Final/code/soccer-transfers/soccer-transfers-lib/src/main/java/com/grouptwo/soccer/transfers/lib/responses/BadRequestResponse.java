package com.grouptwo.soccer.transfers.lib.responses;

import java.util.ArrayList;
import java.util.List;

public class BadRequestResponse {

	private List<ValidationResult> errors;

	public BadRequestResponse() {
		this.errors = new ArrayList<ValidationResult>();
	}

	public List<ValidationResult> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationResult> errors) {
		this.errors = errors;
	}

	public void addError(String field, String message) {
		errors.add(new ValidationResult(field, message));
	}

	class ValidationResult {
		private String field;
		private String message;

		ValidationResult(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public String getMessage() {
			return message;
		}
	}
}
