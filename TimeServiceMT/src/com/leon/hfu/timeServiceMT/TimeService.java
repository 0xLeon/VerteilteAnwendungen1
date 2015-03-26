package com.leon.hfu.timeServiceMT;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class TimeService implements Runnable {
	private Socket serverInstance;
	private Scanner connectionIn;
	private PrintStream connectionOut;

	/**
	 *
	 *
	 * @param	serverInstance
	 * @throws	IOException
	 */
	public TimeService(Socket serverInstance) throws IOException {
		this.serverInstance = serverInstance;
		this.connectionIn = new Scanner(serverInstance.getInputStream());
		this.connectionOut = new PrintStream(serverInstance.getOutputStream());
	}

	/**
	 *
	 *
	 * @see		Runnable#run()
	 */
	@Override
	public void run() {
		String nextMessage;

		this.connectionOut.println("time service");

		connectionLoop:
		while (serverInstance.isConnected()) {
			nextMessage = connectionIn.nextLine();

			switch (nextMessage) {
				case "date":
					connectionOut.println(Clock.date());
					break;
				case "time":
					connectionOut.println(Clock.time());
					break;
				// case "shutdown":
				// 	System.out.println("Shutting down Time Service");
				// 	connectionOut.println("end");
				// 	break mainLoop;
				default: // end
					System.out.println("Closing connection from " + serverInstance.getRemoteSocketAddress());
					System.out.println("Last client message was »" + nextMessage + "«");
					connectionOut.println("end");
					break connectionLoop;
			}
		}

		try {
			this.connectionOut.close();
			this.connectionIn.close();
			serverInstance.close();
		}
		catch (IOException e) { }
	}
}
