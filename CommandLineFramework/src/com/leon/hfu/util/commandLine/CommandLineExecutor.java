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
package com.leon.hfu.util.commandLine;

/**
 * Handles execution of instances. See the #run() method
 * for more information.
 *
 * @author		Stefan Hahn
 */
public final class CommandLineExecutor {
	/**
	 * Runs a given CommandLineProgram instance. This function contains
	 * the needed endless loop calling CommandLineProgram#execute() during
	 * every run.
	 *
	 * @param	program		CommandLineProgram instance object
	 * @param	args		Command line arguments
	 */
	public static final void run(CommandLineProgram program, String[] args) {
		try {
			program.initialize(args);

			mainLoop:
			while (true) {
				try {
					program.execute();
					sleep(1000);
				}
				catch (CommandLineException e) {
					switch (e.getType()) {
						case CONTINUE:
							sleep(1000);
							continue mainLoop;
						case BREAK:
							break mainLoop;
						case ERROR:
							throw e.getCause();
					}
				}
			}
		}
		catch (Throwable e) {
			if (e instanceof RuntimeException) {
				throw ((RuntimeException) e);
			}

			e.printStackTrace();
			System.exit(1);
		}
		finally {
			program.finish();
		}
	}

	/**
	 * Sleeps given milli seconds and catches all InterruptedExceptions.
	 *
	 * @param	millis		Time to wait in milli seconds.
	 */
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) { }
	}
}
