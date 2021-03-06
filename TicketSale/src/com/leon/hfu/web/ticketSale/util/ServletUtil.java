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
package com.leon.hfu.web.ticketSale.util;

import com.leon.exception.URIParameterException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author		Stefan Hahn
 */
public class ServletUtil {
	// TODO: move to own library project
	private static ConcurrentHashMap<String, RequestDispatcher> requestDispatchers = new ConcurrentHashMap<>();

	public static ArrayList<String> getRequestParameter(HttpServletRequest request, String key) throws URIParameterException {
		String[] valuesRaw = request.getParameterValues(key);
		ArrayList<String> values = new ArrayList<>(valuesRaw.length);

		if  (valuesRaw.length == 0) {
			throw new URIParameterException("Parameter »" + key + "« doesn't exist.", key);
		}

		for (String value: valuesRaw) {
			if (value.length() == 0) {
				continue;
			}

			values.add(value);
		}

		if (values.size() == 0) {
			throw new URIParameterException("Parameter »" + key + "« doesn't exist.", key);
		}

		return values;
	}

	public static String getSingleRequestParameter(HttpServletRequest request, String key) throws URIParameterException {
		return ServletUtil.getRequestParameter(request, key).get(0);
	}

	public static <T> T getSessionAttribute(HttpSession session, String key) {
		Object value = session.getAttribute(key);

		if (value == null) {
			throw new RuntimeException();
		}

		return (T) value;
	}

	public synchronized static RequestDispatcher getRequestDispatcher(String path, ServletContext servletContext) {
		if (!ServletUtil.requestDispatchers.containsKey(path)) {
			ServletUtil.requestDispatchers.put(path, servletContext.getRequestDispatcher(path));
		}

		return ServletUtil.requestDispatchers.get(path);
	}

	/**
	 * This will take the three pre-defined entities in XML 1.0 (used
	 * specifically in XML elements) and convert their character representation
	 * to the appropriate entity reference, suitable for XML element content.
	 *
	 * https://code.google.com/p/jatl/source/browse/src/main/java/com/googlecode/jatl/MarkupUtils.java
	 *
	 * @param	string			<code>String</code> input to escape.
	 * @param	attributeContext	If true, escaping is set to attribute context escaping witespace characters as well.
	 * @return				<code>String</code> with escaped content.
	 */
	public static String escapeXML(String string, boolean attributeContext) {
		if (string == null) {
			return null;
		}

		StringBuffer buffer = null;
		char currentChar;
		String entity;

		for (int i = 0, l = string.length(); i < l; i++) {
			currentChar = string.charAt(i);
			entity = ServletUtil.getEntityForChar(currentChar, attributeContext);

			if (buffer == null) {
				if (entity != null) {
					// An entity occurred, so we'll have to use StringBuffer
					// (allocate room for it plus a few more entities).
					buffer = new StringBuffer(string.length() + 20);

					// Copy previous skipped characters and fall through
					// to pickup current character
					buffer.append(string.substring(0, i));
					buffer.append(entity);
				}
			}
			else {
				if (entity == null) {
					buffer.append(currentChar);
				}
				else {
					buffer.append(entity);
				}
			}
		}

		// If there were any entities, return the escaped characters
		// that we put in the StringBuffer. Otherwise, just return
		// the unmodified input string.
		return ((buffer == null) ? string : buffer.toString());
	}

	private static String getEntityForChar(char c, boolean attributeContext) {
		switch (c) {
			case '<':
				return "&lt;";
			case '>':
				return "&gt;";
			case '&':
				return "&amp;";
			case '"':
				return "&quot;";
		}

		if (attributeContext) {
			switch (c) {
				case '\r':
					return "&#xD;";
				case '\n' :
					return "&#xA;";
				case '\t':
					return "&#x9;";
				default:
					return null;
			}
		}
		else {
			return null;
		}
	}

	private ServletUtil() { }
}
