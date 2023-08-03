package com.cts.cms.exception;

public class AuthorizationException extends RuntimeException {

	/**
	 * @param message
	 * @throw Authorization exception if JWT Token is expired, it will throw an
	 *        Authorization exception
	 */
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String message) {
		super(message);
	}
}