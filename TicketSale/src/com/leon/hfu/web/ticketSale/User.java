package com.leon.hfu.web.ticketSale;

/**
 * @author		Stefan Hahn
 */
public class User {
	private int userID;
	private String username;
	private String[] groups;

	public User(int userID, String username) {
		if (userID < 0) {
			throw new IllegalArgumentException();
		}

		if ((username == null) || username.equals("")) {
			throw new IllegalArgumentException();
		}

		this.userID = userID;
		this.username = username;
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
}
