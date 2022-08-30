package com.epam.servlet.ajax;

import com.epam.dao.entity.Film;
import com.epam.service.FilmService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * AJAX Servlet that searches for film in DB and builds result links based on value from request.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "SearchFilm", value = "/SearchFilm")
public class SearchFilm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("string");
        FilmService filmService = (FilmService) request.getServletContext().getAttribute("filmService");
        List<Film> filmList = filmService.getAllFilms();
        List<Film> resultList = new ArrayList<>();
        if (!search.isEmpty()) {
            for (Film f : filmList) {
                if (f.getName().toLowerCase().contains(search.toLowerCase())) {
                    resultList.add(f);
                }
            }
        }


        if (!resultList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (resultList.toArray().length > 2) {
                resultList = resultList.subList(0, 2);
            }

            for (Film f : resultList) {
                stringBuilder.append("<a href=\"").append(request.getContextPath()).append("/FilmPage?id=").append(f.getId()).append("\">").append(f.getName()).append("</a>");
            }

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(stringBuilder));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
