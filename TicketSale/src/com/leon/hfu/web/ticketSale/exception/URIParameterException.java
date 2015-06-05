package com.leon.hfu.web.ticketSale.exception;

import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public class URIParameterException extends Exception {
	private String parameterName = null;
	private HashMap<String, URIParameterException> multipleErrors = null;

	public URIParameterException(String parameterName) {
		super();
		this.parameterName = parameterName;
	}

	public URIParameterException(String message, String parameterName) {
		super(message);
		this.parameterName = parameterName;
	}

	public URIParameterException(String message, String parameterName, Throwable cause) {
		super(message, cause);
		this.parameterName = parameterName;
	}

	public URIParameterException(String parameterName, Throwable cause) {
		super(cause);
		this.parameterName = parameterName;
	}

	public URIParameterException(HashMap<String, URIParameterException> multipleErrors) {
		super();
		this.multipleErrors = multipleErrors;
	}

	public URIParameterException(String message, HashMap<String, URIParameterException> multipleErrors) {
		super(message);
		this.multipleErrors = multipleErrors;
	}

	public URIParameterException(String message, HashMap<String, URIParameterException> multipleErrors, Throwable cause) {
		super(message, cause);
		this.multipleErrors = multipleErrors;
	}

	public URIParameterException(HashMap<String, URIParameterException> multipleErrors, Throwable cause) {
		super(cause);
		this.multipleErrors = multipleErrors;
	}

	public HashMap<String, URIParameterException> getMultipleErrors() {
		return this.multipleErrors;
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
