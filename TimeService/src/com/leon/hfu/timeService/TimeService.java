package com.leon.hfu.timeService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class TimeService {
	/**
	 * Server port this service is listening on.
	 */
	public static final int PORT = 75;

	/**
	 *
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
