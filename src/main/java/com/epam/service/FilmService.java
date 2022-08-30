package com.epam.service;

import com.epam.dao.DBManager;
import com.epam.dao.entity.Film;
import com.epam.dao.exception.DBException;

import java.util.List;

public class FilmService {
    private final DBManager dbManager;

    public FilmService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void createFilm(Film film){
        try {
            dbManager.insertFilm(film);
        }catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Film getFilm(Long filmId){
            return dbManager.getFilm(filmId);
    }

    public List<Film> getAllFilms(){
        try {
            return dbManager.getAllFilms();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
