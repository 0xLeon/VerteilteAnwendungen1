package com.leon.hfu.web.ticketSale.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stefan on 08.05.2015.
 */
public class ServletUtil {
	private static ConcurrentHashMap<String, RequestDispatcher> requestDispatchers = new ConcurrentHashMap<>();

	public static ArrayList<String> getRequestParameter(HttpServletRequest request, String key) throws ServletException {
		String[] valuesRaw = request.getParameterValues(key);
		ArrayList<String> values = new ArrayList<>(valuesRaw.length);

		if ((valuesRaw == null) || (valuesRaw.length == 0)) {
			throw new ServletException("Parameter »" + key + "« doesn't exist.");
		}

		for (String value: valuesRaw) {
			if (value.length() == 0) {
				continue;
			}

			values.add(value);
		}

		return values;
	}

	public static String getSingleRequestParameter(HttpServletRequest request, String key) throws ServletException {
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

	private ServletUtil() { }
}
