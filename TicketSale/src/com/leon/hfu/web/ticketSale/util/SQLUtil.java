package com.leon.hfu.web.ticketSale.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author		Stefan Hahn
 */
public class SQLUtil {
	public static void close(ResultSet result, Statement statement, Connection connection) {
		try {
			if (result != null) result.close();
		}
		catch (SQLException e) { }

		try {
			if (statement != null) statement.close();
		}
		catch (SQLException e) { }

		try {
			if (connection != null) connection.close();
		}
		catch (SQLException e) { }
	}

	public static String getInPlaceholders(int length) {
		StringBuilder placeholders = new StringBuilder();

		for (int i = 0; i < length; i++) {
			placeholders.append("?, ");
		}

		return placeholders.toString().substring(0, placeholders.toString().length() - 2);
	}
}
