package com.epam.listener;

import com.epam.dao.ConnectionPool;
import com.epam.dao.DBManager;
import com.epam.dao.Utils;
import com.epam.service.ShowtimeService;
import com.epam.service.TicketService;
import com.epam.servlet.command.CommandContainer;
import com.epam.service.FilmService;
import com.epam.service.UserService;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener that upon Servlet Context initialization sets it required attributes.
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebListener
public class ContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();

        DBManager dbManager = new DBManager(new ConnectionPool());

        UserService  userService = new UserService(dbManager);
        TicketService ticketService = new TicketService(dbManager);
        ShowtimeService showtimeService = new ShowtimeService(dbManager);
        FilmService filmService = new FilmService(dbManager);

        servletContext.setAttribute("userService",userService);
        servletContext.setAttribute("ticketService",ticketService);
        servletContext.setAttribute("showtimeService",showtimeService);
        servletContext.setAttribute("filmService",filmService);
        servletContext.setAttribute("commandContainer",new CommandContainer(ticketService,userService,showtimeService,filmService));



        servletContext.setAttribute("language", "en");
        /*try {
            servletContext.setAttribute("showtimeList", DBManager.getInstance().getPlannedShowtimes());
        } catch (DBException e) {
            servletContext.getRequestDispatcher("error.jsp");
        }
        try {
            servletContext.setAttribute("thisWeekShowtimeList", DBManager.getInstance().getShowtimesForWeek());
        } catch (DBException e) {
            servletContext.getRequestDispatcher("error.jsp");
        }*/

        servletContext.setAttribute("utils", new Utils());
        servletContext.setAttribute("schedulePage", 0);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
