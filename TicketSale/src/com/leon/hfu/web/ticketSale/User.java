package com.leon.hfu.web.ticketSale;

import com.leon.hfu.web.ticketSale.exception.NoSuchUserException;

/**
 * @author		Stefan Hahn
 */
public class User {
	// TODO: add password hashes for correct login
	public static final User DEFAULT_USER = new User(0, "default", "", new String[0]);

	private int userID;
	private String username;
	private String passwordHash;
	private String[] groups = null;

	public User(int userID, String username, String passwordHash, String[] groups) {
		this(userID, username, passwordHash);

		this.groups = groups;
	}

	public User(int userID, String username, String passwordHash) {
		if (userID < 0) {
			throw new IllegalArgumentException();
		}

		if ((username == null) || username.equals("")) {
			throw new IllegalArgumentException();
		}

		this.userID = userID;
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public int getUserID() {
		return this.userID;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isInGroup(String searchedGroup) {
		this.lazyLoadGroups();

		for (String group: this.groups) {
			if (group.equals(searchedGroup)) {
				return true;
			}
		}

		return false;
	}

	public void lazyLoadGroups(boolean forceReload) {
		if ((this.groups == null) || forceReload) {
			this.groups = UserHandler.getInstance().getGroupsForUser(this);
		}
	}

	public void lazyLoadGroups() {
		this.lazyLoadGroups(false);
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != User.class)) {
			return false;
		}

		return ((User) obj).getUserID() == this.getUserID();
	}
}
