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
package com.leon.hfu.timeServiceMT;

import java.io.IOException;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 * Entrance point for the program execution.
	 * Creates a new TimeServiceConnectionHandler which then listens
	 * for incomming connections.
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		try {
			new TimeServiceConnectionHandler();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
