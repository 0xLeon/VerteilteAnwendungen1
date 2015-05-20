package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
import com.leon.hfu.web.ticketSale.EventException;
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
@WebServlet(name = "TicketHandlerServlet", urlPatterns = { "/Buy", "/Reserve" })
public class TicketHandlerServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		String action = ServletUtil.getSingleRequestParameter(request, "action").toLowerCase();

		switch (action) {
			case "buy":
				this.doBuy(request, response);
				break;
			case "reserve":
				this.doReserve(request, response);
				break;
			default:
				throw new ServletException("Invalid action.");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed!");
	}

	private void doBuy(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		int[] seatIDs = this.getSeatIDs(request);

		try {
			Core.getInstance().getEvent().buySeats(seatIDs, Core.getInstance().getUser(request));
			ServletUtil.getRequestDispatcher("/TicketSale/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
		}
		catch (EventException | IOException e) {
			throw new ServletException(e);
		}
	}

	private void doReserve(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		int[] seatIDs = this.getSeatIDs(request);

		try {
			Core.getInstance().getEvent().reserveSeats(seatIDs, Core.getInstance().getUser(request));
			ServletUtil.getRequestDispatcher("/TicketSale/lib/templates/tSuccess.jsp", this.getServletContext()).forward(request, response);
		}
		catch (EventException | IOException e) {
			throw new ServletException(e);
		}
	}

	private int[] getSeatIDs(HttpServletRequest request) throws ServletException {
		ArrayList<String> seatIDsRaw = ServletUtil.getRequestParameter(request, "seatIDs[]");
		int[] seatIDs = new int[seatIDsRaw.size()];
		int i = 0;

		try {
			for (String seatID : seatIDsRaw) {
				seatIDs[i] = Integer.parseInt(seatID, 10);
			}
		}
		catch (NumberFormatException e) {
			throw new ServletException("Invalid seat ID", e);
		}

		return seatIDs;
	}
}
