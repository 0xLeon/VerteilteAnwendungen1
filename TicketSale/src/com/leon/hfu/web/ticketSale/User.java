/*
 * Copyright (C) 2015 Stefan Hahn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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

	public String getPasswordHash() {
		return this.passwordHash;
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
			this.groups = UserAdapter.getGroupsForUser(this);
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
