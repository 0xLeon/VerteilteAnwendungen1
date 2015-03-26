package com.leon.hfu.util.commandLine;

/**
 * @author		Stefan Hahn
 */
public interface CommandLineProgram {
	/**
	 *
	 * @param	args
	 */
	public void initialize(String[] args);

	/**
	 *
	 * @throws	CommandLineException
	 */
	public void execute() throws CommandLineException;

	/**
	 *
	 */
	public void finish();
}
