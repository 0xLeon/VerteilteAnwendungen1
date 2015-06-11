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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public class SQLUtil {
	private static HashMap<Integer, String> inPlaceHolders = new HashMap<>(10);

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
		if (!SQLUtil.inPlaceHolders.containsKey(length)) {
			StringBuilder placeholders = new StringBuilder();

			for (int i = 0; i < length; i++) {
				placeholders.append("?, ");
			}

			SQLUtil.inPlaceHolders.put(length, placeholders.substring(0, placeholders.length() - 2));
		}

		return SQLUtil.inPlaceHolders.get(length);
	}
}
