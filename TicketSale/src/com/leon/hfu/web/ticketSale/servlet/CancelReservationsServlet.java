package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.util.ServletUtil;

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

		this.event = ServletUtil.getEventFromRequest(request);

		try {
			this.event.cancelReservations(Core.getInstance().getUser(request));
		}
		catch (EventException e) {
			throw new ServletException(e);
		}

		ServletUtil.getRequestDispatcher("/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed!");
	}
}
