package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;
import com.leon.hfu.web.ticketSale.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Stefan on 05.05.2015.
 */
public class UserAdapter {
	public static User createUser(String username, String password, String[] groups) {
		// TODO: implement

		return User.DEFAULT_USER;
	}

	public static void login(int username, String password) {
		// TODO: implement
	}

	public static User getUserByID(int userID) throws NoSuchUserException {
		if (userID < 0) {
			throw new IllegalArgumentException();
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	userID, " +
				"	username, " +
				"	passwordHash " +
				"FROM " +
				"	ticketsale_user " +
				"WHERE " +
				"	userID = ?;"
			);

			statement.setInt(1, userID);

			result = statement.executeQuery();

			result.next();

			return (new User(result.getInt("userID"), result.getString("username"), result.getString("passwordHash")));
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}

	public static User[] getUsersByID(int[] userIDs) throws NoSuchUserException {
		if ((userIDs == null) || (userIDs.length == 0)) {
			throw new IllegalArgumentException();
		}

		User[] users = new User[userIDs.length];
		int i = 0;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	userID, " +
				"	username, " +
				"	passwordHash " +
				"FROM " +
				"	ticketsale_user " +
				"WHERE " +
				"	userID IN (" + SQLUtil.getInPlaceholders(userIDs.length) + ");"
			);

			for (int j = 0, l = userIDs.length; j < l; j++) {
				statement.setInt(j + 1, userIDs[j]);
			}

			result = statement.executeQuery();

			while (result.next()) {
				users[i] = new User(result.getInt("userID"), result.getString("username"), result.getString("passwordHash"));
				i++;
			}

			return users;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}

	public static String[] getGroupsForUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}

		ArrayList<String> groups = new ArrayList<>(10);

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	tGroup.groupIdentifier AS groupIdentifier " +
				"FROM " +
				"	ticketsale_group tGroup " +
				"LEFT JOIN " +
				"	ticketsale_user_to_group toGroup " +
				"ON " +
				"	toGroup.groupID = tGroup.groupID " +
				"WHERE " +
				"	toGroup.userID = ?;"
			);

			statement.setInt(1, user.getUserID());

			result = statement.executeQuery();

			while (result.next()) {
				groups.add(result.getString("groupIdentifier"));
			}

			return groups.toArray(new String[groups.size()]);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}
}
