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

		System.out.println("Events Count: " + events.size());

		request.setAttribute("title", "Ticket Sale");
		request.setAttribute("description", "");
		request.setAttribute("user", Core.getInstance().getUser(request));
		request.setAttribute("events", events);

		synchronized (Core.getInstance()) {
			ServletUtil.getRequestDispatcher("/lib/templates/tIndex.jsp", this.getServletContext()).forward(request, response);
		}
	}
}
