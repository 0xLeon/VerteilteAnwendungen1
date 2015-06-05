package com.leon.hfu.util.passwordHelper;

import com.leon.hfu.util.commandLine.CommandLineExecutor;
import com.leon.hfu.util.commandLine.CommandLineProgram;

public class Main {
	private static CommandLineProgram program;

	public static void main(String[] args) {
		Main.program = new PasswordHelperProgram();

		CommandLineExecutor.run(Main.program, args);
	}
}
