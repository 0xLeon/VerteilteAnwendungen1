/*
 * Copyright (C) 2015 Stefan Hahn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
