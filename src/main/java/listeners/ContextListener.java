package listeners;

import DB.DBManager;
import DB.Utils;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("filmList", DBManager.getInstance().getAllFilms());
        servletContext.setAttribute("language", "en");
        servletContext.setAttribute("showtimeList", DBManager.getInstance().getPlannedShowtimes());
        servletContext.setAttribute("thisWeekShowtimeList", DBManager.getInstance().getShowtimesForWeek());
        servletContext.setAttribute("weekDates", Utils.getWeekDates());
        servletContext.setAttribute("weekDays", Utils.getWeekDays());
        servletContext.setAttribute("schedulePage", 0);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
