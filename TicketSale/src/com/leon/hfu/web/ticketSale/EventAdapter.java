package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author		Stefan Hahn
 */
public class EventAdapter {
	public static Event createEvent(String eventName, String description, Date reservationDeadline, Date purchaseDeadline, int seatCount) throws EventException {
		Event event = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"INSERT INTO event " +
				"	(eventName, description, reservationDeadline, purchaseDeadline) " +
				"VALUES " +
				"	(?, ?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS
			);

			statement.setString(1, eventName);
			statement.setString(2, description);
			statement.setLong(3, reservationDeadline.getTime());
			statement.setLong(4, purchaseDeadline.getTime());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating event failed.");
			}

			generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				event = new Event(generatedKeys.getInt(1), eventName, description, reservationDeadline, purchaseDeadline);
			}
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(generatedKeys, statement, connection);
		}

		if (event != null) {
			SeatAdapter.createSeatsForEvent(event, seatCount);

			return event;
		}
		else {
			throw new EventException("Creating event failed.");
		}
	}

	public static Event getEventByID(int eventID) throws EventException {
		if (eventID < 1) {
			throw new IllegalArgumentException("Invalid eventID");
		}

		Date reserveationDeadline;
		Date purchaseDeadline;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	* " +
				"FROM " +
				"	event " +
				"WHERE " +
				"	eventID = ?;"
			);

			statement.setInt(1, eventID);

			result = statement.executeQuery();

			if (result.next()) {
				reserveationDeadline = new Date(result.getLong("reservationDeadline"));
				purchaseDeadline = new Date(result.getLong("purchaseDeadline"));

				return new Event(result.getInt("eventID"), result.getString("eventName"), result.getString("description"), reserveationDeadline, purchaseDeadline);
			}
			else {
				throw new SQLException();
			}
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}

	public static ArrayList<Event> getAllEvents(int limit) throws EventException {
		ArrayList<Event> events;

		if (limit > 0) {
			events = new ArrayList<>(limit);
		}
		else {
			events = new ArrayList<>(20);
		}

		Date reserveationDeadline;
		Date purchaseDeadline;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	* " +
				"FROM " +
				"	event;"
			);

			result = statement.executeQuery();

			while (result.next()) {
				reserveationDeadline = new Date(result.getLong("reservationDeadline"));
				purchaseDeadline = new Date(result.getLong("purchaseDeadline"));

				events.add(new Event(result.getInt("eventID"), result.getString("eventName"), result.getString("description"), reserveationDeadline, purchaseDeadline));
			}

			return events;
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}

	public static void deleteEvent(Event event) throws EventException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"DELETE FROM " +
				"	event " +
				"WHERE " +
				"	eventID = ?;"
			);

			statement.setInt(1, event.getEventID());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Error on deleting event with eventID »" + event.getEventID() + "«.");
			}
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}
}
