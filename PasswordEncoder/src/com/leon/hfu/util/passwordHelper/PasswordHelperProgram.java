package com.leon.hfu.util.passwordHelper;

import ca.defuse.havoc.util.PasswordUtil;
import com.leon.hfu.util.commandLine.CommandLineException;
import com.leon.hfu.util.commandLine.CommandLineExceptionType;
import com.leon.hfu.util.commandLine.CommandLineProgram;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/**
 * Created by Stefan on 05.06.2015.
 */
public class PasswordHelperProgram implements CommandLineProgram {
	private Scanner scanner;
	private int command;

	@Override
	public void initialize(String[] args) {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void execute() throws CommandLineException {
		System.out.print(
			"Please specify the command!\n" +
			" 1 - Hash password\n" +
			" 2 - Check password\n" +
			" 3 - Exit\n" +
			"> "
		);

		try {
			this.command = Integer.parseInt(this.scanner.nextLine(), 10);

			switch (this.command) {
				case 1:
					this.hashPassword();
					break;
				case 2:
					this.checkPassword();
					break;
				case 3:
					throw new CommandLineException(CommandLineExceptionType.BREAK);
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid command!");
		}
	}

	@Override
	public void finish() {
		this.scanner.close();
	}

	private void hashPassword() {
		String password;
		String hashedPassword;

		System.out.print(
			"Plaese enter the password you wish to hash!\n" +
			"> "
		);
		password = this.scanner.nextLine();

		try {
			hashedPassword = PasswordUtil.createHash(password);

			System.out.print(
				"The hash of the given password is\n" +
				"\n" +
				"\t" + hashedPassword + "\n" +
				"\n"
			);
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	private void checkPassword() {
		String password;
		String hashedPassword;
		boolean result;
		String resultMessage;

		System.out.print(
			"Plaese enter the password you wish to check!\n" +
			"> "
		);
		password = this.scanner.nextLine();

		System.out.print(
			"Plaese enter the password hash you wish to check the password against!\n" +
			"> "
		);
		hashedPassword = this.scanner.nextLine();

		try {
			result = PasswordUtil.validatePassword(password, hashedPassword);

			if (result) {
				resultMessage = "Given password matches the given hash.";
			}
			else {
				resultMessage = "Given password doesn't match the given hash.";
			}

			System.out.print(
				"\n" +
				"\t" + resultMessage + "\n" +
				"\n"
			);
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
