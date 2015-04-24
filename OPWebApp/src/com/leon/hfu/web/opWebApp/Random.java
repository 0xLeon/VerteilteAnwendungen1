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
package com.leon.hfu.web.opWebApp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which takes two integer parameters from HTTP request
 * and calculates a random integer between those numbers.
 *
 * @author		Stefan Hahn
 */
@WebServlet(name = "Rand", description = "Acceppts two int parameters and displays a random int between these two ints.", urlPatterns = { "/Rand" })
public class Random extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Reacts on every HTTP request and calculates a random integer between
	 * two given integer parameters.
	 *
	 * @see HttpServlet#service(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lowerString = request.getParameter("lower");
		String upperString = request.getParameter("upper");
		int lower;
		int upper;
		
		if ((lowerString == null) || (upperString == null)) {
			response.sendRedirect("/OPWebApp/invalid-values.html");
			return;
		}
		
		try {
			lower = Integer.parseInt(lowerString, 10);
			upper = Integer.parseInt(upperString, 10);
		}
		catch (NumberFormatException e) {
			response.sendRedirect("/OPWebApp/invalid-values.html");
			return;
		}
		
		if (lower >= upper) {
			response.sendRedirect("/OPWebApp/invalid-values.html");
			return;
		}
		
		response.getWriter().println(lower + Math.round((Math.random() * (upper - lower))));
	}
}
