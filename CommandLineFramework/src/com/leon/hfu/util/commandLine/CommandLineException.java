package com.leon.hfu.util.commandLine;

/**
 * Execption used to controll the execution of a CommandLineProgram program.
 * The main method CommandLineProgram#execute() is able to continue or break
 * the program execution or can wrap errors which would cause the whole program
 * to exit.
 *
 * @author		Stefan Hahn
 */
public class CommandLineException extends Exception {
	/**
	 * CommandLineExceptionType of this CommandLineException.
	 */
	private CommandLineExceptionType type;

	/**
	 * Creates a new CommandLineExecption instance with specified exception type.
	 *
	 * @param	type		Type of this CommandLineException. See #getType() for more information.
	 */
	public CommandLineException(CommandLineExceptionType type) {
		this(type, null);
	}

	/**
	 * Creates a new CommandLineExecption instance with specified cause.
	 * The exception type is always CommandLineExceptionType#ERROR when
	 * created with this constructor
	 *
	 * @param	e		The causing exception
	 */
	public CommandLineException(Throwable e) {
		this(CommandLineExceptionType.ERROR, e);
	}

	/**
	 * Creates a new CommandLineExecption instance with specified type and cause.
	 *
	 * @param	type		Type of this CommandLineException. See #getType for more information.
	 * @param	e		The causing exception.
	 */
	public CommandLineException(CommandLineExceptionType type, Throwable e) {
		super(e);

		this.type = type;
	}

	/**
	 * Returns the CommandLineExceptionType of this CommandLineException.
	 *
	 * CommandLineExceptionType.CONTINUE
	 * Used to stop the current run of the CommandLineProgram#execute() method
	 * immediately and start a new run. Usually no other error is involved.
	 *
	 * CommandLineExceptionType.BREAK
	 * Used to stop the current run of the CommandLineProgram#execute() method
	 * immediately and stop the program as well. An unrecoverable error may be
	 * involved, but this is the default way to exit an CommandLineProgram as well.
	 *
	 * CommandLineExceptionType.ERROR
	 * Used to indicate an unrecoverable error which can't be handled by the
	 * program itself. The executing method has to handle the given cause.
	 *
	 * @return			The CommandLineExceptionType of this CommandLineException.
	 */
	public CommandLineExceptionType getType() {
		return this.type;
	}
}
