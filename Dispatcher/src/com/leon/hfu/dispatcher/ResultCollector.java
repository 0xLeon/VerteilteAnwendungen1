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
 * Thread-safe object wrapper for an integer array.
 * Holds all calculated results.
 *
 * @author		Stefan Hahn
 */
public class ResultCollector {
	/**
	 * Local array for saved values.
	 */
	private int[] results;

	/**
	 * Amount of values to be safed.
	 */
	private int n;

	/**
	 * Counts safed values. Reaches 0 when #n values are saved.
	 */
	private int counter;

	/**
	 * Creates a new ResultCollector object and initialized the local array.
	 *
	 * @param	n		Amount of values to be safed.
	 */
	public ResultCollector(int n) {
		this.n = n;
		this.counter = n;
		this.results = new int[n];
	}

	/**
	 * Saves a given value on position i in a local array.
	 * This method is thread safe.
	 *
	 * @param	i		Position in local array.
	 * @param	result		Value to be safed.
	 */
	public synchronized void pushResult(int i, int result) {
		if ((i > -1) && (i < this.n)) {
			this.results[i] = result;
			this.counter--;

			if (this.counter == 0) {
				this.notify();
			}
		}
	}

	/**
	 * Returns the complete local array.
	 * This method is thread safe.
	 *
	 * @return			The complete local array.
	 */
	public synchronized int[] getResults() {
		return this.results;
	}
}
