package com.epam.servlet.ajax;

import com.epam.dao.Utils;
import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.User;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import com.epam.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

/**
 * AJAX Servlet that builds ticket preview page fragment for showtime page.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "TicketPreview", value = "/TicketPreview")
public class TicketPreview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String showtimeId = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("loggedUser");
        List<String> seatsChecked = new ArrayList<>();
        Set<String> keySet = new Utils().fillSeatMap().keySet();
        for (String s : keySet) {
            if (Objects.equals(request.getParameter(s), "true")) {
                seatsChecked.add(s);
            }
        }
        ShowtimeService showtimeService = (ShowtimeService) request.getServletContext().getAttribute("showtimeService");
        FilmService filmService = (FilmService) request.getServletContext().getAttribute("filmService");
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");


        Showtime showtime = null;
        showtime = showtimeService.getShowtime(Long.valueOf(showtimeId));
        List<Film> filmList = filmService.getAllFilms();
        String filmName = "";
        for (Film f : filmList) {
            if (Objects.equals(showtime.getFilmId(), f.getId())) {
                filmName = f.getName();
            }
        }
        seatsChecked.removeIf(Objects::isNull);
        if (!seatsChecked.isEmpty()) {
            String language = (String) request.getSession().getAttribute("language");
            if (language == null) {
                language = "en";
            }
            Locale locale = new Locale(language);
            ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
            StringBuilder stringBuilder = new StringBuilder();
            Long totalCost = seatsChecked.toArray().length * 75L;
            stringBuilder.append("<div style=\"width: 48.5%;\" class=\"well\"><h5>")
                    .append(resourceBundle.getString("label.totalCost"))
                    .append(totalCost)
                    .append(resourceBundle.getString("label.hrn"))
                    .append("</h5>");

            Long userBalance = userService.getUserBalance(user.getId());

            stringBuilder.append("<h5>").append(resourceBundle.getString("label.yourBalance")).append(userBalance).append(resourceBundle.getString("label.hrn")).append("</h5>");
            if(totalCost> userService.getUserBalance(user.getId())){
                stringBuilder.append("<h5>").append(resourceBundle.getString("label.insufficientFunds")).append("</h5>");
            }
            /*stringBuilder.append("<script>" +
                    "\n" +
                    "    $(document).ready(function(){\n" +
                    "        $('[data-toggle=\"popover\"]').popover();\n" +
                    "    });\n").append("</script>");*/
            stringBuilder.append("<div style=\"width: 45%; class=\"container\">");
            stringBuilder.append("<input class=\"btn btn-success\" ");
            if(totalCost> userBalance){
                /* stringBuilder.append(" data-toggle=\"popover\" data-placement=\"right\" data-trigger=\"hover\" data-content=\"")
                        .append(resourceBundle.getString("label.insufficientFunds"))
                        .append("\" data-original-title=\"\" title=\"\" ");*/
                stringBuilder.append(" disabled ");
            }
            stringBuilder.append(" type=\"submit\" value=\"")
                    .append(resourceBundle.getString("label.purchaseTickets"))
                    .append("\">");

            stringBuilder.append("</div>")
                    .append("</div>\n");
            stringBuilder.append("<div style=\"width: 50%; position: absolute; left: 0%\" class=\"container\">\n");
            for (String s : seatsChecked) {
                stringBuilder.append("<div class=\"panel panel-info\">\n"
                                + "<div class=\"panel-heading\">\n"
                                + "<h3 class=\"panel-title\" >").append(filmName)
                        .append("</h3>\n")
                        .append("</div>\n")
                        .append("<div class=\"panel-body\">\n")
                        .append(resourceBundle.getString("label.date"))
                        .append(":")
                        .append(showtime.getDate())
                        .append(" ")
                        .append(resourceBundle.getString("label.startEndTime"))
                        .append(":")
                        .append(showtime.getStartTime())
                        .append("-")
                        .append(showtime.getEndTime())
                        .append(" ")
                        .append(resourceBundle.getString("label.seat"))
                        .append(":")
                        .append(s)
                        .append("</div>\n")
                        .append("</div>\n");
            }
            stringBuilder.append("</div>");

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(stringBuilder));
        }
    }


}
