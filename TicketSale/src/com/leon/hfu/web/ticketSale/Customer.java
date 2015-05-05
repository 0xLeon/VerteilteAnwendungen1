package com.leon.hfu.web.ticketSale;

/**
 * @author		Stefan Hahn
 */
public class Customer extends User {
	public static final Customer DEFAULT_CUSTOMER = new Customer(0, "default");

	public Customer(int userID, String username) {
		super(userID, username);
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != Customer.class)) {
			return false;
		}

		return ((Customer) obj).getUserID() == this.getUserID();
	}
}
