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
package com.leon.hfu.timeClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Provides methods to get date and time from a server.
 *
 * @author		Stefan Hahn
 */
public class TimeServiceClient {
	/**
	 * Gets current date from server and returns the date as String.
	 * A Time Service has to be running on TCP port 75 on the
	 * specified server.
	 *
	 * @param	serverAddress		Server address running a Time Service on TCP port 75
	 * @return				Current date as String
	 * @throws	IOException		Thrown when there is an error when trying to receive the current date.
	 */
	public static String dateFromServer(String serverAddress) throws IOException {
		return getMessageFromServer(serverAddress, "date");
	}

	/**
	 * Gets current time from server and returns the time as String.
	 * A Time Service has to be running on TCP port 75 on the
	 * specified server.
	 *
	 * @param	serverAddress		Server address running a Time Service on TCP port 75
	 * @return				Current time as String
	 * @throws	IOException		Thrown when there is an error when trying to receive the current time.
	 */
	public static String timeFromServer(String serverAddress) throws IOException {
		return getMessageFromServer(serverAddress, "time");
	}

	/**
	 * Sends a message to a given server on TCP port 75.
	 * Returns the answer from the server as string.
	 *
	 * @param	serverAddress		Server address running a Time Service on port 75
	 * @param	message			Message sent to Time Service
	 * @return				The answer from Time Service as String.
	 * @throws	IOException		Thrown when there is an error when trying to communicate with the Time Service.
	 */
	private static String getMessageFromServer(String serverAddress, String message) throws IOException {
		Socket client = new Socket(serverAddress, 75);
		Scanner connectionIn = new Scanner(client.getInputStream());
		PrintStream connectionOut = new PrintStream(client.getOutputStream());
		String serverMessage;

		serverMessage = connectionIn.nextLine();

		if (!serverMessage.equals("time service")) {
			throw new IOException();
		}

		connectionOut.println(message);

		serverMessage = connectionIn.nextLine();

		connectionOut.println("end");
		connectionIn.close();
		connectionOut.close();
		client.close();

		return serverMessage;
	}
}
