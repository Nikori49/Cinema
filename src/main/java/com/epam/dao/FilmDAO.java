package com.epam.dao;

import com.epam.dao.entity.Entity;
import com.epam.dao.entity.Film;
import com.epam.dao.exception.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO implements DAO{
    private final ConnectionPool connectionPool;

    private static final Logger logger = LogManager.getLogger(FilmDAO.class);

    public FilmDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final String INSERT_FILM = "INSERT INTO films values (DEFAULT,?,?,?,?,?,?,?)";
    private static final String GET_FILM_BY_ID = "SELECT * FROM films WHERE id=?";
    private static final String GET_ALL_FILMS = "SELECT * FROM films";

    /**
     * Returns Film object with specified id.
     *
     * @param id <code>Long</code> object to search with.
     * @return Film object
     * @see Film
     */
    @Override
    public Film findById(long id) throws DBException {
        Film film = new Film();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FILM_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = extract(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return film;
    }

    /**
     * Returns list of all Film objects in <code>films</code> table.
     *
     * @return List of Film objects
     * @see Film,java.util.List
     */
    public List<Film> getAllFilms() throws DBException {
        List<Film> filmList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FILMS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                filmList.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return filmList;
    }


    @Override
    public Film extract(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setGenre(resultSet.getString("genre"));
        film.setPosterImgPath(resultSet.getString("posterImgPath"));
        film.setDirector(resultSet.getString("director"));
        film.setRunningTime(resultSet.getLong("runningTime"));
        film.setYoutubeTrailerId(resultSet.getString("youtubeTrailerId"));
        return film;
    }


    /**
     * Inserts Film object into <code>films</code> table.
     *
     * @param entity <code>Film</code> object to insert.
     * @see Film
     */
    @Override
    public void insert(Entity entity) throws DBException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILM)) {

            Film film = (Film) entity;
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setString(3, film.getGenre());
            preparedStatement.setString(4, film.getPosterImgPath());
            preparedStatement.setString(5, film.getDirector());
            preparedStatement.setLong(6, film.getRunningTime());
            preparedStatement.setString(7, film.getYoutubeTrailerId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }


}
