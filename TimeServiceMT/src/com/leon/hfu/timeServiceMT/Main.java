package com.leon.hfu.timeServiceMT;

import java.io.IOException;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 *
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
