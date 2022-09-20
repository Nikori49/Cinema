package com.epam.servlet.ajax;

import com.epam.service.ShowtimeService;
import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.service.FilmService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

/**
 * AJAX Servlet that depending on "type" request parameter builds page fragment.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "GetDateShowtime", value = "/GetDateShowtime")
public class GetDateShowtime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringDate = request.getParameter("date");
        if (!stringDate.isEmpty()) {
            String language = (String) request.getSession().getAttribute("language");
            if (language == null) {
                language = "en";
            }
            Locale locale = new Locale(language);
            ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
            String type = request.getParameter("type");
            ShowtimeService showtimeService = (ShowtimeService) request.getServletContext().getAttribute("showtimeService");
            FilmService filmService = (FilmService) request.getServletContext().getAttribute("filmService");
            if (Objects.equals(type, "stats")) {
                String year = stringDate.substring(3, 7);
                String month = stringDate.substring(0, 2);
                stringDate = year + "-" + month + "-" + "01";
            }

            Date date = Date.valueOf(stringDate);

            List<Showtime> showtimeList = new ArrayList<>();
            if (!Objects.equals(type, "stats")) {
                showtimeList = showtimeService.getShowtimeForDate(date);
                showtimeList.removeIf(showtime -> Objects.equals(showtime.getStatus(), "canceled") || Objects.equals(showtime.getStatus(), "finished"));
                showtimeList.sort((o1, o2) -> {
                    if (o1.getDate().compareTo(o2.getDate()) == 0) {
                        return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime());
                    }
                    return o1.getDate().compareTo(o2.getDate());
                });
            }
            if (Objects.equals(type, "stats")) {
                showtimeList = showtimeService.getShowtimeForMonth(date);
                showtimeList.sort((o1, o2) -> {
                    if (o1.getDate().compareTo(o2.getDate()) == 0) {
                        return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime());
                    }
                    return o1.getDate().compareTo(o2.getDate());
                });
            }

            if (!showtimeList.isEmpty()) {

                List<Film> filmList = filmService.getAllFilms();
                StringBuilder stringBuilder = new StringBuilder();
                if (Objects.equals(type, "add")) {
                    stringBuilder.append("<table class=\"table table-striped\">\n");
                    stringBuilder.append("<tr><th>")
                            .append(resourceBundle.getString("label.name"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.startTime"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.endTime"))
                            .append("</th></tr> ");
                    for (Showtime s : showtimeList) {
                        String filmName = "";
                        for (Film f : filmList) {
                            if (Objects.equals(s.getFilmId(), f.getId())) {
                                filmName = f.getName();
                            }
                        }
                        stringBuilder.append("<tr><td>").append(filmName).append("</td><td>").append(s.getStartTime()).append("</td><td>").append(s.getEndTime()).append("</td></tr>\n");
                    }
                    stringBuilder.append("</table>");
                }
                if (Objects.equals(type, "cancel")) {
                    stringBuilder.append("<form  role=\"form\" name=\"showtimeForm\" action=\"controller\" method=\"post\">\n")
                            .append("<input type=\"hidden\" name=\"command\" value=\"cancelShowtime\">\n")
                            .append("<div class=\"form-group\">\n")
                            .append("<label for=\"inputFilm\">")
                            .append(resourceBundle.getString("label.selectShowtime"))
                            .append("</label>\n")
                            .append("<select class=\"form-control\" oninput=\"sendFilmInfo()\" id=\"inputFilm\" name=\"film\">\n");

                    for (Showtime s : showtimeList) {
                        String filmName = "";
                        for (Film f : filmList) {
                            if (Objects.equals(s.getFilmId(), f.getId())) {
                                filmName = f.getName();
                            }
                        }
                        stringBuilder.append("<option value=\"")
                                .append(s.getId())
                                .append("\">")
                                .append(filmName)
                                .append(" ")
                                .append(s.getStartTime())
                                .append("-")
                                .append(s.getEndTime())
                                .append("</option>");
                    }
                    stringBuilder.append("</select>\n</div>\n");
                    stringBuilder.append("<input class=\"btn btn-success\"  type=\"submit\" value=\"")
                            .append(resourceBundle.getString("label.cancelShowtime"))
                            .append("\">\n");
                    stringBuilder.append("</form>\n");
                }
                if (Objects.equals(type, "stats")) {
                    int totalTicketsSold = 0;
                    for (Showtime showtime : showtimeList) {
                        for (String string : showtime.getSeats().keySet()) {
                            if (Objects.equals(showtime.getSeats().get(string), "occupied")) {
                                totalTicketsSold = totalTicketsSold + 1;
                            }
                        }
                    }
                    stringBuilder.append(resourceBundle.getString("label.date"))
                            .append(":")
                            .append(date.toString(), 0, 7)
                            .append(" ")
                            .append(resourceBundle.getString("label.totalShowtimes"))
                            .append(showtimeList.toArray().length)
                            .append(" ")
                            .append(resourceBundle.getString("label.totalTickets"))
                            .append(totalTicketsSold)
                            .append("<table class=\"table table-striped table-bordered\">\n")
                            .append("<tr class=\"info\" >\n")
                            .append("<th>").append(resourceBundle.getString("label.date"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.film"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.startTime"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.endTime"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.seatsTaken"))
                            .append("</th><th>")
                            .append(resourceBundle.getString("label.status"))
                            .append("</th>\n")
                            .append("</tr>\n");
                    int intPage = 0;
                    String page = request.getParameter("page");
                    if (page != null) {
                        intPage = Integer.parseInt(page);
                    }
                    int length = showtimeList.size();
                    int pageCount = (int) Math.ceil(length / new BigDecimal(7).doubleValue());
                    if (length > 7) {
                        if(intPage+1==pageCount){
                            showtimeList = showtimeList.subList(intPage * 7, length);
                        }else {
                            showtimeList = showtimeList.subList(intPage * 7, intPage * 7 + 7);
                        }

                    }
                    for (Showtime s : showtimeList) {
                        String filmName = "";
                        for (Film f : filmList) {
                            if (Objects.equals(s.getFilmId(), f.getId())) {
                                filmName = f.getName();
                            }
                        }
                        int ticketsSold = 0;
                        for (String key : s.getSeats().keySet()) {
                            if (Objects.equals(s.getSeats().get(key), "occupied")) {
                                ticketsSold = ticketsSold + 1;
                            }
                        }
                        stringBuilder.append("<tr>\n" + "<td>")
                                .append(s.getDate())
                                .append("</td>\n")
                                .append("<td>")
                                .append(filmName)
                                .append("</td>\n")
                                .append("<td>")
                                .append(s.getStartTime())
                                .append("</td>\n")
                                .append("<td>")
                                .append(s.getEndTime())
                                .append("</td>\n")
                                .append("<td>")
                                .append(ticketsSold)
                                .append("/288</td>\n")
                                .append("<td>")
                                .append(s.getStatus())
                                .append("</td>\n")
                                .append("</tr>\n");
                    }
                    if (pageCount > 1) {
                        stringBuilder.append("<div class=\"container\">  \n" +
                                "  <ul class=\"pagination\">  \n");
                        for (int i = 0; i < pageCount; i++) {
                            stringBuilder.append("<li ");
                            if(i==intPage){
                                stringBuilder.append("  class=\"active\" ");
                            }
                            stringBuilder.append(" ><a href=\"#\" onclick=\"sendDateInfoForStats(" + i + ")\">" + (i + 1) + "</a></li>");
                        }
                        stringBuilder.append("  </ul>  \n" +
                                "</div>  ");
                    }
                    stringBuilder.append("</table>\n" + "<form role=\"presentation\" action=\"controller\" method=\"get\">\n"
                                    + "<input hidden name=\"command\" value=\"printStats\">\n"
                                    + "<input hidden name=\"month\" value=\"")
                            .append(stringDate)
                            .append("\">\n")
                            .append("<input class=\"btn btn-info\" type=\"submit\" value=\"")
                            .append(resourceBundle.getString("label.printStats"))
                            .append("\">\n")
                            .append("</form>");

                }

                String showtimeTable = String.valueOf(stringBuilder);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(showtimeTable);
            }


        }

    }


}
