package com.leon.hfu.web.ticketSale;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author		Stefan Hahn
 */
@WebListener
public class TicketSaleStartupListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("core", Core.getInstance());
		sce.getServletContext().setAttribute("event", Core.getInstance().getEvent());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
}
