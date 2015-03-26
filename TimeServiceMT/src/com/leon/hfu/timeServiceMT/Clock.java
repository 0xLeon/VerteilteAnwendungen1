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
package com.leon.hfu.timeServiceMT;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class providing functions to get formatted date and time.
 *
 * @author		Stefan Hahn
 */
public class Clock {
	/**
	 * Time format template.
	 * Format is hh:mm:ss.
	 */
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss");

	/**
	 * Date format template.
	 * Format is dd.mm.yyyy.
	 */
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * Util Date object.
	 */
	private static Date d = new Date();

	/**
	 * Returns a formatted date String according to #dateFormatter template.
	 *
	 * @return			Formatted date String
	 */
	public static String date() {
		d.setTime(System.currentTimeMillis());

		return dateFormatter.format(d);
	}

	/**
	 * Returns a formatted time String according to #timeFormatter template.
	 *
	 * @return			Formatted time String
	 */
	public static String time() {
		d.setTime(System.currentTimeMillis());

		return timeFormatter.format(d);
	}
}
