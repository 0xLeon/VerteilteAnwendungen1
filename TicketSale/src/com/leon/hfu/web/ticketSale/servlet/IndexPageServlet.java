package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
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
@WebServlet(name = "IndexPageServlet", urlPatterns = { "/Index" })
public class IndexPageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("Method not allowed.");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		request.setAttribute("title", "Ticket Sale");
		request.setAttribute("description", "");
		request.setAttribute("user", Core.getInstance().getUser(request));

		synchronized (Core.getInstance()) {
			ServletUtil.getRequestDispatcher("/lib/templates/tIndex.jsp", this.getServletContext()).forward(request, response);
		}
	}
}
