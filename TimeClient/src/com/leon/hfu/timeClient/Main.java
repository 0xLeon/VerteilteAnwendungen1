package com.leon.hfu.timeClient;

import com.leon.hfu.util.commandLine.CommandLineExecutor;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class Main {
	public static TimeServiceClientProgram program;

	/**
	 *
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		program = new TimeServiceClientProgram();

		CommandLineExecutor.run(program, args);
	}
}
