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
import com.leon.hfu.web.ticketSale.exception.URIParameterException;
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
@WebServlet(name = "TicketHandlerServlet", urlPatterns = { "/HandleTicket" })
public class TicketHandlerServlet extends HttpServlet {
	private Event event;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		this.event = ServletUtil.getEventFromRequest(request);

		String action;
		try {
			action = ServletUtil.getSingleRequestParameter(request, "ticketFormAction").toLowerCase();
		}
		catch (URIParameterException e) {
			// TODO: redirect to event page with error message
			throw new ServletException(e);
		}

		switch (action) {
			case "buy":
				this.doBuy(request, response);
				break;
			case "reserve":
				this.doReserve(request, response);
				break;
			case "cancel":
				this.doCancel(request, response);
				break;
			default:
				throw new ServletException("Invalid action.");
		}

		request.setAttribute("pageTitle", "Erfolgreich | Ticket Sale");
		request.setAttribute("pageDescription", "");
		request.setAttribute("redirectURL", "/TicketSale/Event?eventID=" + this.event.getEventID());
		request.setAttribute("redirectText", "Weiter zum Event");
		ServletUtil.getRequestDispatcher("/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed!");
	}

	private void doBuy(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		int[] seatIDs = this.getSeatIDs(request);

		try {
			this.event.buySeats(seatIDs, Core.getInstance().getUser(request));
		}
		catch (EventException e) {
			throw new ServletException(e);
		}
	}

	private void doReserve(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		int[] seatIDs = this.getSeatIDs(request);

		try {
			this.event.reserveSeats(seatIDs, Core.getInstance().getUser(request));
		}
		catch (EventException e) {
			throw new ServletException(e);
		}
	}

	private void doCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		int[] seatIDs = this.getSeatIDs(request);

		try {
			this.event.cancelSeats(seatIDs, Core.getInstance().getUser(request));
		}
		catch (EventException e) {
			throw new ServletException(e);
		}
	}

	private int[] getSeatIDs(HttpServletRequest request) throws ServletException {
		try {
			ArrayList<String> seatIDsRaw = ServletUtil.getRequestParameter(request, "seatIDs[]");
			int[] seatIDs = new int[seatIDsRaw.size()];
			int i = 0;

			try {
				for (String seatID : seatIDsRaw) {
					seatIDs[i] = Integer.parseInt(seatID, 10);
					i++;
				}
			}
			catch (NumberFormatException e) {
				throw new ServletException("Invalid seat ID", e);
			}

			return seatIDs;
		}
		catch (NullPointerException e) {
			throw new ServletException(e);
		}
		catch (URIParameterException e) {
			// TODO: redirect to event page with error message
			throw new ServletException(e);
		}
	}
}
