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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles incomming connections and delegates these
 * connections to their own thread. For every incomming
 * connection this class will create an own thread, which
 * handles further messages on these connections.
 *
 * @author		Stefan Hahn
 */
public class TimeServiceConnectionHandler {
	/**
	 * TCP port this connection handler listens on.
	 */
	public static final int PORT = 75;

	/**
	 * Server socket listenng on TCP port in #PORT constant.
	 */
	private ServerSocket serverSocket;

	/**
	 * Creates a new TimeServiceConnectionHandler object.
	 * Starts the listening socket and initiates listenng loop.
	 *
	 * @throws	IOException		Thrown when the listening socket cannot be establshed.
	 */
	public TimeServiceConnectionHandler() throws IOException {
		this.serverSocket = new ServerSocket(PORT);

		this.listen();
	}

	/**
	 * Runs the connection acceppting loop. Delegates all incomming
	 * connections to their own thread, which will handle this connection.
	 *
	 * @throws	IOException		Thrown when there is an error occurng during connection handling.
	 */
	private void listen() throws IOException {
		System.out.println("Waiting for clients on port " + this.serverSocket.getLocalPort());

		while (true) {
			Socket serverInstance = null;

			try {
				serverInstance = this.serverSocket.accept();

				System.out.println("Connection accepted from " + serverInstance.getRemoteSocketAddress());

				new Thread(new TimeService(serverInstance)).start();
			}
			catch (IOException e) {
				e.printStackTrace();

				if (serverInstance != null) {
					serverInstance.close();
				}
			}
		}
	}
}
