package com.leon.hfu.web.ticketSale;

/**
 * Created by Stefan on 01.05.2015.
 */
public final class Core {
	private static Core instance = null;

	private Core() {

	}

	public static Core getInstance() {
		if (Core.instance == null) {
			Core.instance = new Core();
		}

		return Core.instance;
	}
}
