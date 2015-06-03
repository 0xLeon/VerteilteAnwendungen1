package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;
import com.leon.hfu.web.ticketSale.exception.TicketSaleException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Stefan on 01.05.2015.
 */
public final class Core {
	private static Core instance = null;

	private Event event = null;
	private DataSource dataSource = null;

	private Core() {
		this.initDatabase();
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

	public Connection getDatabaseConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	private void initDatabase()  {
		Connection connection = null;

		try {
			this.dataSource = (DataSource) (new InitialContext()).lookup("java:app/jdbc/main");

			connection = this.dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
				"SELECT COUNT(*) AS eventCount FROM event;"
			);

			ResultSet result = statement.executeQuery();
			result.next();
			int eventCount = result.getInt("eventCount");

			if (eventCount < 1) {
				throw new SQLException("Table »event« doesn't contain any data.");
			}
		}
		catch (NamingException | SQLException e) {
			throw new TicketSaleException(e);
		}
		finally {
			try {
				if (connection != null) connection.close();
			}
			catch (SQLException e) { }
		}
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
