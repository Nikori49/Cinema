package com.epam.dao;

import com.epam.dao.ConnectionPool;
import com.epam.dao.DBManager;
import com.epam.dao.Utils;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DBManagerTest {
    @Test
    public void findUserByEmailTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_USER_BY_EMAIL;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);


        User user = new User();
        user.setId(1L);
        user.setRole("testUser");
        user.setPassword("testPass");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("testEmail@test.com");
        user.setPhoneNumber("+123456789");

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(user.getPhoneNumber());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("surname")).thenReturn(user.getSurname());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(user.getRole());

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Assertions.assertEquals(user, dbManager.findUserByEMail(user.getEmail()));

    }

    @Test
    public void findUserByLoginTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_USER_BY_LOGIN;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);


        User user = new User();
        user.setId(1L);
        user.setRole("testUser");
        user.setPassword("testPass");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("testEmail@test.com");
        user.setPhoneNumber("+123456789");

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(user.getPhoneNumber());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("surname")).thenReturn(user.getSurname());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(user.getRole());

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Assertions.assertEquals(user.toString(), dbManager.findUserByLogin(user.getLogin()).toString());
    }

    @Test
    public void findUserByPhoneNumber() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_USER_BY_PHONE_NUMBER;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);


        User user = new User();
        user.setId(1L);
        user.setRole("testUser");
        user.setPassword("testPass");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("testEmail@test.com");
        user.setPhoneNumber("+123456789");

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(user.getPhoneNumber());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("surname")).thenReturn(user.getSurname());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(user.getRole());

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Assertions.assertEquals(user.toString(), dbManager.findUserByPhoneNumber(user.getPhoneNumber()).toString());
    }

    @Test
    public void insertUserTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.INSERT_USER;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);


        User user = new User();
        user.setId(1L);
        user.setRole("testUser");
        user.setPassword("testPass");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("testEmail@test.com");
        user.setPhoneNumber("+123456789");

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(2L);

        when(preparedStatement.execute())
                .thenReturn(true);
        when(preparedStatement.getGeneratedKeys())
                .thenReturn(resultSet);


        Assertions.assertEquals(2L, dbManager.insertUser(user).getId());
    }

    @Test
    public void createShowTimeTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement1 = DBManager.INSERT_SHOWTIME;
        String statement2 = DBManager.INSERT_SEATS;
        PreparedStatement preparedStatement1 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement1))
                .thenReturn(preparedStatement1);
        when(connection.prepareStatement(statement2))
                .thenReturn(preparedStatement2);
        DBManager dbManager = new DBManager(connectionPool);


        User user = new User();
        user.setId(1L);
        user.setRole("testUser");
        user.setPassword("testPass");
        user.setLogin("testLogin");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("testEmail@test.com");
        user.setPhoneNumber("+123456789");

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("phone_number")).thenReturn(user.getPhoneNumber());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("surname")).thenReturn(user.getSurname());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getString("role")).thenReturn(user.getRole());

        when(preparedStatement1.executeQuery()).thenReturn(resultSet);


        Assertions.assertEquals(user.toString(), dbManager.findUserByEMail(user.getEmail()).toString());
    }

    @Test
    public void getShowtimeForDate() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SHOWTIME_BY_DATE;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);


        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("planned");
        showtime1.setSeats(new Utils().fillSeatMap());
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));

        Showtime showtime2 = new Showtime();
        showtime2.setDate(Date.valueOf("2022-08-12"));
        showtime2.setId(2L);
        showtime2.setStatus("planned");
        showtime2.setSeats(new Utils().fillSeatMap());
        showtime2.setFilmId(2L);
        showtime2.setStartTime(Time.valueOf("10:10:10"));
        showtime2.setEndTime(Time.valueOf("12:12:12"));

        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);
        for (String ignored : stringSet) {
            when(resultSet.next())
                    .thenReturn(true);
        }
        for (String ignored : stringSet) {
            when(resultSet.next())
                    .thenReturn(true);
        }
        when(resultSet.next())
                .thenReturn(false);
        for (String ignored : stringSet) {
            when(resultSet.getLong("id"))
                    .thenReturn(showtime1.getId());
        }
        for (String ignored : stringSet) {
            when(resultSet.getLong("id"))
                    .thenReturn(showtime2.getId());
        }

        for (String ignored : stringSet) {
            when(resultSet.getLong("show_times.id"))
                    .thenReturn(showtime1.getId());
        }
        for (String ignored : stringSet) {
            when(resultSet.getLong("show_times.id"))
                    .thenReturn(showtime2.getId());
        }


        for (String ignored : stringSet) {
            when(resultSet.getLong("filmId"))
                    .thenReturn(showtime1.getFilmId());
        }
        for (String ignored : stringSet) {
            when(resultSet.getLong("filmId"))
                    .thenReturn(showtime2.getFilmId());
        }


        for (String ignored : stringSet) {
            when(resultSet.getDate("date"))
                    .thenReturn(showtime1.getDate());
        }
        for (String ignored : stringSet) {
            when(resultSet.getDate("date"))
                    .thenReturn(showtime2.getDate());
        }


        for (String ignored : stringSet) {
            when(resultSet.getString("show_times.status"))
                    .thenReturn(showtime1.getStatus());
        }
        for (String ignored : stringSet) {
            when(resultSet.getString("show_times.status"))
                    .thenReturn(showtime2.getStatus());
        }


        for (String ignored : stringSet) {
            when(resultSet.getTime("startTime"))
                    .thenReturn(showtime1.getStartTime());
        }
        for (String ignored : stringSet) {
            when(resultSet.getTime("startTime"))
                    .thenReturn(showtime2.getStartTime());
        }


        for (String ignored : stringSet) {
            when(resultSet.getTime("endTime"))
                    .thenReturn(showtime1.getEndTime());
        }
        for (String ignored : stringSet) {
            when(resultSet.getTime("endTime"))
                    .thenReturn(showtime2.getEndTime());
        }


        for (String s : showtime1.getSeats().keySet()) {
            when(resultSet.getString("seat")).thenReturn(s);
            when(resultSet.getString(resultSet.getString("seats.status"))).thenReturn(showtime1.getSeats().get(s));
        }
        for (String s : showtime2.getSeats().keySet()) {
            when(resultSet.getString("seat")).thenReturn(s);
            when(resultSet.getString(resultSet.getString("seats.status"))).thenReturn(showtime2.getSeats().get(s));
        }
        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);
        showtimeList.add(showtime2);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        System.out.println(showtimeList);
        System.out.println(dbManager.getShowtimeForDate(showtime1.getDate()));


        Assertions.assertEquals(showtimeList, dbManager.getShowtimeForDate(showtime1.getDate()));
    }


}
