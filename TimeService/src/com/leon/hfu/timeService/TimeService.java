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
package com.leon.hfu.timeService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class TimeService {
	/**
	 * Server port this service is listening on.
	 */
	public static final int PORT = 75;

	/**
	 * Entrance point for the program execution.
	 * Creates a server socket listening on port specified by #PORT.
	 * Blocks till connection is established and handles only one
	 * connection at once.
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket;
		String nextMessage;

		try {
			Socket server;
			Scanner connectionIn;
			PrintStream connectionOut;
			serverSocket = new ServerSocket(PORT);

			mainLoop:
			while (true) {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort());

				server = serverSocket.accept();

				System.out.println("Connection accepted from " + server.getRemoteSocketAddress());

				connectionIn = new Scanner(server.getInputStream());
				connectionOut = new PrintStream(server.getOutputStream());

				connectionOut.println("time service");

				connectionLoop:
				while (true) {
					nextMessage = connectionIn.nextLine();

					switch (nextMessage) {
						case "date":
							connectionOut.println(Clock.date());
							break;
						case "time":
							connectionOut.println(Clock.time());
							break;
						case "shutdown":
							System.out.println("Shutting down Time Service");
							connectionOut.println("end");
							server.close();
							break mainLoop;
						default: // end
							System.out.println("Closing connection from " + server.getRemoteSocketAddress());
							System.out.println("Last client message was »" + nextMessage + "«");
							connectionOut.println("end");
							server.close();
							break connectionLoop;
					}
				}
			}

			serverSocket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
