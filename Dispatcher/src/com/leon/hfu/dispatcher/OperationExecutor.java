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

/**
 * Wraps the execution of an operation implementing F interface in a new thread.
 *
 * @author		Stefan Hahn
 */
public class OperationExecutor implements Runnable {
	/**
	 * Operation object
	 */
	private F operation = null;

	/**
	 * Parameter for first operation execution.
	 */
	private int x = 0;

	/**
	 * ResultCollector saving operation results.
	 */
	private ResultCollector resultCollector = null;

	/**
	 * Creates a new OperationExecutor object and saves necessary data.
	 *
	 * @param	operation		Operation object
	 * @param	x			Parameter for first operation execution.
	 * @param	resultCollector		ResultCollector saving operation results.
	 */
	public OperationExecutor(F operation, int x, ResultCollector resultCollector) {
		this.operation = operation;
		this.x = x;
		this.resultCollector = resultCollector;
	}

	/**
	 * Executes the operation.
	 * Runs multiple depending on specified number of calculatons per thread.
	 *
	 * @see		Main#CALCS_PER_THREAD
	 * @see		Runnable#run()
	 */
	@Override
	public void run() {
		int result;

		for (int z = this.x; z < Main.N; z += Main.STEP) {
			result = this.operation.f(z);
			this.resultCollector.pushResult(z, result);
		}
	}
}
