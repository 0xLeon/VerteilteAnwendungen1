package com.leon.hfu.timeClient;

import com.leon.hfu.util.commandLine.CommandLineException;
import com.leon.hfu.util.commandLine.CommandLineExceptionType;
import com.leon.hfu.util.commandLine.CommandLineProgram;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Stefan on 23.03.2015.
 */
public class TimeServiceClientProgram implements CommandLineProgram {
	private String address;
	private Scanner scanner;
	private int command;

	@Override
	public void initialize(String[] args) {
		this.scanner = new Scanner(System.in);

		System.out.print("Please enter the time service server address!\n> ");
		address = scanner.nextLine();
	}

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
					break;
				case 4:
					break;
				case 5:
					throw new CommandLineException(CommandLineExceptionType.BREAK);
			}
		}
		catch (IOException e) {

		}
	}

	@Override
	public void finish() {
		this.scanner.close();
	}
}
