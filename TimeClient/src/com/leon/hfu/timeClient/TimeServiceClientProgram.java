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
package com.leon.hfu.timeClient;

import com.leon.hfu.util.commandLine.CommandLineException;
import com.leon.hfu.util.commandLine.CommandLineExceptionType;
import com.leon.hfu.util.commandLine.CommandLineProgram;

import java.io.IOException;
import java.util.Scanner;

/**
 * Contains a programm which is able to display date and time
 * received from a network service.
 *
 * @author		Stefan Hahn
 */
public class TimeServiceClientProgram implements CommandLineProgram {
	/**
	 * Server address.
	 * A Time Service has to be running on TCP port 75.
	 */
	private String address;

	/**
	 * Scanner instance used to read from STDIN.
	 */
	private Scanner scanner;

	/**
	 * Specified command ID from STDIN.
	 */
	private int command;

	/**
	 * Initializes this program.
	 * Creates a Scanner instance on STDIN and promts for the server address.
	 *
	 * @param	args		Command line arguments
	 * @see		CommandLineProgram#initialize(String[])
	 */
	@Override
	public void initialize(String[] args) {
		this.scanner = new Scanner(System.in);

		System.out.print("Please enter the time service server address!\n> ");
		address = scanner.nextLine();
	}

	/**
	 * Executes the main part of this program.
	 * Promts for a command ID and executes the related program part.
	 * This includes getting the current date and time from a server.
	 *
	 * @throws	CommandLineException		Thrown to control programm execution.
	 * @see		CommandLineProgram#execute()
	 */
	@Override
	public void execute() throws CommandLineException {
		System.out.print("Please specify the command!\n 1 - Date\n 2 - Time\n 3 - End\n 4 - Shutdown\n 5 - Exit\n> ");
		command = scanner.nextInt();

		try {
			switch (command) {
				case 1:
					System.out.println("Server response:\n" + TimeServiceClient.dateFromServer(address));
					break;
				case 2:
					System.out.println("Server response:\n" + TimeServiceClient.timeFromServer(address));
					break;
				case 3:
					// TODO: implement
					break;
				case 4:
					// TODO: implement
					break;
				case 5:
					throw new CommandLineException(CommandLineExceptionType.BREAK);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
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
