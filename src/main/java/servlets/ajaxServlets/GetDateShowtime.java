package servlets.ajaxServlets;

import DB.DBManager;
import DB.entity.Film;
import DB.entity.Showtime;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.function.Predicate;

@WebServlet(name = "GetDateShowtime", value = "/GetDateShowtime")
public class GetDateShowtime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringDate = request.getParameter("date");
        String language = (String) request.getSession().getAttribute("language");
        if (language == null) {
            language = "en";
        }
        Locale locale = new Locale(language);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
        if (!stringDate.isEmpty()) {
            String type = request.getParameter("type");
            if (Objects.equals(type, "stats")) {
                String year = stringDate.substring(3, 7);
                String month = stringDate.substring(0, 2);
                stringDate = year + "-" + month + "-" + "01";
            }
            System.out.println(stringDate);
            Date date = Date.valueOf(stringDate);
            System.out.println(type);
            System.out.println(date);
            List<Showtime> showtimeList = new ArrayList<>();
            if (!Objects.equals(type, "stats")) {
                showtimeList = DBManager.getInstance().getShowtimeForDate(date);
                showtimeList.removeIf(showtime -> Objects.equals(showtime.getStatus(), "canceled") || Objects.equals(showtime.getStatus(), "finished"));
                showtimeList.sort((o1, o2) -> (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime()));
            }
            if (Objects.equals(type, "stats")) {
                showtimeList = DBManager.getInstance().getShowtimesForMonth(date);
                showtimeList.sort((o1, o2) -> {
                    if (o1.getDate().compareTo(o2.getDate()) == 0) {
                        return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime()) ;
                    }
                    return o1.getDate().compareTo(o2.getDate());
                });
            }

            if (!showtimeList.isEmpty()) {

                List<Film> filmList = (List<Film>) request.getServletContext().getAttribute("filmList");
                StringBuilder stringBuilder = new StringBuilder();
                if (Objects.equals(type, "add")) {
                    stringBuilder.append("<table class=\"table table-striped\">\n");
                    stringBuilder.append("<tr><th>" + resourceBundle.getString("label.name") + "</th><th>" + resourceBundle.getString("label.startTime") + "</th><th>" + resourceBundle.getString("label.endTime") + "</th></tr> ");
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
                            .append("<label for=\"inputFilm\">" + resourceBundle.getString("label.selectShowtime") + "</label>\n")
                            .append("<select class=\"form-control\" oninput=\"sendFilmInfo()\" id=\"inputFilm\" name=\"film\">\n");

                    for (Showtime s : showtimeList) {
                        String filmName = "";
                        for (Film f : filmList) {
                            if (Objects.equals(s.getFilmId(), f.getId())) {
                                filmName = f.getName();
                            }
                        }
                        stringBuilder.append("<option value=\"").append(s.getId()).append("\">").append(filmName).append(" ").append(s.getStartTime()).append("-").append(s.getEndTime()).append("</option>");
                    }
                    stringBuilder.append("</select>\n</div>\n");
                    stringBuilder.append("<input class=\"btn btn-success\"  type=\"submit\" value=\"").append(resourceBundle.getString("label.cancelShowtime")).append("\">\n");
                    stringBuilder.append("</form>\n");
                }
                if (Objects.equals(type, "stats")) {
                    int totalTicketsSold=0;
                    for (Showtime showtime:showtimeList) {
                        for (String string:showtime.getSeats().keySet()) {
                            if(Objects.equals(showtime.getSeats().get(string), "occupied")){
                                totalTicketsSold=totalTicketsSold+1;
                            }
                        }
                    }
                    System.out.println(totalTicketsSold);
                    stringBuilder.append(resourceBundle.getString("label.date")).append(":").append(date.toString(), 0, 7).append(" ")
                            .append(resourceBundle.getString("label.totalShowtimes")).append(showtimeList.toArray().length).append(" ")
                            .append(resourceBundle.getString("label.totalTickets")).append(totalTicketsSold)
                            .append("<table class=\"table table-striped table-bordered\">\n")
                            .append("<tr class=\"info\" >\n").append("<th>").append(resourceBundle.getString("label.date"))
                            .append("</th><th>").append(resourceBundle.getString("label.film"))
                            .append("</th><th>").append(resourceBundle.getString("label.startTime"))
                            .append("</th><th>").append(resourceBundle.getString("label.endTime"))
                            .append("</th><th>").append(resourceBundle.getString("label.seatsTaken"))
                            .append("</th><th>").append(resourceBundle.getString("label.status"))
                            .append("</th>\n").append("</tr>\n");
                    for (Showtime s:showtimeList) {
                        String filmName="";
                        for (Film f:filmList) {
                            if(Objects.equals(s.getFilmId(), f.getId())){
                                filmName=f.getName();
                            }
                        }
                        int ticketsSold=0;
                        for (String key:s.getSeats().keySet()) {
                            if(Objects.equals(s.getSeats().get(key), "occupied")){
                                ticketsSold=ticketsSold+1;
                            }
                        }
                        stringBuilder.append("<tr>\n" + "<td>").append(s.getDate()).append("</td>\n")
                                .append("<td>").append(filmName).append("</td>\n")
                                .append("<td>").append(s.getStartTime()).append("</td>\n")
                                .append("<td>").append(s.getEndTime()).append("</td>\n")
                                .append("<td>").append(ticketsSold).append("/288</td>\n")
                                .append("<td>").append(s.getStatus()).append("</td>\n")
                                .append("</tr>\n");
                    }
                    stringBuilder.append("</table>\n" + "<form role=\"presentation\" action=\"controller\" method=\"get\">\n" + "<input hidden name=\"command\" value=\"printStats\">\n"
                                    + "<input hidden name=\"month\" value=\"")
                            .append(stringDate).append("\">\n").append("<input class=\"btn btn-info\" type=\"submit\" value=\"")
                            .append(resourceBundle.getString("label.printStats"))
                            .append("\">\n").append("</form>");

                }

                String showtimeTable = String.valueOf(stringBuilder);
                System.out.println(showtimeTable);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(showtimeTable);
            }


        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
