package servlet.command;

import DB.DBManager;
import DB.entity.Film;
import DB.entity.Showtime;
import DB.exception.DBException;
import service.FilmService;
import service.ShowtimeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

/**
 * Command which execute method validates and inserts Showtime object into DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class AddShowtimeCommand implements Command {
    private final ShowtimeService showtimeService;
    private final FilmService filmService;

    public AddShowtimeCommand(ShowtimeService showtimeService, FilmService filmService) {
        this.showtimeService = showtimeService;
        this.filmService = filmService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address = "manager.jsp";
        String filmString = request.getParameter("film");
        if (filmString.isEmpty()) {
            request.getSession().setAttribute("showtimeError", 3);
            return "addShowtime.jsp";
        }
        Long filmId = Long.valueOf(filmString);
        String stringDate = request.getParameter("date");
        if (stringDate.isEmpty()) {
            request.getSession().setAttribute("showtimeError", 2);
            return "addShowtime.jsp";
        }
        Date date = Date.valueOf(request.getParameter("date"));
        String time = request.getParameter("startTime") + ":00";
        if (time.equals(":00")) {
            request.getSession().setAttribute("showtimeError", 1);
            return "addShowtime.jsp";
        }
        List<Showtime> showtimeList = null;
        showtimeList = showtimeService.getShowtimeForDate(date);
        Time nineAm = Time.valueOf("09:00:00");
        Time twentyTwoPm = Time.valueOf("22:00:00");
        Time midnight = Time.valueOf("24:00:00");

        Time startTime = Time.valueOf(time);
        Film film = filmService.getFilm(filmId);
        Time endTime = new Time(startTime.getTime() + (film.getRunningTime() * 60000));
        boolean flag = false;
        if (startTime.getTime() < nineAm.getTime() || startTime.getTime() > twentyTwoPm.getTime()) {
            flag = true;
        }
        if (endTime.getTime() > midnight.getTime()) {
            flag = true;
        }
        for (Showtime s : showtimeList) {

            if (Objects.equals(s.getStatus(), "planned") && startTime.getTime() >= s.getStartTime().getTime() && startTime.getTime() <= s.getEndTime().getTime()) {
                flag = true;
            }
            if (Objects.equals(s.getStatus(), "planned") && endTime.getTime() >= s.getStartTime().getTime() && endTime.getTime() <= s.getEndTime().getTime()) {
                flag = true;
            }
            if (flag) {
                break;
            }
        }
        if (flag) {
            request.getSession().setAttribute("showtimeError", 1);
            return "addShowtime.jsp";
        }

        Showtime showtime = new Showtime();
        showtime.setFilmId(filmId);
        showtime.setDate(date);
        showtime.setStatus("planned");
        showtime.setStartTime(startTime);
        showtime.setEndTime(endTime);
        showtime.setId(0L);
        showtimeService.createShowtime(showtime);

        /*try {
            request.getServletContext().setAttribute("showtimeList", DBManager.getInstance().getPlannedShowtimes());
        } catch (DBException e) {
            return "error.jsp";
        }
        try {
            request.getServletContext().setAttribute("thisWeekShowtimeList", DBManager.getInstance().getShowtimesForWeek());
        } catch (DBException e) {
            return "error.jsp";
        }
*/

        return address;
    }
}
