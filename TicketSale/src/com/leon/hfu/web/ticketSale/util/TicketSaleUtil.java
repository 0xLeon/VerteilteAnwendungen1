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
package com.leon.hfu.web.ticketSale.util;

import com.leon.hfu.web.ticketSale.Event;
import com.leon.hfu.web.ticketSale.EventAdapter;
import com.leon.hfu.web.ticketSale.exception.EventException;
import com.leon.exception.URIParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author		Stefan Hahn
 */
public final class TicketSaleUtil {
	public static Event getEventFromRequest(HttpServletRequest request) throws ServletException {
		try {
			int eventID = Integer.parseInt(ServletUtil.getSingleRequestParameter(request, "eventID"));

			 return EventAdapter.getEventByID(eventID);
		}
		catch (NumberFormatException | URIParameterException | EventException e) {
			throw new ServletException(e);
		}
	}

	private TicketSaleUtil() { }
}
