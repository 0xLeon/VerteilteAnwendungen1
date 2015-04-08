package com.leon.hfu.httpClient;

import com.leon.hfu.util.commandLine.CommandLineExecutor;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 *
	 */
	public static HTTPClientProgram program;

	/**
	 *
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		program = new HTTPClientProgram();

		CommandLineExecutor.run(program, args);
	}
}
