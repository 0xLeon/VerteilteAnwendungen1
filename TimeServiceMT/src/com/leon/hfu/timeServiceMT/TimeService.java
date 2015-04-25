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
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * A Time Service implementation ready for multi-threaded use.
 * Each instance of this class is able to process a single client
 * connection and handle incomming message and outgoing response.
 *
 * @author		Stefan Hahn
 */
public class TimeService implements Runnable {
	/**
	 * Client connection socket, which handles the communcation
	 * with the connected client.
	 */
	private Socket serverInstance;

	/**
	 * Wrapped input stream, which enabled the service to read
	 * input data sent by the client.
	 */
	private Scanner connectionIn;

	/**
	 * Wrapped output stream, which enabled the service to write
	 * output data sendng it to the client.
	 */
	private PrintStream connectionOut;

	/**
	 * Creates a new TimeService objects.
	 * Stores the client connection socket and creates streams
	 * which enable easier handlng of message transfer.
	 *
	 * @param	serverInstance		Client connection this thread should handle.
	 * @throws	IOException		Thrown when there is an error wrapping input or output streams.
	 */
	public TimeService(Socket serverInstance) throws IOException {
		this.serverInstance = serverInstance;
		this.connectionIn = new Scanner(this.serverInstance.getInputStream());
		this.connectionOut = new PrintStream(this.serverInstance.getOutputStream());
	}

	/**
	 * Executes the message handling loop.
	 * This loop runs in an own thread, thus won't block other incomming
	 * connections. When the connection loop is finished (end message),
	 * this thread will be suspended.
	 *
	 * @see		Runnable#run()
	 */
	@Override
	public void run() {
		String nextMessage;

		this.connectionOut.println("time service");

		connectionLoop:
		while (serverInstance.isConnected()) {
			nextMessage = this.connectionIn.nextLine();

			switch (nextMessage) {
				case "date":
					this.connectionOut.println(Clock.date());
					break;
				case "time":
					this.connectionOut.println(Clock.time());
					break;
				// case "shutdown":
				// 	System.out.println("Shutting down Time Service");
				// 	this.connectionOut.println("end");
				// 	break mainLoop;
				default: // end
					System.out.println("Closing connection from " + this.serverInstance.getRemoteSocketAddress());
					System.out.println("Last client message was »" + nextMessage + "«");
					this.connectionOut.println("end");
					break connectionLoop;
			}
		}

		try {
			this.connectionOut.close();
			this.connectionIn.close();
			this.serverInstance.close();
		}
		catch (IOException e) { }
	}
}
