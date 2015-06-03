package com.leon.hfu.web.ticketSale.exception;

/**
 * @author		Stefan Hahn
 */
public class TicketSaleException extends RuntimeException {
	public TicketSaleException() {
		super();
	}

	public TicketSaleException(String message) {
		super(message);
	}

	public TicketSaleException(String message, Throwable cause) {
		super(message, cause);
	}

	public TicketSaleException(Throwable cause) {
		super(cause);
	}
}
