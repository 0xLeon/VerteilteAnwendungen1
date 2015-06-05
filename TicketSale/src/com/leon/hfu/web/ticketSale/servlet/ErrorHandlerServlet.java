package com.leon.hfu.web.ticketSale.servlet;

import com.leon.hfu.web.ticketSale.Core;
import com.leon.hfu.web.ticketSale.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author		Stefan Hahn
 */
@WebServlet(name = "ErrorHandlerServlet", urlPatterns = { "/Error" })
public class ErrorHandlerServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		String stacktrace = null;

		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		stacktrace = sw.toString();

		request.setAttribute("title", "Fehler");
		request.setAttribute("description", "");
		request.setAttribute("exception", exception);
		request.setAttribute("stacktrace", stacktrace);

		ServletUtil.getRequestDispatcher("/lib/templates/tError.jsp", this.getServletContext()).forward(request, response);
	}
}
