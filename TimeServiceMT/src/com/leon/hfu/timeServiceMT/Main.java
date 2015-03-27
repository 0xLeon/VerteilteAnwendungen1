package com.leon.hfu.timeServiceMT;

import java.io.IOException;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 * Entrance point for the program execution.
	 * Creates a new TimeServiceConnectionHandler which then listens
	 * for incomming connections.
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		try {
			new TimeServiceConnectionHandler();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
