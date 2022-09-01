package com.epam.listener;

import com.epam.annotation.processor.ServiceInjectionProcessor;
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
import java.util.Locale;
import java.util.Map;

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

        ServiceInjectionProcessor serviceInjectionProcessor = new ServiceInjectionProcessor(dbManager);
        serviceInjectionProcessor.yakiysMethod("com.epam.service");

        serviceInjectionProcessor.getMap().forEach((key,value)->{
            String s = key.getSimpleName().substring(0,1).toLowerCase(Locale.ROOT);
            servletContext.setAttribute(s+key.getSimpleName().substring(1),value);
        });



        servletContext.setAttribute("commandContainer",new CommandContainer(serviceInjectionProcessor));




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
