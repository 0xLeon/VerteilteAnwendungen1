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
 * Implements the given F interface, where F#f calculates the
 * faculty of the given parameter
 *
 * @author		Stefan Hahn
 */
public class Faculty implements F {
	/**
	 * Calculates the faculty of the given param.
	 *
	 * @param	x		Parameter
	 * @return			Faculty of fven parameter
	 * @see		F#f
	 */
	@Override
	public int f(int x) {
		int result = 1;

		while (x > 1) {
			result = result * x;
			x--;
		}

		return result;
	}
}
