package com.leon.hfu.web.ticketSale.rmi;

import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.EventAdapter;
import com.leon.hfu.web.ticketSale.Seat;
import com.leon.hfu.web.ticketSale.User;
import com.leon.hfu.web.ticketSale.UserAdapter;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;
import com.leon.hfu.web.ticketSale.rmi.interfaces.TicketSale;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public class TicketSaleRMIService extends UnicastRemoteObject implements TicketSale {
	public TicketSaleRMIService() throws RemoteException { }

	@Override
	public User getUser(int userID) throws NoSuchUserException, RemoteException {
		return UserAdapter.getUserByID(userID);
	}

	@Override
	public ArrayList<Event> getEvents() throws EventException, RemoteException {
		return EventAdapter.getAllEvents(0);
	}

	@Override
	public HashMap<Integer, Seat> getSeats(int eventID) throws EventException, RemoteException {
		return EventAdapter.getEventByID(eventID).getSeats();
	}

	@Override
	public void reserveSeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException {
		Event event = EventAdapter.getEventByID(eventID);
		User user = UserAdapter.getUserByID(userID);

		event.reserveSeats(new int[] {seatID}, user);
	}

	@Override
	public void buySeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException {
		Event event = EventAdapter.getEventByID(eventID);
		User user = UserAdapter.getUserByID(userID);

		event.buySeats(new int[] {seatID}, user);
	}

	@Override
	public void cancelSeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException {
		Event event = EventAdapter.getEventByID(eventID);
		User user = UserAdapter.getUserByID(userID);

		event.cancelSeats(new int[] {seatID}, user);
	}
}
