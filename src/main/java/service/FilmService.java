package service;

import DB.DBManager;
import DB.entity.Film;
import DB.exception.DBException;

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
