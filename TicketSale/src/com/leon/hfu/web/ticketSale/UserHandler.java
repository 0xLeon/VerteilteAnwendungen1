package com.leon.hfu.web.ticketSale;

import java.util.Vector;

/**
 * Created by Stefan on 05.05.2015.
 */
public class UserHandler {
	private static UserHandler instance = null;

	private Vector<User> userList = new Vector<>();

	private UserHandler() {
		this.userList.add(User.DEFAULT_USER);
		// TODO: add password hashes for correct login
		this.userList.add(new User(1, "Leon", "", new String[] {"user.admin", "user.customer"}));
		this.userList.add(new User(2, "Test", "", new String[] {"user.customer"}));
	}

	public static UserHandler getInstance() {
		if (UserHandler.instance == null) {
			UserHandler.instance = new UserHandler();
		}

		return UserHandler.instance;
	}

	public User createUser(String username, String password, String[] groups) {
		// TODO: implement

		return User.DEFAULT_USER;
	}

	public void login(int username, String password) {
		// TODO: implement
	}

	public User[] getUsersByID(int[] userIDs) {
		if ((userIDs == null) || (userIDs.length == 0)) {
			throw new IllegalArgumentException();
		}

		User[] users = new User[userIDs.length];
		int i = 0;

		synchronized (this) {
			for (int userID : userIDs) {
				users[i] = this.userList.get(userID);
			}

			return users;
		}
	}
}
