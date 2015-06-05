package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public class SeatAdapter {
	// TODO: ad method to create single seats

	public static void createSeatsForEvent(Event event, int seatCount) throws EventException {
		int eventID = event.getEventID();

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"INSERT INTO seat" +
				"	(seatNumber, eventID, seatStatus)" +
				"VALUES (?, ?, ?);"
			);

			statement.setInt(2, eventID);
			statement.setInt(3, SeatStatus.FREE.ordinal());
			for (int i = 1, l = seatCount + 1; i < l; i++) {
				statement.setInt(1, i);
				statement.execute();
			}
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			try {
				if (statement != null) statement.close();
			}
			catch (SQLException e) { }

			try {
				if (connection != null) connection.close();
			}
			catch (SQLException e) { }
		}
	}

	public static HashMap<Integer, Seat> getSeatsForEvent(Event event) throws EventException {
		int eventID = event.getEventID();
		HashMap<Integer, Seat> seats = new HashMap<>(100);

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"SELECT " +
				"	seat.seatID AS seatID, " +
				"	seat.seatNumber AS seatNumber, " +
				"	seat.seatStatus AS seatStatus, " +
				"	user.userID AS userID, " +
				"	user.username AS username, " +
				"	user.passwordHash AS passwordHash " +
				"FROM " +
				"	seat " +
				"LEFT JOIN " +
				"	user ON seat.userID = user.userID " +
				"WHERE " +
				"	seat.eventID = ? " +
				"ORDER BY " +
				"	seat.seatNumber ASC;"
			);

			statement.setInt(1, eventID);

			result = statement.executeQuery();
			User user;
			Seat seat;
			while (result.next()) {
				if (result.getInt("userID") == 0) {
					user = null;
				}
				else {
					user = new User(result.getInt("userID"), result.getString("username"), result.getString("passwordHash"));
				}

				seat = new Seat(result.getInt("seatID"), result.getInt("seatNumber"), eventID, SeatStatus.values()[result.getInt("seatStatus")], user);

				seats.put(result.getInt("seatID"), seat);
			}

			return seats;
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(result, statement, connection);
		}
	}

	protected static void saveSeat(Seat seat) throws EventException {
		Connection connection = null;
		PreparedStatement statement = null;

		if ((seat.getCustomer().getUserID() == 0) && (seat.getStatus().ordinal() > SeatStatus.FREE.ordinal())) {
			throw new EventException("User can't be null if seat is reserved or purchased.");
		}

		try {
			connection = Core.getInstance().getDatabaseConnection();
			statement = connection.prepareStatement(
				"UPDATE seat " +
				"SET " +
				"	seatStatus = ?, " +
				"	userID = ? " +
				"WHERE " +
				"	seatID = ?;"
			);

			statement.setInt(1, seat.getStatus().ordinal());
			statement.setInt(3, seat.getSeatID());

			if (seat.getCustomer().getUserID() > 0) {
				statement.setInt(2, seat.getCustomer().getUserID());
			}
			else {
				statement.setNull(2, Types.NULL);
			}

			statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new EventException(e);
		}
		finally {
			SQLUtil.close(null, statement, connection);
		}
	}
}
