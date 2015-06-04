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
@WebServlet(name = "EventPageServlet", urlPatterns = { "/Event" })
public class EventPageServlet extends HttpServlet {
	private Event event;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed.");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		this.event = ServletUtil.getEventFromRequest(request);

		request.setAttribute("title", "Ticket Sale");
		request.setAttribute("description", "");
		request.setAttribute("user", Core.getInstance().getUser(request));
		request.setAttribute("event", this.event);

		synchronized (Core.getInstance()) {
			ServletUtil.getRequestDispatcher("/lib/templates/tEvent.jsp", this.getServletContext()).forward(request, response);
		}
	}
}
