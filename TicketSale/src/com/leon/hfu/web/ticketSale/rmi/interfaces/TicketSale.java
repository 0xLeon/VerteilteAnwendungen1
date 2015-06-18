package com.leon.hfu.web.ticketSale.rmi.interfaces;

import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.Seat;
import com.leon.hfu.web.ticketSale.User;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
public interface TicketSale extends Remote {
	public User getUser(int userID) throws NoSuchUserException, RemoteException;

	public ArrayList<Event> getEvents() throws EventException, RemoteException;

	public HashMap<Integer, Seat> getSeats(int eventID) throws EventException, RemoteException;

	public void reserveSeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException;

	public void buySeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException;

	public void cancelSeat(int eventID, int seatID, int userID) throws EventException, NoSuchUserException, RemoteException;
}
