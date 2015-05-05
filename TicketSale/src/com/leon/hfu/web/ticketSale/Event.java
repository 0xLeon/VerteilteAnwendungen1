package com.leon.hfu.web.ticketSale;

import java.util.Date;

/**
 * @author		Stefan Hahn
 */
public class Event {
	private Date date;
	private Seat[] seats;
	private boolean reservationPossible;

	public Event(Date date, int seatCount) {
		if (seatCount < 1) {
			throw new IllegalArgumentException();
		}

		// TODO: check date
		this.date = date;
		this.seats = new Seat[seatCount];

		for (seatCount--; seatCount > -1; seatCount--) {
			this.seats[seatCount] = new Seat(seatCount, SeatStatus.FREE, null);
		}
	}

	public void reserveSeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats[seatID].reserve(customer);
			}
		}
	}

	public void buySeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats[seatID].buy(customer);
			}
		}
	}

	public void cancelSeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats[seatID].cancel(customer);
			}
		}
	}

	public void cancelReservations(User user) throws EventException {
		if ((user == null) || (user.isInGroup("user.admin"))) {
			throw new EventException();
		}

		synchronized (this) {
			for (Seat seat : this.seats) {
				if (seat.getStatus() == SeatStatus.RESERVED) {
					seat.cancelReservation();
				}
			}
		}
	}


	private void checkCustomer(User customer) throws EventException {
		if ((customer == null) || (customer.equals(User.DEFAULT_USER))) {
			throw new EventException();
		}
	}

	private void checkSeatID(int seatID) throws EventException {
		if ((seatID < 0) || (seatID >= this.seats.length)) {
			throw new EventException();
		}
	}
}
