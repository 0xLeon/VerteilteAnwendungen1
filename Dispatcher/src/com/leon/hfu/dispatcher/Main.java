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
package com.leon.hfu.dispatcher;

import java.util.Arrays;

/**
 * Main class taking care of program execution.
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 * Amount of operation calculation runs.
	 */
	public static final int N = 15;

	/**
	 * Amount of operation calculation runs each thread executes.
	 */
	public static final int CALCS_PER_THREAD = 3;

	/**
	 * Resulting step width, result of runs and calculations per thread.
	 *
	 * @see		#N
	 * @see		#CALCS_PER_THREAD
	 */
	public static final int STEP = 5;

	/**
	 * Entrance point for the program execution.
	 * Creates operation object and calls #execute(F, int) method.
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		F operation = new Faculty();
		int[] results = execute(operation, Main.N);

		System.out.println(Arrays.toString(results));
	}

	/**
	 * Creates threads for calculation execution and waits for all results.
	 *
	 * @param	f		Operation object
	 * @param	n		Amount of operation calculation runs
	 * @return			All operation results
	 */
	public static int[] execute(F f, int n) {
		ResultCollector resultCollector = new ResultCollector(n);

		for (int x = 0; x < Main.STEP; x++) {
			(new Thread(new OperationExecutor(f, x, resultCollector))).start();
		}

		synchronized (resultCollector) {
			try {
				resultCollector.wait();
			}
			catch (InterruptedException e) { }
		}

		return resultCollector.getResults();
	}
}
