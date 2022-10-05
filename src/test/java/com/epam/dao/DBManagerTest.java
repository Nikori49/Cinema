package com.epam.dao;

import com.epam.dao.ConnectionPool;
import com.epam.dao.DBManager;
import com.epam.dao.Utils;
import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.Ticket;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
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

        UserService userService = new UserService(dbManager);

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

        User test = userService.findUserByEmail(user.getEmail());

        Assertions.assertEquals(user,test );
        Assertions.assertEquals(user.hashCode(), test.hashCode());
        Assertions.assertEquals(user.getId(), test.getId());


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

        UserService userService = new UserService(dbManager);

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


        Assertions.assertEquals(user.toString(), userService.findUserByLogin(user.getLogin()).toString());
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

        UserService userService = new UserService(dbManager);

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


        Assertions.assertEquals(user, userService.findUserByPhoneNumber(user.getPhoneNumber()));
    }

    @Test
    public void insertUserTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.INSERT_USER;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        UserService userService = new UserService(dbManager);

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


        Assertions.assertEquals(user, userService.createUser(user));
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
        when(connection.prepareStatement(statement1,Statement.RETURN_GENERATED_KEYS))
                .thenReturn(preparedStatement1);
        when(connection.prepareStatement(statement2))
                .thenReturn(preparedStatement2);
        DBManager dbManager = new DBManager(connectionPool);


        ShowtimeService showtimeService = new ShowtimeService(dbManager);



        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("planned");
        showtime1.setSeats(new Utils().fillSeatMap());
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).
                thenReturn(true)
                .thenReturn(false);
       // when(preparedStatement1.)
        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.getLong(1)).thenReturn(1L);




        Assertions.assertDoesNotThrow(()->showtimeService.createShowtime(showtime1));

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

        ShowtimeService showtimeService = new ShowtimeService(dbManager);

        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

            when(resultSet.next())
                    .thenReturn(true,true,false);





            when(resultSet.getLong("id"))
                    .thenReturn(showtime1.getId())
                    .thenReturn(showtime1.getId());




            when(resultSet.getLong("show_times.id"))
                    .thenReturn(showtime1.getId())
                    .thenReturn(showtime1.getId());





            when(resultSet.getLong("filmId"))
                    .thenReturn(showtime1.getFilmId())
                    .thenReturn(showtime1.getFilmId());





            when(resultSet.getDate("date"))
                    .thenReturn(showtime1.getDate())
                    .thenReturn(showtime1.getDate());





            when(resultSet.getString("show_times.status"))
                    .thenReturn(showtime1.getStatus())
                    .thenReturn("planned");





            when(resultSet.getTime("startTime"))
                    .thenReturn(showtime1.getStartTime())
                    .thenReturn(showtime1.getStartTime());





            when(resultSet.getTime("endTime"))
                    .thenReturn(showtime1.getEndTime())
                    .thenReturn(showtime1.getEndTime());





            when(resultSet.getString("seat"))
                    .thenReturn("A1")
                    .thenReturn("A2");
            when(resultSet.getString("seats.status"))
                    .thenReturn("vacant")
                    .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals(showtimeList, showtimeService.getShowtimeForDate(showtime1.getDate()));
    }

    @Test
    public void getShowtimeForFilmTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SHOWTIME_BY_FILM_ID;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);


        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals(showtimeList, showtimeService.getShowtimeForFilm(showtime1.getFilmId()));
    }

    @Test
    public void getShowtimeTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SHOWTIME_BY_ID;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);


        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Showtime test = showtimeService.getShowtime(showtime1.getId());


        Assertions.assertEquals(showtime1,test );
        Assertions.assertEquals(showtime1.hashCode(),test.hashCode() );
        Assertions.assertEquals(showtime1.toString(),test.toString() );
    }

    @Test
    public void cancelShowtimeTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.CANCEL_SHOWTIME_BY_ID;
        String statement1 = DBManager.GET_SHOWTIME_BY_ID;
        String statement2 = DBManager.GET_SHOWTIME_TICKETS;
        String statement3 = DBManager.UPDATE_USER_BALANCE;
        String statement4 = DBManager.UPDATE_TICKET_STATUS;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        PreparedStatement preparedStatement1 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement4 = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        when(connection.prepareStatement(statement1))
                .thenReturn(preparedStatement1);
        when(connection.prepareStatement(statement2))
                .thenReturn(preparedStatement2);
        when(connection.prepareStatement(statement3))
                .thenReturn(preparedStatement3);
        when(connection.prepareStatement(statement4))
                .thenReturn(preparedStatement4);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);


        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));

        Ticket ticket = new Ticket();
        ticket.setStatus("u");
        ticket.setId(1L);
        ticket.setSeat("K2");
        ticket.setShowTimeId(1L);
        ticket.setUserId(1L);



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);
        ResultSet resultSet1 = mock(ResultSet.class);

        when(preparedStatement2.executeQuery()).thenReturn(resultSet1);



        when(resultSet.next())
                .thenReturn(true,true,false);
        when(resultSet1.next())
                .thenReturn(true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");



        when(preparedStatement1.executeQuery()).thenReturn(resultSet);




        Assertions.assertDoesNotThrow(()->showtimeService.cancelShowtime(showtime1.getId()));
    }

    @Test
    public void insertFilmTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.INSERT_FILM;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        FilmService filmService = new FilmService(dbManager);


        Film film = new Film();
        film.setId(1L);
        film.setPosterImgPath("susPath");
        film.setDescription("testDesc");
        film.setGenre("testGenre");
        film.setName("testName");
        film.setDirector("testDirector");
        film.setYoutubeTrailerId("12345678901");
        film.setRunningTime(10L);


        Assertions.assertDoesNotThrow(()->filmService.createFilm(film));
    }

    @Test
    public void getFilmTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_FILM_BY_ID;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        FilmService filmService = new FilmService(dbManager);


        Film film = new Film();
        film.setId(1L);
        film.setPosterImgPath("susPath");
        film.setDescription("testDesc");
        film.setGenre("testGenre");
        film.setName("testName");
        film.setDirector("testDirector");
        film.setYoutubeTrailerId("12345678901");
        film.setRunningTime(10L);

        ResultSet resultSet = mock(ResultSet.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(film.getId());
        when(resultSet.getString("name")).thenReturn(film.getName());
        when(resultSet.getString("description")).thenReturn(film.getDescription());
        when(resultSet.getString("genre")).thenReturn(film.getGenre());
        when(resultSet.getString("posterImgPath")).thenReturn(film.getPosterImgPath());
        when(resultSet.getString("director")).thenReturn(film.getDirector());
        when(resultSet.getLong("runningTime")).thenReturn(film.getRunningTime());
        when(resultSet.getString("youtubeTrailerId")).thenReturn(film.getYoutubeTrailerId());


        Film test = filmService.getFilm(film.getId());

        Assertions.assertEquals(film,test);
        Assertions.assertEquals(film.hashCode(),test.hashCode());
        Assertions.assertEquals(film.toString(),test.toString());
    }

    @Test
    public void getAllFilmsTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_ALL_FILMS;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        FilmService filmService = new FilmService(dbManager);


        Film film = new Film();
        film.setId(1L);
        film.setPosterImgPath("susPath");
        film.setDescription("testDesc");
        film.setGenre("testGenre");
        film.setName("testName");
        film.setDirector("testDirector");
        film.setYoutubeTrailerId("12345678901");
        film.setRunningTime(10L);

        Film film1 = new Film();
        film1.setId(1L);
        film1.setPosterImgPath("susPath");
        film1.setDescription("testDesc");
        film1.setGenre("testGenre");
        film1.setName("testName1");
        film1.setDirector("testDirector");
        film1.setYoutubeTrailerId("12345678901");
        film1.setRunningTime(10L);

        ResultSet resultSet = mock(ResultSet.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(film.getId()).thenReturn(film1.getId());
        when(resultSet.getString("name")).thenReturn(film.getName()).thenReturn(film1.getName());
        when(resultSet.getString("description")).thenReturn(film.getDescription()).thenReturn(film1.getDescription());
        when(resultSet.getString("genre")).thenReturn(film.getGenre()).thenReturn(film1.getGenre());
        when(resultSet.getString("posterImgPath")).thenReturn(film.getPosterImgPath()).thenReturn(film1.getPosterImgPath());
        when(resultSet.getString("director")).thenReturn(film.getDirector()).thenReturn(film1.getDirector());
        when(resultSet.getLong("runningTime")).thenReturn(film.getRunningTime()).thenReturn(film1.getRunningTime());
        when(resultSet.getString("youtubeTrailerId")).thenReturn(film.getYoutubeTrailerId()).thenReturn(film1.getYoutubeTrailerId());

        List<Film> filmList = new ArrayList<>();
        filmList.add(film);
        filmList.add(film1);

        Assertions.assertEquals(filmList,filmService.getAllFilms());
    }

    @Test
    public void insertTicketTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement1 = DBManager.INSERT_TICKET;
        String statement2 = DBManager.UPDATE_SEAT_STATUS;
        String statement3 = DBManager.UPDATE_USER_BALANCE;
        PreparedStatement preparedStatement1 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        PreparedStatement preparedStatement3 = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement1))
                .thenReturn(preparedStatement1);
        when(connection.prepareStatement(statement2))
                .thenReturn(preparedStatement2);
        when(connection.prepareStatement(statement3))
                .thenReturn(preparedStatement3);
        DBManager dbManager = new DBManager(connectionPool);

        TicketService ticketService = new TicketService(dbManager);

        Ticket ticket = new Ticket();
        ticket.setSeat("A1");
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setShowTimeId(1L);
        ticket.setStatus("test");

        Assertions.assertDoesNotThrow(()->ticketService.createTicket(ticket));
    }

    @Test
    public void getTicketTest() throws SQLException, DBException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement1 = DBManager.GET_TICKET_BY_ID;

        PreparedStatement preparedStatement1 = mock(PreparedStatement.class);

        when(connection.prepareStatement(statement1))
                .thenReturn(preparedStatement1);

        DBManager dbManager = new DBManager(connectionPool);

        TicketService ticketService = new TicketService(dbManager);


        Ticket ticket = new Ticket();
        ticket.setSeat("A1");
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setShowTimeId(1L);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getLong("id")).thenReturn(ticket.getId());
        when(resultSet.getLong("userId")).thenReturn(ticket.getUserId());
        when(resultSet.getLong("showTimeId")).thenReturn(ticket.getShowTimeId());
        when(resultSet.getString("seat")).thenReturn(ticket.getSeat());

        when(preparedStatement1.executeQuery()).thenReturn(resultSet);




        Ticket test = ticketService.getTicket(ticket.getId());


        Assertions.assertEquals(ticket,test);
        Assertions.assertEquals(ticket.hashCode(),test.hashCode());
        Assertions.assertEquals(ticket.toString(),test.toString());
    }

    @Test
    public void getUserTicketsTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement1 = DBManager.GET_USERS_TICKETS;

        PreparedStatement preparedStatement1 = mock(PreparedStatement.class);

        when(connection.prepareStatement(statement1))
                .thenReturn(preparedStatement1);

        DBManager dbManager = new DBManager(connectionPool);

        TicketService ticketService = new TicketService(dbManager);


        Ticket ticket = new Ticket();
        ticket.setSeat("A1");
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setShowTimeId(1L);

        Ticket ticket1 = new Ticket();
        ticket1.setSeat("A11");
        ticket1.setId(1L);
        ticket1.setUserId(1L);
        ticket1.setShowTimeId(1L);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true,true,false);
        when(resultSet.getLong("id"))
                .thenReturn(ticket.getId())
                .thenReturn(ticket1.getId());
        when(resultSet.getLong("userId"))
                .thenReturn(ticket.getUserId())
                .thenReturn(ticket1.getUserId());
        when(resultSet.getLong("showTimeId"))
                .thenReturn(ticket.getShowTimeId())
                .thenReturn(ticket1.getShowTimeId());
        when(resultSet.getString("seat"))
                .thenReturn(ticket.getSeat())
                .thenReturn(ticket1.getSeat());

        when(preparedStatement1.executeQuery()).thenReturn(resultSet);



        List<Ticket> ticketList = new ArrayList<>();

        ticketList.add(ticket);
        ticketList.add(ticket1);




        Assertions.assertEquals(ticketList,ticketService.getUserTickets(1L));

    }

    @Test
    public  void getPlannedShowtimeTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_PLANNED_SHOWTIMES;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);

        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals(showtimeList, showtimeService.getPlannedShowtime());
    }

    @Test
    public void getSeatStatusTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SEAT_STATUS;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);

        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,false);


        when(resultSet.getString("status"))
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals("vacant", showtimeService.getSeatStatus(showtime1.getSeats().firstKey(),showtime1.getId()));
    }

    @Test
    public void getShowtimeForMonthTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SHOWTIMES_FOR_MONTH;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);

        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2222-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("planned");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals(showtimeList, showtimeService.getShowtimeForMonth(showtime1.getDate()));
    }

    @Test
    public void getShowtimeForWeek() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.GET_SHOWTIMES_FOR_WEEK;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);

        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2022-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("finished");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));



        Set<String> stringSet = new Utils().fillSeatMap().keySet();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next())
                .thenReturn(true,true,false);





        when(resultSet.getLong("id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());




        when(resultSet.getLong("show_times.id"))
                .thenReturn(showtime1.getId())
                .thenReturn(showtime1.getId());





        when(resultSet.getLong("filmId"))
                .thenReturn(showtime1.getFilmId())
                .thenReturn(showtime1.getFilmId());





        when(resultSet.getDate("date"))
                .thenReturn(showtime1.getDate())
                .thenReturn(showtime1.getDate());





        when(resultSet.getString("show_times.status"))
                .thenReturn(showtime1.getStatus())
                .thenReturn("planned");





        when(resultSet.getTime("startTime"))
                .thenReturn(showtime1.getStartTime())
                .thenReturn(showtime1.getStartTime());





        when(resultSet.getTime("endTime"))
                .thenReturn(showtime1.getEndTime())
                .thenReturn(showtime1.getEndTime());





        when(resultSet.getString("seat"))
                .thenReturn("A1")
                .thenReturn("A2");
        when(resultSet.getString("seats.status"))
                .thenReturn("vacant")
                .thenReturn("vacant");


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);




        List<List<Showtime>> list =  new ArrayList<>();
        list.add(showtimeList);
        for (int i = 0;i<6;i++){

            list.add(new ArrayList<>());
        }

        when(preparedStatement.executeQuery()).thenReturn(resultSet);




        Assertions.assertEquals(list, showtimeService.getShowtimeForWeek());
    }

    @Test
    public void updatePastShowtimeTest() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.UPDATE_PAST_SHOWTIMES;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        ShowtimeService showtimeService = new ShowtimeService(dbManager);


        Assertions.assertDoesNotThrow(showtimeService::finishPastShowtime);
    }

    @Test
    public void updateUserBalanceTest() throws SQLException{
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        Connection connection = mock(Connection.class);
        when(connectionPool.getConnection())
                .thenReturn(connection);
        String statement = DBManager.UPDATE_USER_BALANCE;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(statement))
                .thenReturn(preparedStatement);
        DBManager dbManager = new DBManager(connectionPool);

        UserService userService = new UserService(dbManager);


        Assertions.assertDoesNotThrow(() -> userService.updateBalance(1L, 75L));
    }




}
