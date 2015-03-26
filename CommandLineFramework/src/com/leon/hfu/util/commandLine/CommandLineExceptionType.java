package com.leon.hfu.util.commandLine;

/**
 * This enum holds all possible types of CommandLineExceptions.
 *
 * @author		Stefan Hahn
 * @see			CommandLineException#getType()
 */
public enum CommandLineExceptionType {
	/**
	 * Immediate next run of main method.
	 */
	CONTINUE,

	/**
	 * Immediate stop of program execution.
	 * There may be or may be not an error involved.
	 */
	BREAK,

	/**
	 * Unrecoverable error causing the program to stop.
	 */
	ERROR
}
