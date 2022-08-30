package servlet;

import DB.DBManager;
import DB.entity.Film;
import DB.entity.Showtime;
import DB.entity.User;
import DB.exception.DBException;
import service.FilmService;
import service.ShowtimeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Servlet that constructs film page based on provided film id.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "FilmPage", value = "/FilmPage")
public class FilmPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String filmId = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("loggedUser");
        String contextPath = request.getContextPath();
        ShowtimeService showtimeService = (ShowtimeService) request.getServletContext().getAttribute("showtimeService");
        FilmService filmService = (FilmService) request.getServletContext().getAttribute("filmService");
        String userRole = "";
        if (user == null) {
            userRole = "guest";
        }
        if (user != null) {
            userRole = user.getRole();
        }
        String language = (String) request.getSession().getAttribute("language");
        if (language == null) {
            language = "en";
        }
        Locale locale = new Locale(language);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
        if (filmId.isEmpty() || !filmId.matches("\\d+")) {
            response.sendRedirect("films.jsp");
            return;
        }
        Film film;
        film = filmService.getFilm(Long.valueOf(filmId));

        if (film.getId() == null) {
            response.sendRedirect("films.jsp");
            return;
        }

        out.write("<html >\n" +
                "<head>\n" +
                "    <title>" + film.getName() + "</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n");
        out.write("<script>" +
                "function changeLanguage(lang) {\n" +
                "            let request\n" +
                "            const url = \"" +
                request.getContextPath() +
                "/ChangeLanguage?lang=\" + lang;\n" +
                "\n" +
                "            if(window.XMLHttpRequest){\n" +
                "                request=new XMLHttpRequest();\n" +
                "            }\n" +
                "            else if(window.ActiveXObject){\n" +
                "                request=new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "            }\n" +
                "\n" +
                "            try\n" +
                "            {\n" +
                "                request.open(\"GET\",url,false);\n" +
                "                request.send();\n" +
                "            }\n" +
                "            catch(e)\n" +
                "            {\n" +
                "                alert(\"Unable to connect to server\");\n" +
                "            }\n" +
                "            document.location.reload();\n" +
                "        }" +
                "</script>" +
                "</head>\n" + "<body>");

        out.write("<nav class=\"navbar navbar-inverse\">\n" +
                "    <div class=\"container-fluid\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "            <a class=\"navbar-brand\" href=\"index.jsp\">Cinema</a>\n" +
                "        </div>\n" +
                "        <ul class=\"nav navbar-nav\">\n" +
                "            <li class=\"active\"><a href=\"films.jsp\">" + resourceBundle.getString("label.films") + "</a></li>\n" +
                "            <li><a href=\"schedule.jsp\">" + resourceBundle.getString("label.schedule") + "</a></li>\n");
        if (Objects.equals(userRole, "manager")) {
            out.write("<li><a href=\"manager.jsp\">" + resourceBundle.getString("label.managerWorkplace") + "</a></li>\n");
        }
        out.write("</ul>\n");
        out.write("<ul class=\"nav navbar-nav navbar-right\">\n");
        out.write("<li ");
        if (language.equals("en")) {
            out.write("class=\"active\" ");
        }
        out.write("> <a href=\"#\" onclick=\"changeLanguage('en')\" >En</a></li>\n" +
                "<li ");
        if (language.equals("uk")) {
            out.write("class=\"active\" ");
        }
        out.write("> <a href=\"#\" onclick=\"changeLanguage('uk')\" >Укр</a></li>");
        if (Objects.equals(userRole, "guest")) {
            out.write("<li><a href=\"register.jsp\"><span class=\"glyphicon glyphicon-user\"></span>" + resourceBundle.getString("label.signUp") + "</a></li>\n" +
                    "<li><a href=\"login.jsp\"><span class=\"glyphicon glyphicon-log-in\"></span>" + resourceBundle.getString("label.logIn") + "</a></li>\n");
        }
        if (!Objects.equals(userRole, "guest")) {
            out.write("<li>\n" +
                    "                    <a href=\"" + request.getContextPath() + "/LogOut\" >\n" +
                    "                        <span class=\"glyphicon glyphicon glyphicon-log-out\"></span>"
                    + resourceBundle.getString("label.logOut") + "\n" +
                    "                    </a>\n" +
                    "                </li>");
        }
        if (Objects.equals(userRole, "client")) {
            out.write("                <li><a href=\"client.jsp\"><span class=\"glyphicon glyphicon-user\"></span>"
                    + resourceBundle.getString("label.profile") + "</a></li>\n");
        }
        if (Objects.equals(userRole, "manager")) {
            out.write("                <li><a href=\"manager.jsp\"><span class=\" glyphicon glyphicon-briefcase\"></span>"
                    + resourceBundle.getString("label.managerWorkplace") + "</a></li>\n");
        }
        out.write("</ul>\n" +
                "</div>\n" +
                "</nav>");
        out.write("<div style=\"width: 720px; height: 405px; position: absolute; top: 10%; right: 10%\"  class=\"container\">\n" +
                "    <h2>" + resourceBundle.getString("label.trailer") + "</h2>\n" +
                "    <div class=\"embed-responsive embed-responsive-16by9\">\n" +
                "        <iframe  class=\"embed-responsive\" src=\"https://www.youtube.com/embed/" + film.getYoutubeTrailerId() + "\"></iframe>\n" +
                "    </div>\n" +
                "</div>");
        out.write("<div class=\"container\">\n" +
                "    <img width=\"400\" height=\"600\" class=\"img-responsive\" alt=\"Missing poster\" src=\"" + film.getPosterImgPath() + "\">\n" +
                "    <h3>" + film.getName() + "</h3>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>" + resourceBundle.getString("label.director") + "</h5>\n" +
                "        <div class=\"well\">" + film.getDirector() + "</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>" + resourceBundle.getString("label.genre") + "</h5>\n" +
                "        <div class=\"well\">" + film.getGenre() + "</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>" + resourceBundle.getString("label.runtime") + "</h5>\n" +
                "        <div class=\"well\">" + film.getRunningTime() + "</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>" + resourceBundle.getString("label.description") + "</h5>\n" +
                "        <div class=\"well\">" + film.getDescription() + "</div>\n" +
                "    </div>\n" +
                "</div>");
        List<Showtime> showtimeList = null;
        showtimeList = showtimeService.getShowtimeForFilm(film.getId());
        if (!showtimeList.isEmpty()) {
            showtimeList.removeIf(showtime -> Objects.equals(showtime.getStatus(), "finished") || Objects.equals(showtime.getStatus(), "canceled"));
            if (!showtimeList.isEmpty()) {
                showtimeList.sort(Comparator.comparing(Showtime::getDate));
                out.write("<div class=\"container\">");
                out.write("<label for=\"showtime\">" + resourceBundle.getString("label.availableShowtimes") + "</label>");
                out.write("<form id=\"showtime\" role=\"presentation\">\n");
                for (Showtime s : showtimeList) {
                    out.write("<button class=\"btn btn-block btn-info\" formmethod=\"get\" formaction=\""
                            + contextPath + "/ShowtimePage\" name=\"id\" value=\"" + s.getId().toString() + "\">"
                            + s.getDate() + "  " + s.getStartTime() + "-" + s.getEndTime() + "</button>\n");
                }
                out.write("</form>");
                out.write("</div>");
            }

        }

        out.write("</body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
