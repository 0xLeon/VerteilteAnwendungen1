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
package com.leon.hfu.eggTimer;

/**
 * Timer class which displays a message after a given amount of seconds.
 * The sleeping is non-blocking as the sleeps runs in its own thread.
 *
 * @author		Stefan Hahn
 */
public class Timer implements Runnable {
	/**
	 * Message displayed when time is up.
	 */
	private String message = "";

	/**
	 * Time in seconds to wait.
	 */
	private int time = 0;

	/**
	 * Creates a new Timer object and initializes local fields.
	 *
	 * @param		message		Message displayed when time is up.
	 * @param		time		Time in seconds to wait.
	 */
	public Timer(String message, int time) {
		this.setMessage(message);
		this.setTime(time);
	}

	/**
	 * Sets the message displayed when time is up.
	 *
	 * @param		message		Message displayed when time is up.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the time in seconds to wait.
	 *
	 * @param		time		Time in seconds to wait.
	 */
	public void setTime(int time) {
		if (time > 0) {
			this.time = time;
		}
	}

	/**
	 * Calls Timer#sleep(int) to wait and displays the message.
	 * Print seconds left every second befor displaying the message.
	 *
	 * @see			Runnable#run()
	 */
	@Override
	public void run() {
		for (int i = this.time; i > 0; i--) {
			System.out.print(Integer.toString(i) + " ");
			Timer.sleep(1);
		}

		System.out.println("\nNachricht: " + this.message);
	}

	/**
	 * Sleeps given seconds and catches all InterruptedExceptions.
	 *
	 * @param		m			Time in seconds to wait.
	 */
	public static void sleep(int m) {
		try {
			Thread.sleep(1000 * m);
		}
		catch (InterruptedException e) { }
	}
}
