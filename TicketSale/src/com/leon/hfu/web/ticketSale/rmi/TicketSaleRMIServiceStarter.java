package com.leon.hfu.web.ticketSale.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author		Stefan Hahn
 */
public final class TicketSaleRMIServiceStarter {
	public static void startService() {
		try {
			LocateRegistry.createRegistry(1099);
			Naming.bind("TicketSale", new TicketSaleRMIService());
		}
		catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
