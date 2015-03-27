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
		this.connectionIn = new Scanner(this.serverInstance.getInputStream());
		this.connectionOut = new PrintStream(this.serverInstance.getOutputStream());
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
