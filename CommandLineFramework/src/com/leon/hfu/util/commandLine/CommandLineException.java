package com.leon.hfu.util.commandLine;

/**
 * @author		Stefan Hahn
 */
public class CommandLineException extends Exception {
	private CommandLineExceptionType type;

	/**
	 *
	 *
	 * @param	type
	 */
	public CommandLineException(CommandLineExceptionType type) {
		this(type, null);
	}

	/**
	 *
	 *
	 * @param	e
	 */
	public CommandLineException(Throwable e) {
		this(CommandLineExceptionType.ERROR, e);
	}

	/**
	 *
	 *
	 * @param	type
	 * @param	e
	 */
	public CommandLineException(CommandLineExceptionType type, Throwable e) {
		super(e);

		this.type = type;
	}

	/**
	 *
	 * @return
	 */
	public CommandLineExceptionType getType() {
		return this.type;
	}
}
