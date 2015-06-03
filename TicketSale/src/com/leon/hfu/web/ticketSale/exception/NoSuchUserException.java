package com.leon.hfu.web.ticketSale.exception;

/**
 * @author		Stefan Hahn
 */
public class NoSuchUserException extends Exception {
	public NoSuchUserException() { }

	public NoSuchUserException(String message) {
		super(message);
	}

	public NoSuchUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchUserException(Throwable cause) {
		super(cause);
	}
}
