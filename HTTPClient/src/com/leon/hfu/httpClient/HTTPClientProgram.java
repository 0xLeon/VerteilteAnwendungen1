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
		System.out.print("Please enter an URL or exit to quit!\n> ");

		try {
			String inputLine = this.scanner.nextLine();

			if (inputLine.equals("exit")) {
				throw new CommandLineException(CommandLineExceptionType.BREAK);
			}

			HTTPClient.get(inputLine);
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
