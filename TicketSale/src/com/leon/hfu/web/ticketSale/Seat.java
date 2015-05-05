package com.leon.hfu.web.ticketSale;

/**
 * @author		Stefan Hahn
 */
public class Seat {
	private int seatID;

	private SeatStatus status;

	private Customer customer = Customer.DEFAULT_CUSTOMER;

	public Seat(int seatID, SeatStatus status, Customer customer) {
		this.seatID = seatID;
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

	public SeatStatus getStatus() {
		return this.status;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void reserve(Customer customer) throws EventException {
		if (this.status != SeatStatus.FREE) {
			throw new EventException();
		}

		this.status = SeatStatus.RESERVED;
		this.customer = customer;
	}

	public void buy(Customer customer) throws EventException {
		if (this.status == SeatStatus.SOLD) {
			throw new EventException();
		}

		if (((this.status == SeatStatus.RESERVED) && !this.customer.equals(customer))) {
			throw new EventException();
		}

		this.status = SeatStatus.SOLD;
		this.customer = customer;
	}

	public void cancel(Customer customer) throws EventException {
		if (this.status == SeatStatus.FREE) {
			throw new EventException();
		}

		if (!this.customer.equals(customer)) {
			throw new EventException();
		}

		this.status = SeatStatus.FREE;
		this.customer = null;
	}

	public void cancelReservation() {
		if (this.status == SeatStatus.RESERVED) {
			this.status = SeatStatus.FREE;
			this.customer = Customer.DEFAULT_CUSTOMER;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != Seat.class)) {
			return false;
		}

		return ((Seat) obj).getSeatID() == this.getSeatID();
	}
}
