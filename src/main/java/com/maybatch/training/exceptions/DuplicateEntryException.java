package com.maybatch.training.exceptions;

public class DuplicateEntryException extends RuntimeException {

	private static final long serialVersionUID = 767892231152606651L;

	public DuplicateEntryException(String message) {
		super(message);
	}

}
