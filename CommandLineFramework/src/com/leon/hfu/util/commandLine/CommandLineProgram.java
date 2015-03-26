package com.leon.hfu.util.commandLine;

/**
 * Interface for programs which should be executable
 * by this command line framework.
 *
 * @author		Stefan Hahn
 */
public interface CommandLineProgram {
	/**
	 * Initializes the program.
	 * This method should contain all tasks, which have to be
	 * completet, before the program can run ts functions.
	 * These tasks are things like establishing database connections
	 * or creating network sockets.
	 *
	 * @param	args		Command line arguments
	 */
	public void initialize(String[] args);

	/**
	 * Main method which is called in an endless loop.
	 * Ths method contains the function of the program.
	 * The program flow can be controlled with CommandLineExceptions
	 *
	 * @throws	CommandLineException		Thrown to controll the program flow
	 * @see		CommandLineException#getType()
	 */
	public void execute() throws CommandLineException;

	/**
	 * Ths method finishes the program and completes necessary tasks
	 * like closing streams and sockets or database connections.
	 */
	public void finish();
}
