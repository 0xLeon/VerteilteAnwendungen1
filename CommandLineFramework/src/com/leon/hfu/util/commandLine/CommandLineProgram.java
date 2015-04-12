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
 * Interface for programs which should be executable
 * by this command line framework.
 *
 * @author		Stefan Hahn
 */
public interface CommandLineProgram {
	/**
	 * Initializes the program.
	 * This method should contain all tasks, which have to be
	 * completet, before the program can run ts functions.
	 * These tasks are things like establishing database connections
	 * or creating network sockets.
	 *
	 * @param	args		Command line arguments
	 */
	public void initialize(String[] args);

	/**
	 * Main method which is called in an endless loop.
	 * Ths method contains the function of the program.
	 * The program flow can be controlled with CommandLineExceptions.
	 *
	 * @throws	CommandLineException		Thrown to control program execution.
	 * @see		CommandLineException#getType()
	 */
	public void execute() throws CommandLineException;

	/**
	 * Ths method finishes the program and completes necessary tasks
	 * like closing streams and sockets or database connections.
	 */
	public void finish();
}
