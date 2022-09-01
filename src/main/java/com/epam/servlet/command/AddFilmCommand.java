package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.dao.entity.Film;
import com.epam.service.FilmService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Command which execute method validates and inserts Film object into DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class AddFilmCommand implements Command {
    private final FilmService filmService;

    @MyInject
    public AddFilmCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "manager.jsp";
        Film film = new Film();
        String filmName = request.getParameter("name");
        String filmDescription = request.getParameter("description");
        String filmGenre = request.getParameter("genre");
        String filmDirector = request.getParameter("director");
        String stringRuntime = request.getParameter("runtime");
        Part filePart = request.getPart("posterImg");
        String youtubeTrailerId = request.getParameter("youtubeTrailerId");
        if (filmName.isEmpty()) {
            request.getSession().setAttribute("filmError", 1);
            return "addFilm.jsp";
        }
        if (filmDescription.isEmpty()) {
            request.getSession().setAttribute("filmError", 2);
            return "addFilm.jsp";
        }
        if (filmGenre.isEmpty()) {
            request.getSession().setAttribute("filmError", 3);
            return "addFilm.jsp";
        }
        if (filmDirector.isEmpty()) {
            request.getSession().setAttribute("filmError", 4);
            return "addFilm.jsp";
        }
        if (stringRuntime.isEmpty() || stringRuntime.matches("\\D+")) {
            request.getSession().setAttribute("filmError", 5);
            return "addFilm.jsp";
        }
        if (filePart.getSize() == 0) {
            request.getSession().setAttribute("filmError", 6);
            return "addFilm.jsp";
        }
        if (youtubeTrailerId.length() != 11) {
            request.getSession().setAttribute("filmError", 7);
            return "addFilm.jsp";
        }
        Long filmRuntime = Long.valueOf(stringRuntime);
        film.setName(filmName);
        filmName = filmName.replaceAll("\\W+", "_");

        filePart.write("C:\\Users\\yohoh\\Desktop\\JAVA\\Cinema\\src\\main\\webapp\\posterImages\\" + filmName + "_poster");

        film.setDescription(filmDescription);
        film.setGenre(filmGenre);
        film.setDirector(filmDirector);
        film.setRunningTime(filmRuntime);
        film.setPosterImgPath("posterImages/" + filmName + "_poster");


        filmService.createFilm(film);

        return address;
    }
}
