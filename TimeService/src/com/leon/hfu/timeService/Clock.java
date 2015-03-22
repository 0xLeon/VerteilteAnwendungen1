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
package com.leon.hfu.timeService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stefan on 21.03.2015.
 */
public class Clock {
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss");
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
	private static Date d = new Date();

	public static String date() {
		d.setTime(System.currentTimeMillis());

		return dateFormatter.format(d);
	}
	public static String time() {
		d.setTime(System.currentTimeMillis());

		return timeFormatter.format(d);
	}
}
