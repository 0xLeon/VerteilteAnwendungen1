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
package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.EventAdapter;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author		Stefan Hahn
 */
@WebServlet(name = "IndexPageServlet", urlPatterns = { "/Index" })
public class IndexPageServlet extends HttpServlet {
	private static final long serialVersionUID = -3001473253494788701L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed.");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		ArrayList<Event> events;

		try {
			events = EventAdapter.getAllEvents(0);
		}
		catch (EventException e) {
			throw new ServletException(e);
		}

		request.setAttribute("pageTitle", "Ticket Sale");
		request.setAttribute("pageDescription", "");
		request.setAttribute("events", events);

		synchronized (Core.getInstance()) {
			ServletUtil.getRequestDispatcher("/lib/templates/tIndex.jsp", this.getServletContext()).forward(request, response);
		}
	}
}
