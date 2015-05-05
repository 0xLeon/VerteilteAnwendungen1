package com.leon.hfu.web.ticketSale;

/**
 * @author		Stefan Hahn
 */
public class EventException extends RuntimeException {
	public EventException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventException(String message) {
		super(message);
	}

	public EventException(Throwable cause) {
		super(cause);
	}

	public EventException() {
		super();
	}
}
