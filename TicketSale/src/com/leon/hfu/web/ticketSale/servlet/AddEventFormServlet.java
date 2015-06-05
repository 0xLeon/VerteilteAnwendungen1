package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
import com.leon.hfu.web.ticketSale.EventAdapter;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.hfu.web.ticketSale.exception.URIParameterException;
import com.leon.hfu.web.ticketSale.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author		Stefan Hahn
 */
@WebServlet(name = "AddEventFormServlet", urlPatterns = { "/AddEvent" })
public class AddEventFormServlet extends HttpServlet {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	private String eventName;
	private String description;
	private int seatCount;
	private Date reservationDeadline;
	private Date purchaseDeadline;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Form submit
		Core.getInstance().initSession(request, response);

		try {
			this.getParameters(request);
			EventAdapter.createEvent(this.eventName, this.description, this.reservationDeadline, this.purchaseDeadline, this.seatCount);

			request.setAttribute("pageTitle", "Erfolgreich | Ticket Sale");
			request.setAttribute("pageDescription", "");
			ServletUtil.getRequestDispatcher("/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
		}
		catch (URIParameterException e) {
			request.setAttribute("error", e);
			request.setAttribute("eventName", this.eventName);
			request.setAttribute("eventDescription", this.description);
			request.setAttribute("seatCount", this.seatCount);
			request.setAttribute("reservationDeadline", AddEventFormServlet.dateFormat.format(this.reservationDeadline));
			request.setAttribute("purchaseDeadline", AddEventFormServlet.dateFormat.format(this.purchaseDeadline));

			this.doGet(request, response);
		}
		catch (EventException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Form display
		Core.getInstance().initSession(request, response);

		request.setAttribute("pageTitle", "Event hinzufügen | Ticket Sale");
		request.setAttribute("pageDescription", "");
		request.setAttribute("title", "Event hinzufügen");

		ServletUtil.getRequestDispatcher("/lib/templates/tAddEvent.jsp", this.getServletContext()).forward(request, response);
	}

	private void getParameters(HttpServletRequest request) throws URIParameterException {
		HashMap<String, URIParameterException> formErrors = new HashMap<>(5);

		try {
			this.eventName = ServletUtil.getSingleRequestParameter(request, "eventName");

			if (this.eventName.length() > 45) {
				throw new URIParameterException("Event name is too long.", "eventName");
			}
		}
		catch (URIParameterException e) {
			formErrors.put("eventName", e);
		}

		try {
			this.description = ServletUtil.getSingleRequestParameter(request, "description");
		}
		catch (URIParameterException e) { }

		try {
			this.seatCount = Integer.parseInt(ServletUtil.getSingleRequestParameter(request, "seatCount"), 10);

			if (this.seatCount < 1) {
				throw new URIParameterException("Seat count must be larger than zero.", "seatCount");
			}
		}
		catch (NumberFormatException e) {
			formErrors.put("seatCount", new URIParameterException("Seat count is invalid.", "seatCount"));
		}
		catch (URIParameterException e) {
			formErrors.put("seatCount", e);
		}

		try {
			this.reservationDeadline = AddEventFormServlet.dateFormat.parse(ServletUtil.getSingleRequestParameter(request, "reservationDeadline"));

			// TODO: should check more in detail
			if (this.reservationDeadline.before(new Date())) {
				throw new URIParameterException("Reservation deadline must be in future", "reservationDeadline");
			}
		}
		catch (ParseException e) {
			formErrors.put("reservationDeadline", new URIParameterException("Resveration deadline is invalid", "reservationDeadline"));
		}
		catch (URIParameterException e) {
			formErrors.put("reservationDeadline", e);
		}

		try {
			this.purchaseDeadline = AddEventFormServlet.dateFormat.parse(ServletUtil.getSingleRequestParameter(request, "purchaseDeadline"));

			// TODO: should check more in detail
			if (this.purchaseDeadline.before(new Date())) {
				throw new URIParameterException("Purchase deadline must be in future", "purchaseDeadline");
			}
		}
		catch (ParseException e) {
			formErrors.put("reservationDeadline", new URIParameterException("Purchase deadline is invalid", "purchaseDeadline"));
		}
		catch (URIParameterException e) {
			formErrors.put("reservationDeadline", e);
		}

		if (!this.reservationDeadline.before(this.purchaseDeadline)) {
			formErrors.put("reservationDeadline", new URIParameterException("Reservtion Deadline must be before purchase deadline.", "reservationDeadline"));
		}

		if (formErrors.size() > 0) {
			throw new URIParameterException("Invalid input.", formErrors);
		}
	}
}
