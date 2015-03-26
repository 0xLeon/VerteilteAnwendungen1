package com.leon.hfu.timeServiceMT;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class TimeServiceConnectionHandler {
	public static final int PORT = 75;

	private ServerSocket serverSocket;

	/**
	 *
	 *
	 * @throws	IOException
	 */
	public TimeServiceConnectionHandler() throws IOException {
		this.serverSocket = new ServerSocket(PORT);

		this.listen();
	}

	/**
	 *
	 *
	 * @throws	IOException
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
