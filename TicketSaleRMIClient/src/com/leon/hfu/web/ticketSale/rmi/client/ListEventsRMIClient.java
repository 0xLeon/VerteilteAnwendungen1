package com.leon.hfu.web.ticketSale.rmi.client;

import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.rmi.interfaces.TicketSale;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author		Stefan Hahn
 */
public class ListEventsRMIClient {
	public static void main(String[] args) {
		try {
			String rmiUrl = args[0];
			TicketSale ticketSale = (TicketSale) Naming.lookup(rmiUrl);
			ArrayList<Event> events = ticketSale.getEvents();

			if (events.size() > 0) {
				events.forEach(event -> System.out.println(event.getEventID() + " - " + event.getEventName()));
			}
			else {
				System.out.println("No events!");
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: listevents <rmi-url>");
			System.exit(1);
		}
		catch (NotBoundException | MalformedURLException | RemoteException | EventException e) {
			e.printStackTrace();
		}
	}
}
