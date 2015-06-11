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
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.util.ServletUtil;
import com.leon.hfu.web.ticketSale.util.TicketSaleUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author		Stefan Hahn
 */
@WebServlet(name = "CancelReservationsServlet", urlPatterns = { "/CancelReservations" })
public class CancelReservationsServlet extends HttpServlet {
	private Event event;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		if (!Core.getInstance().getUser(request).isInGroup("user.admin")) {
			throw new ServletException("Zugriff nicht gestattet!");
		}

		this.event = TicketSaleUtil.getEventFromRequest(request);

		try {
			this.event.lazyLoadSeats();
			this.event.cancelReservations(Core.getInstance().getUser(request));
		}
		catch (EventException e) {
			throw new ServletException(e);
		}

		request.setAttribute("pageTitle", "Erfolgreich | Ticket Sale");
		request.setAttribute("pageDescription", "");
		ServletUtil.getRequestDispatcher("/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed!");
	}
}
