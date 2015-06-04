package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.EventException;

/**
 * @author		Stefan Hahn
 */
public class Seat {
	private int seatID;

	private int seatNumber;

	private int eventID;

	private SeatStatus status;

	private User customer = User.DEFAULT_USER;

	public Seat(int seatID, int seatNumber, int eventID, SeatStatus status, User customer) {
		this.seatID = seatID;
		this.seatNumber = seatNumber;
		this.eventID = eventID;
		this.status = status;

		if ((this.status == SeatStatus.RESERVED) || this.status == SeatStatus.SOLD) {
			if ((customer == null) || this.customer.equals(customer)) {
				throw new IllegalArgumentException("Seat is reserved or sold but no customer was given.");
			}

			this.customer = customer;
		}
	}

	public int getSeatID() {
		return this.seatID;
	}

	public int getSeatNumber() {
		return this.seatNumber;
	}

	public int getEventID() {
		return this.eventID;
	}

	public SeatStatus getStatus() {
		return this.status;
	}

	public User getCustomer() {
		return this.customer;
	}

	public boolean isFree() {
		return (this.status == SeatStatus.FREE);
	}

	public boolean isReserved() {
		return (this.status == SeatStatus.RESERVED);
	}

	public boolean isSold() {
		return (this.status == SeatStatus.SOLD);
	}

	public void reserve(User customer) throws EventException {
		if (this.status != SeatStatus.FREE) {
			throw new EventException();
		}

		this.status = SeatStatus.RESERVED;
		this.customer = customer;

		this.updateData();
	}

	public void buy(User customer) throws EventException {
		if (this.status == SeatStatus.SOLD) {
			throw new EventException();
		}

		if (((this.status == SeatStatus.RESERVED) && !this.customer.equals(customer))) {
			throw new EventException();
		}

		this.status = SeatStatus.SOLD;
		this.customer = customer;

		this.updateData();
	}

	public void cancel(User customer) throws EventException {
		if (this.status == SeatStatus.FREE) {
			throw new EventException();
		}

		if (!this.customer.equals(customer)) {
			throw new EventException();
		}

		this.status = SeatStatus.FREE;
		this.customer = User.DEFAULT_USER;

		this.updateData();
	}

	public void cancelReservation() throws EventException {
		if (this.status == SeatStatus.RESERVED) {
			this.status = SeatStatus.FREE;
			this.customer = User.DEFAULT_USER;

			this.updateData();
		}
	}

	private void updateData() throws EventException {
		SeatAdapter.saveSeat(this);
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != Seat.class)) {
			return false;
		}

		return ((Seat) obj).getSeatID() == this.getSeatID();
	}
}
