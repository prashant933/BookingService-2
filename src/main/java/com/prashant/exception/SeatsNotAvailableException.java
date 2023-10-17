package com.prashant.exception;

public class SeatsNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SeatsNotAvailableException(String message) {
		super(message);
	}
}
