package com.epam.service;

import com.epam.annotation.Service;
import com.epam.dao.FilmDAO;
import com.epam.dao.entity.Film;
import com.epam.dao.exception.DBException;

import java.util.List;
@Service
public class FilmService {
    private final FilmDAO filmDAO;

    public FilmService(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    public void createFilm(Film film){
        try {
            filmDAO.insert(film);
        }catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Film getFilm(Long filmId) {
        try {
            return filmDAO.findById(filmId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public List<Film> getAllFilms(){
        try {
            return filmDAO.getAllFilms();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
