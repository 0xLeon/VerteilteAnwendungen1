package com.leon.hfu.web.ticketSale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Stefan on 01.05.2015.
 */
public final class Core {
	private static Core instance = null;

	private Event event = null;

	private Core() {
		// first date is Sat, 23 May 2015 21:59:59 GMT
		// second date is  Sun, 24 May 2015 21:59:59 GMT
		this.event = new Event(new Date(1432418399000L), new Date(1432504799000L), 100);
	}

	public static Core getInstance() {
		if (Core.instance == null) {
			Core.instance = new Core();
		}

		return Core.instance;
	}

	public void initSession(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		int userID = -1;
		String passwordHash = null;
		User user;

		if (cookies != null) {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equals("ticketsale_userID")) {
					userID = Integer.parseInt(cookie.getValue(), 10);
				}

				if (cookie.getName().equals("ticketsale_passwordHash")) {
					passwordHash = cookie.getValue();
				}
			}
		}

		if (request.getSession(true).isNew() || (request.getSession().getAttribute("user") == null)) {
			try {
				if (userID > 0) {
					// TODO: check login information
					user = User.getUserByUserID(userID);
				}
				else {
					user = User.DEFAULT_USER;
				}
			}
			catch (NoSuchUserException e) {
				// TODO: show message or just assume default user?
				user = User.DEFAULT_USER;
			}

			// TODO: remove, hard coded user for test purpose
			try {
				user = User.getUserByUserID(1); // user is Admin user Leon
			}
			catch (NoSuchUserException e) { }

			request.getSession().setAttribute("user", user);
			response.addCookie(new Cookie("ticketsale_userID", Integer.toString(user.getUserID())));
		}
	}

	public User getUser(HttpServletRequest request) throws ServletException {
		Object rawUserObject = request.getSession().getAttribute("user");

		if ((rawUserObject == null) || (rawUserObject.getClass() != User.class)) {
			throw new ServletException("Invalid User.");
		}

		return ((User) rawUserObject);
	}

	public Event getEvent() {
		return this.event;
	}
}
