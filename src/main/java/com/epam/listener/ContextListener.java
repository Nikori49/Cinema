package com.epam.listener;

import com.epam.annotation.processor.ServiceInjectionProcessor;
import com.epam.dao.*;
import com.epam.servlet.command.CommandContainer;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Locale;

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

        ConnectionPool connectionPool = new ConnectionPool();

        ServiceInjectionProcessor serviceInjectionProcessor = new ServiceInjectionProcessor(new FilmDAO(connectionPool),new TicketDAO(connectionPool),new ShowtimeDAO(connectionPool),new UserDAO(connectionPool),"com.epam.service");


        serviceInjectionProcessor.getMap().forEach((key,value)->{
            String s = key.getSimpleName().substring(0,1).toLowerCase(Locale.ROOT);
            servletContext.setAttribute(s+key.getSimpleName().substring(1),value);
        });



        servletContext.setAttribute("commandContainer",new CommandContainer(serviceInjectionProcessor));




        servletContext.setAttribute("language", "en");

        servletContext.setAttribute("utils", new Utils());
        servletContext.setAttribute("schedulePage", 0);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
