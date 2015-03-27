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
	 * Server socket listenng on TCP port in #PORT
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
