/*
 * Copyright (C) 2015 Stefan Hahn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.leon.hfu.httpClient;

import com.leon.hfu.util.commandLine.CommandLineException;
import com.leon.hfu.util.commandLine.CommandLineExceptionType;
import com.leon.hfu.util.commandLine.CommandLineProgram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Represents a simple HTTP client program which prompts
 * for an URL and fires an HTTP request. Display some information
 * and the content of the resulting response.
 *
 * @author		Stefan Hahn
 */
public class HTTPClientProgram implements CommandLineProgram {
	/**
	 * Formater used to display dates in human readable form.
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");

	/**
	 * Scanner instance used to read from STDIN.
	 */
	private Scanner scanner;

	// static initializer
	static {
		HTTPClientProgram.DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Initializes this program.
	 * Creates a Scanner instance on STDIN.
	 *
	 * @param	args		Command line arguments
	 * @see		CommandLineProgram#initialize(String[])
	 */
	@Override
	public void initialize(String[] args) {
		this.scanner = new Scanner(System.in);
	}

	/**
	 * Executes the main part of this program.
	 * Promts for an URL and executes an HTTP request with the given URL.
	 * Displays various information about the response.
	 *
	 * @throws	CommandLineException		Thrown to control programm execution.
	 * @see		CommandLineProgram#execute()
	 */
	@Override
	public void execute() throws CommandLineException {
		System.out.print("Please enter an URL or exit to quit!\n> ");

		try {
			String inputLine = this.scanner.nextLine();

			if (inputLine.equals("exit")) {
				throw new CommandLineException(CommandLineExceptionType.BREAK);
			}

			HTTPRequest request = (new HTTPRequest(inputLine)).execute().parse();

			System.out.println("------------------------------");
			System.out.println("URL Exists: " + request.urlExists());
			System.out.println("Status: " + request.getStatusCode() + " " + request.getStatusText());
			System.out.println("Last Modified Date: " + HTTPClientProgram.DATE_FORMAT.format(request.getLastModifiedDate()));

			System.out.println("------------------------------");
			System.out.println("Response Header:");
			System.out.println(request.getRawHeader());

			System.out.println("------------------------------");
			System.out.println("Response Body:");
			System.out.println(request.getRawBody());
			System.out.println("------------------------------");
		}
		catch (NoSuchElementException e) {
			System.err.println("\nCouldn't read input!");

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
		catch (MalformedURLException e) {
			System.err.println("Invalid URL entered!");

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
		catch (ParseException | IOException e) {
			System.err.println(e.getMessage());

			throw new CommandLineException(CommandLineExceptionType.CONTINUE);
		}
	}

	/**
	 * Finishes this program.
	 * Closes the Scanner instance running on STDIN.
	 *
	 * @see		CommandLineProgram#finish()
	 */
	@Override
	public void finish() {
		this.scanner.close();
	}
}
