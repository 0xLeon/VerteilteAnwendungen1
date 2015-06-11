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
package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;
import com.leon.hfu.web.ticketSale.exception.TicketSaleException;
import com.leon.util.SQLUtil;

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

/**
 * @author		Stefan Hahn
 */
public final class Core {
	private static Core instance = null;

	private DataSource dataSource = null;

	private Core() {
		this.initDatabase();
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
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			this.dataSource = (DataSource) (new InitialContext()).lookup("java:app/jdbc/main");

			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(
				"SELECT COUNT(*) AS eventCount FROM ticketsale_event;"
			);

			result = statement.executeQuery();
		}
		catch (NamingException | SQLException e) {
			throw new TicketSaleException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
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
					user = UserAdapter.getUserByID(userID);
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
				user = UserAdapter.getUserByID(1); // user is Admin
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
}
