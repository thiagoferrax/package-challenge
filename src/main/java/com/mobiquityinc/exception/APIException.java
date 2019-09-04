package com.mobiquityinc.exception;

public class APIException extends Exception {

	private static final long serialVersionUID = 2177676455969353712L;

	public APIException(String message, Exception e) {
		super(message, e);
	}

	public APIException(String message) {
		super(message);
	}
}
