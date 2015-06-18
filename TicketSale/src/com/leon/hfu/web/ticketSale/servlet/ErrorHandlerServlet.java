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
	private static final long serialVersionUID = -6819538599669667627L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Core.getInstance().initSession(request, response);

		Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		String stacktrace = null;

		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		stacktrace = sw.toString();

		request.setAttribute("pageTitle", "Fehler");
		request.setAttribute("pageDescription", "");
		request.setAttribute("exception", exception);
		request.setAttribute("stacktrace", stacktrace);

		ServletUtil.getRequestDispatcher("/lib/templates/tError.jsp", this.getServletContext()).forward(request, response);
	}
}
