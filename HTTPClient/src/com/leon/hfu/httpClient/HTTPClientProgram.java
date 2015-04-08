package com.leon.hfu.httpClient;

import com.leon.hfu.util.commandLine.CommandLineException;
import com.leon.hfu.util.commandLine.CommandLineExceptionType;
import com.leon.hfu.util.commandLine.CommandLineProgram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class HTTPClientProgram implements CommandLineProgram {
	/**
	 * Scanner instance used to read from STDIN.
	 */
	private Scanner scanner;

	/**
	 * Specified command ID from STDIN.
	 */
	private int command;

	/**
	 *
	 */
	private String targetURL;

	/**
	 *
	 *
	 * @param	args		Command line arguments
	 */
	@Override
	public void initialize(String[] args) {
		this.scanner = new Scanner(System.in);
	}

	/**
	 *
	 * @throws	CommandLineException
	 */
	@Override
	public void execute() throws CommandLineException {
		System.out.print("Please enter an URL!\n> ");

		try {
			this.targetURL = this.scanner.nextLine();
			HTTPClient.get(this.targetURL);
		}
		catch (NoSuchElementException e) {
			System.err.println("\nCouldn't read input!");

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
		catch (MalformedURLException e) {
			System.err.println("Invalid URL entered!");

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
		catch (IOException e) {
			System.err.println(e.getMessage());

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
	}

	/**
	 *
	 */
	@Override
	public void finish() {
		this.scanner.close();
	}
}
