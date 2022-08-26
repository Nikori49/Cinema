package servlets.ajaxServlets;

import DB.DBManager;
import DB.Utils;
import DB.entity.Film;
import DB.entity.Showtime;

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
        List<String> seatsChecked = new ArrayList<>();
        Set<String> keySet = Utils.fillSeatMap().keySet();
        for (String s : keySet) {
            if (Objects.equals(request.getParameter(s), "true")) {
                seatsChecked.add(s);
            }
        }

        Showtime showtime = DBManager.getInstance().getShowTime(Long.valueOf(showtimeId));
        List<Film> filmList = (List<Film>) request.getServletContext().getAttribute("filmList");
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
            stringBuilder.append("<div style=\"width: 50%;\" class=\"well\"><h5>")
                    .append(resourceBundle.getString("label.totalCost"))
                    .append(seatsChecked.toArray().length * 75)
                    .append(resourceBundle.getString("label.hrn"))
                    .append("</h5></div>\n");
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
            stringBuilder.append("<div style=\"width: 50%; position: absolute; left: -18%\" class=\"container\">");
            stringBuilder.append("<input class=\"btn btn-success\" type=\"submit\" value=\"")
                    .append(resourceBundle.getString("label.purchaseTickets"))
                    .append("\">");
            stringBuilder.append("</div>");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(stringBuilder));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
