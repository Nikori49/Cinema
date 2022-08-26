package servlets.ajaxServlets;

import DB.DBManager;
import DB.entity.Film;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * AJAX Servlet that builds image tag based film id value from request.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "GetFilm", value = "/GetFilm")
public class GetFilm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long filmId = Long.valueOf(request.getParameter("film"));
        Film film = DBManager.getInstance().getFilm(filmId);
        String imagePath = ("<img class=\"img-responsive\" alt=\"Missing poster\" width=\"300\" height=\"450\" src=\"" + film.getPosterImgPath() + "\">");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(imagePath);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
