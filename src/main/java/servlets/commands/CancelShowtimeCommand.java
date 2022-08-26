package servlets.commands;

import DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command which execute method updates Showtime status to canceled in DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class CancelShowtimeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("film");
        if (!stringId.isEmpty()) {
            Long id = Long.valueOf(stringId);
            DBManager.getInstance().cancelShowtime(id);
        }
        request.getServletContext().setAttribute("showtimeList", DBManager.getInstance().getPlannedShowtimes());
        request.getServletContext().setAttribute("thisWeekShowtimeList", DBManager.getInstance().getShowtimesForWeek());


        return "manager.jsp";
    }
}
