package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.EventException;

import java.util.Date;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public class Event {
	private int eventID;
	private String eventName;
	private String description;
	private Date reservationDeadline;
	private Date purchaseDeadline;
	private HashMap<Integer, Seat> seats = null;

	public Event(int eventID, String eventName, String description, Date reservationDeadline, Date purchaseDeadline) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.description = description;
		// TODO: check reservationDeadline
		this.reservationDeadline = reservationDeadline;
		this.purchaseDeadline = purchaseDeadline;
	}

	public int getEventID() {
		return this.eventID;
	}

	public String getEventName() {
		return this.eventName;
	}

	public String getDescription() {
		return this.description;
	}

	public HashMap<Integer, Seat> getSeats() throws EventException {
		this.lazyLoadSeats();

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
			this.lazyLoadSeats(true);

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
			this.lazyLoadSeats(true);

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
			this.lazyLoadSeats(true);

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
			try {
				this.lazyLoadSeats(true);
				this.seats.entrySet().forEach(entry -> {
					if (entry.getValue().getStatus() == SeatStatus.RESERVED) {
						try {
							entry.getValue().cancelReservation();
						}
						catch (EventException e) {
							throw new RuntimeException(e);
						}
					}
				});
			}
			catch (RuntimeException e) {
				if ((e.getCause() != null) && (e.getCause() instanceof EventException)) {
					throw (EventException) e.getCause();
				}
				else {
					throw e;
				}
			}
		}
	}

	public void lazyLoadSeats(boolean forceReload) throws EventException {
		if ((this.seats == null) || forceReload) {
			this.seats = SeatAdapter.getSeatsForEvent(this);
		}
	}

	public void lazyLoadSeats() throws EventException {
		this.lazyLoadSeats(false);
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
