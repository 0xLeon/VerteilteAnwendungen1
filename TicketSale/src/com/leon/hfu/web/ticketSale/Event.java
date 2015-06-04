package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.EventException;

import java.util.Date;
import java.util.Vector;

/**
 * @author		Stefan Hahn
 */
public class Event {
	private Date reservationDeadline;
	private Date purchaseDeadline;
	private Vector<Seat> seats;

	public Event(Date reservationDeadline, Date purchaseDeadline, int seatCount) {
		if (seatCount < 1) {
			throw new IllegalArgumentException();
		}

		// TODO: check reservationDeadline
		this.reservationDeadline = reservationDeadline;
		this.purchaseDeadline = purchaseDeadline;
		this.seats = new Vector<>(seatCount);

	}

	public Vector<Seat> getSeats() {
		return this.seats;
	}

	public Date getReservationDeadline() {
		return this.reservationDeadline;
	}

	public Date getPurchaseDeadline() {
		return this.purchaseDeadline;
	}

	public boolean isReservationPossible() {
		return (new Date()).before(this.reservationDeadline);
	}

	public boolean isPurchasePossible() {
		return (new Date()).before(this.purchaseDeadline);
	}

	public void reserveSeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		if (!this.isReservationPossible()) {
			throw new EventException("Reservation deadline reached.");
		}

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats.get(seatID).reserve(customer);
			}
		}
	}

	public void buySeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		if (!this.isPurchasePossible()) {
			throw new EventException("Purchase deadline reached.");
		}

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats.get(seatID).buy(customer);
			}
		}
	}

	public void cancelSeats(int[] seatIDs, User customer) throws EventException {
		this.checkCustomer(customer);

		synchronized (this) {
			// TODO: transaction style, revert all if an error occurs
			for (int seatID : seatIDs) {
				this.checkSeatID(seatID);
				this.seats.get(seatID).cancel(customer);
			}
		}
	}

	public void cancelReservations(User user) throws EventException {
		if ((user == null) || !(user.isInGroup("user.admin"))) {
			throw new EventException();
		}

		synchronized (this) {
			this.seats.forEach(seat -> {
				if (seat.getStatus() == SeatStatus.RESERVED) {
					seat.cancelReservation();
				}
			});
		}
	}

	private void checkCustomer(User customer) throws EventException {
		if ((customer == null) || (customer.equals(User.DEFAULT_USER))) {
			throw new EventException("Invalid User.");
		}
	}

	private void checkSeatID(int seatID) throws EventException {
		if ((seatID < 0) || (seatID >= this.seats.size())) {
			throw new EventException("Invalid seatID.");
		}
	}
}
