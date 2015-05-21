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

	public User[] getUsersByID(int[] userIDs) throws NoSuchUserException {
		if ((userIDs == null) || (userIDs.length == 0)) {
			throw new IllegalArgumentException();
		}

		User[] users = new User[userIDs.length];
		int i = 0;

		synchronized (this) {
			for (int userID : userIDs) {
				try {
					users[i] = this.userList.get(userID);

					if (users[i] == null) {
						throw new NoSuchUserException("User with userID »" + Integer.toString(i) + "« dosen't exist.");
					}

					i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					throw new NoSuchUserException("User with userID »" + Integer.toString(i) + "« dosen't exist.");
				}
			}

			if (i != users.length) {
				throw new IllegalStateException();
			}

			return users;
		}
	}

	public User[] getUsersByName(String[] usernames) throws NoSuchUserException {
		if ((usernames == null) || (usernames.length == 0)) {
			throw new IllegalArgumentException();
		}

		User[] users = new User[usernames.length];
		int[] i = {0};

		synchronized (this) {
			for (String username : usernames) {
				int c = i[0];

				this.userList.forEach(user -> {
					if (user.getUsername().equals(username)) {
						users[i[0]] = user;
						i[0]++;
					}
				});

				if (c == i[0]) {
					throw new NoSuchUserException("User »" + username + "« not found.");
				}
			}

			if (i[0] != users.length) {
				throw new IllegalStateException();
			}

			return users;
		}
	}
}
