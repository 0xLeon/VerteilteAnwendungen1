package com.leon.hfu.web.ticketSale;

/**
 * @author		Stefan Hahn
 */
public class User {
	// TODO: add password hashes for correct login
	public static final User DEFAULT_USER = new User(0, "default", "", new String[0]);

	private int userID;
	private String username;
	private String passwordHash;
	private String[] groups;

	public User(int userID, String username, String passwordHash, String[] groups) {
		if (userID < 0) {
			throw new IllegalArgumentException();
		}

		if ((username == null) || username.equals("")) {
			throw new IllegalArgumentException();
		}

		this.userID = userID;
		this.username = username;
		this.passwordHash = passwordHash;
		this.groups = groups;
	}

	public int getUserID() {
		return this.userID;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isInGroup(String searchedGroup) {
		for (String group: this.groups) {
			if (group.equals(searchedGroup)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != User.class)) {
			return false;
		}

		return ((User) obj).getUserID() == this.getUserID();
	}

	public static User getUserByUserID(int userID) throws NoSuchUserException {
		if (userID < 0) {
			throw new IllegalArgumentException();
		}

		return UserHandler.getInstance().getUsersByID(new int[] {userID})[0];
	}

	public static User getUserByUsername(String username) throws NoSuchUserException {
		if ((username == null) || (username.length() == 0)) {
			throw new IllegalArgumentException();
		}

		return UserHandler.getInstance().getUsersByName(new String[] {username})[0];
	}
}
