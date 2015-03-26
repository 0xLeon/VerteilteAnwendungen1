package com.leon.hfu.timeClient;

import com.leon.hfu.util.commandLine.CommandLineExecutor;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 * TimeServiceClientProgram instance holding the main program.
	 */
	public static TimeServiceClientProgram program;

	/**
	 * Starts this program.
	 * Creates an instance of TimeServiceClientProgram and passes it
	 * to the executing method.
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		program = new TimeServiceClientProgram();

		CommandLineExecutor.run(program, args);
	}
}
