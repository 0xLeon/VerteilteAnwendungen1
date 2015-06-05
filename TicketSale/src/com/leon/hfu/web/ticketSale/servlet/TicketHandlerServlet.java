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
 * Created by Stefan on 08.05.2015.
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

		request.setAttribute("title", "Erfolgreich | Ticket Sale");
		request.setAttribute("description", "");
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
