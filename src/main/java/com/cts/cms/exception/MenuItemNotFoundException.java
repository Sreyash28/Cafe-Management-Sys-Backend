package com.cts.cms.exception;

public class MenuItemNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuItemNotFoundException() {
		super();
	}

	public MenuItemNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MenuItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuItemNotFoundException(String message) {
		super(message);
	}

	public MenuItemNotFoundException(Throwable cause) {
		super(cause);
	}
}
