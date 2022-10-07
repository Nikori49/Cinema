package com.epam.servlet;

import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Servlet that constructs showtime page based on provided showtime id.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "ShowtimePage", value = "/ShowtimePage")
public class ShowtimePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String showtimeId = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("loggedUser");
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
        if (showtimeId.isEmpty() || !showtimeId.matches("\\d+")) {
            response.sendRedirect("index.jsp");
            return;
        }
        Showtime showtime = null;
        showtime = showtimeService.getShowtime(Long.valueOf(showtimeId));

        if (showtime == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        if (Objects.equals(showtime.getStatus(), "finished") || Objects.equals(showtime.getStatus(), "canceled")) {
            response.sendRedirect("index.jsp");
            return;
        }


        Film film = null;

            film = filmService.getFilm(showtime.getFilmId());

        TreeMap<String, String> seats = showtime.getSeats();


        out.write("<html>\n" +
                "<head>\n" +
                "    <title>" + film.getName() + "</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
                "<script>\n" +
                "    $(document).ready(function(){\n" +
                "        $('[data-toggle=\"popover\"]').popover();\n" +
                "    });\n" +
                "</script>\n");
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
                "            <li><a href=\"films.jsp\">" + resourceBundle.getString("label.films") + "</a></li>\n" +
                "            <li class=\"active\"><a href=\"schedule.jsp\">" + resourceBundle.getString("label.schedule") + "</a></li>\n");
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
                    "                        <span class=\"glyphicon glyphicon glyphicon-log-out\"></span>" + resourceBundle.getString("label.logOut") + "\n" +
                    "                    </a>\n" +
                    "                </li>");
        }
        if (Objects.equals(userRole, "client")) {
            out.write("                <li><a href=\"client.jsp\"><span class=\"glyphicon glyphicon-user\"></span>" + resourceBundle.getString("label.profile") + "</a></li>\n");
        }
        if (Objects.equals(userRole, "manager")) {
            out.write("                <li><a href=\"manager.jsp\"><span class=\" glyphicon glyphicon-briefcase\"></span>" + resourceBundle.getString("label.managerWorkplace") + "</a></li>\n");
        }

        out.write("</ul>\n" +
                "</div>\n" +
                "</nav>");
        out.write("<div class=\"container\"<h3>" + film.getName() + " " + showtime.getStartTime().toString().substring(0, 5) + "-" + showtime.getEndTime().toString().substring(0, 5) + "</h3>");
        out.write("<img   id=\"poster\" class=\"img-responsive\" alt=\"Missing poster\"  width=\"200\" height=\"300\"  src=\"" + film.getPosterImgPath() + "\"></div>");
        out.write("<script>" +
                "let request;\n" +
                "        function purchaseTicketPreview() {\n");
        for (String s : seats.keySet()) {
            out.write("const " + s + " = document.getElementById(\"" + s + "\"); \n");
            out.write("let " + s + "_status='false';\n" +
                    "if (" + s + ".checked == true && " + s + ".disabled!=true ){  \n" +
                    s + "_status ='true' \n" +
                    "  }  \n");
        }
        out.write("            const url = \"" + request.getContextPath() + "/TicketPreview?");
        for (String s : seats.keySet()) {
            out.write(s + "=\"+" + s + "_status" + "+\"&");
        }
        out.write("id=\"+" + showtimeId + "; \n");

        out.write("            if (window.XMLHttpRequest) {\n" +
                "                request = new XMLHttpRequest();\n" +
                "            } else if (window.ActiveXObject) {\n" +
                "                request = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "            }\n" +
                "\n" +
                "            try {\n" +
                "                request.onreadystatechange = getPreview;\n" +
                "                request.open(\"GET\", url, true);\n" +
                "                request.send();\n" +
                "            } catch (e) {\n" +
                "                alert(\"Unable to connect to server\");\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        function getPreview() {\n" +
                "            if (request.readyState === 4) {\n" +
                "                document.getElementById('ticketPreview').innerHTML = request.responseText;\n" +
                "            }\n" +
                "        }" +
                "</script>");

        out.write("<div style=\"position: absolute; top: 15%; right: 0%\" class=\"container\">\n" +
                "    <form role=\"form\" action=\"controller\" method=\"post\">\n" +
                "        <input type=\"hidden\" name=\"command\" value=\"purchaseTickets\">\n" +
                "        <input type=\"hidden\" id=\"showtimeId\" name=\"showtimeId\" value=\"" + showtime.getId() + "\">\n" +
                "        <table class=\"table-responsive\">\n" +
                "            <tr>\n");

        List<String> seatList = new ArrayList<>(seats.keySet());
        seatList.sort((o1, o2) -> {
            Character character1 = o1.charAt(0);
            Character character2 = o2.charAt(0);
            if (character1.compareTo(character2) != 0) {
                return character1.compareTo(character2);
            }
            if (o1.length() != o2.length()) {
                return o1.length() - o2.length();
            }
            return o1.compareTo(o2);
        });



        int counter = 1;
        for (String s : seatList) {


            out.write("<td><input class=\"checkbox-inline\" ");
            if (Objects.equals(seats.get(s), "occupied")) {
                out.write(" disabled checked");
            }
            out.write(" data-placement=\"top\" ");
            if (Objects.equals(userRole, "client")) {
                out.write("onclick=\"purchaseTicketPreview()\" ");
            }
            out.write("data-toggle=\"popover\" data-trigger=\"hover\" data-content=\"" + s + "\" id=\"" + s + "\" value=\"" + s + "\" name=\"" + s + "\" type=\"checkbox\"></td>\n");

            if (counter == 24) {
                out.write("</tr><tr>");
                counter = 0;
            }
            counter++;
        }
        out.write("            </tr>\n" +
                "        </table>\n");
        out.write("<span id=\"ticketPreview\"></span>");

        out.write("    </form>\n" +
                "</div>");
        out.write("</body");
    }


}
