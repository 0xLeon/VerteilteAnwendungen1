package com.leon.hfu.timeClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class TimeServiceClient {
	public static String dateFromServer(String serverAddress) throws IOException {
		return getMessageFromServer(serverAddress, "date");
	}

	public static String timeFromServer(String serverAddress) throws IOException {
		return getMessageFromServer(serverAddress, "time");
	}

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
