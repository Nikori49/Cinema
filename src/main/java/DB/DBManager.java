package DB;

import DB.entity.Film;
import DB.entity.Showtime;
import DB.entity.Ticket;
import DB.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Class that handles DB interactions.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 * @see ConnectionPool
 */
public class DBManager {
    private static DBManager instance;
    private final Connection connection;

    /**
     * Returns existing DBManager instance or creates one if none exist.
     *
     * @return <code>DBManager</code> object
     */
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            try {
                instance = new DBManager();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private DBManager() throws SQLException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    public static final String GET_USER_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number=?";
    public static final String INSERT_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,md5(?),'client')";
    public static final String GET_SHOWTIME_BY_DATE = "SELECT * FROM show_times WHERE date=?";
    public static final String GET_SHOWTIME_BY_FILM_ID = "SELECT * FROM show_times WHERE filmId=?";
    public static final String GET_SHOWTIME_BY_ID = "SELECT * FROM show_times WHERE id=?";
    public static final String CANCEL_SHOWTIME_BY_ID = "UPDATE cinema.show_times SET status='canceled' WHERE show_times.id=?";
    public static final String INSERT_FILM = "INSERT INTO films values (DEFAULT,?,?,?,?,?,?,?)";
    public static final String GET_FILM_BY_ID = "SELECT * FROM films WHERE id=?";
    public static final String GET_ALL_FILMS = "SELECT * FROM films";
    public static final String GET_PLANNED_SHOWTIMES = "SELECT * FROM show_times WHERE status='planned'";
    public static final String INSERT_TICKET = "INSERT INTO tickets VALUES (DEFAULT,?,?,?)";
    public static final String GET_TICKET_BY_ID = "SELECT * FROM tickets WHERE id=?";
    public static final String GET_USERS_TICKETS = "SELECT * FROM tickets WHERE userId=?";
    public static final String GET_SHOWTIMES_FOR_MONTH = "SELECT * FROM show_times WHERE date>=? AND date<=?";
    public static final String GET_SHOWTIMES_FOR_WEEK = "SELECT * FROM show_times WHERE date=? AND status='planned'";
    public static final String UPDATE_PAST_SHOWTIMES = "UPDATE show_times SET status = 'finished' WHERE NOT status = 'canceled' and (date<? OR (date = ? AND endTime < ?))";
    public static final String INSERT_SHOWTIME = "INSERT INTO show_times VALUES (DEFAULT,?,?,?,?,?)";
    public static final String INSERT_SEATS = "INSERT INTO seats VALUES (DEFAULT,?,?,'vacant')";
    public static final String GET_SHOWTIME_SEATS = "SELECT * FROM seats WHERE showtimeId=?";
    public static final String UPDATE_SEAT_STATUS = "UPDATE seats SET status='occupied' WHERE seat=? AND showtimeId=?";
    public static final String GET_SEAT_STATUS = "SELECT * FROM seats WHERE seat=? AND showtimeId=?";

    /**
     * Returns User object based on SQL statement and search parameter.
     *
     * @param statement       SQL statement to use in search
     * @param searchParameter parameter with which to identify a user
     * @return <code>User</code> object
     * @see User,DBManager
     */
    public User getUser(String searchParameter, String statement) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1, searchParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * Returns User object based on with a specified email.
     *
     * @param email email to search by
     * @return <code>User</code> object
     * @see User,DBManager
     */
    public User findUserByEMail(String email) {
        return getUser(email, GET_USER_BY_EMAIL);
    }

    /**
     * Returns User object based on with a specified phone number.
     *
     * @param phoneNumber phone number to search by
     * @return <code>User</code> object
     * @see User,DBManager
     */
    public User findUserByPhoneNumber(String phoneNumber) {
        return getUser(phoneNumber, GET_USER_BY_PHONE_NUMBER);
    }

    /**
     * Returns User object based on with a specified login.
     *
     * @param login login to search by
     * @return <code>User</code> object
     * @see User,DBManager
     */
    public User findUserByLogin(String login) {
        return getUser(login, GET_USER_BY_LOGIN);
    }

    /**
     * Inserts User object into DB.
     *
     * @param user User object to insert
     * @return <code>User</code> object
     * @see User,DBManager
     */
    public User insertUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Extracts User object from ResultSet object.
     *
     * @param resultSet ResultSet object to extract User from
     * @return <code>User</code> object
     * @see User,DBManager
     */
    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        return user;
    }

    /**
     * Inserts Showtime object into <code>show_times</code> table and related seats into <code>seats</code> table.
     *
     * @param showtime <code>Showtime</code> object to insert
     * @see Showtime,java.util.Map,DBManager
     */
    public void createShowTime(Showtime showtime) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementInsertShowTime = connection.prepareStatement(INSERT_SHOWTIME, Statement.RETURN_GENERATED_KEYS);
            preparedStatementInsertShowTime.setLong(1, showtime.getFilmId());
            preparedStatementInsertShowTime.setDate(2, showtime.getDate());
            preparedStatementInsertShowTime.setString(3, showtime.getStatus());
            preparedStatementInsertShowTime.setTime(4, showtime.getStartTime());
            preparedStatementInsertShowTime.setTime(5, showtime.getEndTime());
            preparedStatementInsertShowTime.execute();
            ResultSet resultSet = preparedStatementInsertShowTime.getGeneratedKeys();
            while (resultSet.next()) {
                showtime.setId(resultSet.getLong(1));
            }
            TreeMap<String, String> seatMap = Utils.fillSeatMap();
            for (String seat : seatMap.keySet()) {
                PreparedStatement preparedStatementInsertSeat = connection.prepareStatement(INSERT_SEATS);
                preparedStatementInsertSeat.setLong(1, showtime.getId());
                preparedStatementInsertSeat.setString(2, seat);
                preparedStatementInsertSeat.execute();
            }
            connection.commit();


        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Returns list of Showtime objects with specified date.
     *
     * @param date <code>Date</code> object to search with.
     * @return List of Showtime objects
     * @see Showtime,Date,DBManager
     */
    public List<Showtime> getShowtimeForDate(Date date) {
        List<Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_DATE);
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Showtime showtime = extractShowtime(resultSet);

                showtimeList.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return showtimeList;
    }

    /**
     * Returns list of Showtime objects with specified film id.
     *
     * @param filmId <code>Long</code> object to search with.
     * @return List of Showtime objects
     * @see Showtime,DBManager,Film
     */
    public List<Showtime> getShowtimeForFilm(Long filmId) {
        List<Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_FILM_ID);
            preparedStatement.setLong(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Showtime showtime = extractShowtime(resultSet);

                showtimeList.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return showtimeList;
    }

    /**
     * Returns Showtime object with specified id.
     *
     * @param id <code>Long</code> object to search with.
     * @return Showtime object
     * @see Showtime,DBManager
     */
    public Showtime getShowTime(Long id) {
        Showtime showtime = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                showtime = extractShowtime(resultSet);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    /**
     * Updates status of Showtime  with specified id to "canceled".
     *
     * @param id <code>Long</code> object to search with.
     * @see Showtime,DBManager
     */
    public void cancelShowtime(Long id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_SHOWTIME_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Inserts Film object into <code>films</code> table.
     *
     * @param film <code>Film</code> object to insert.
     * @see Film,DBManager
     */
    public void insertFilm(Film film) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILM);
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setString(3, film.getGenre());
            preparedStatement.setString(4, film.getPosterImgPath());
            preparedStatement.setString(5, film.getDirector());
            preparedStatement.setLong(6, film.getRunningTime());
            preparedStatement.setString(7, film.getYoutubeTrailerId());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            }catch (SQLException s){
                s.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Returns Film object with specified id.
     *
     * @param id <code>Long</code> object to search with.
     * @return Film object
     * @see Film,DBManager
     */
    public Film getFilm(Long id) {
        Film film = new Film();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FILM_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = extractFilm(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

    /**
     * Returns list of all Film objects in <code>films</code> table.
     *
     * @return List of Film objects
     * @see Film,java.util.List,DBManager
     */
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FILMS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                filmList.add(extractFilm(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmList;
    }

    /**
     * Extracts Film object from ResultSet object.
     *
     * @param resultSet <code>ResultSet</code> object to extract from.
     * @return Film object
     * @see Film,ResultSet,DBManager
     */
    public Film extractFilm(ResultSet resultSet) throws SQLException {
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
     * Extracts Showtime object from ResultSet object.
     *
     * @param resultSet <code>ResultSet</code> object to extract from.
     * @return Showtime object
     * @see Showtime,ResultSet,DBManager
     */
    public Showtime extractShowtime(ResultSet resultSet) throws SQLException {
        Showtime showtime = new Showtime();
        showtime.setId(resultSet.getLong("id"));
        showtime.setFilmId(resultSet.getLong("filmId"));
        showtime.setDate(resultSet.getDate("date"));
        showtime.setStatus(resultSet.getString("status"));
        showtime.setStartTime(resultSet.getTime("startTime"));
        showtime.setEndTime(resultSet.getTime("endTime"));
        PreparedStatement preparedStatementGetSeatTable = connection.prepareStatement(GET_SHOWTIME_SEATS);
        preparedStatementGetSeatTable.setLong(1, resultSet.getLong("id"));
        ResultSet resultSetSeatTable = preparedStatementGetSeatTable.executeQuery();
        TreeMap<String, String> seatMap = new TreeMap<>();
        while (resultSetSeatTable.next()) {
            seatMap.put(resultSetSeatTable.getString("seat"), resultSetSeatTable.getString("status"));
        }
        showtime.setSeats(seatMap);
        return showtime;
    }

    /**
     * Inserts Ticket object into <code>tickets</code> table.
     *
     * @param ticket <code>Ticket</code> object to insert.
     * @see Ticket,DBManager
     */
    public void insertTicket(Ticket ticket) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementForTicket = connection.prepareStatement(INSERT_TICKET);
            preparedStatementForTicket.setLong(1, ticket.getUserId());
            preparedStatementForTicket.setLong(2, ticket.getShowTimeId());
            preparedStatementForTicket.setString(3, ticket.getSeat());
            preparedStatementForTicket.execute();
            PreparedStatement preparedStatementForSeat = connection.prepareStatement(UPDATE_SEAT_STATUS);
            preparedStatementForSeat.setString(1, ticket.getSeat());
            preparedStatementForSeat.setLong(2, ticket.getShowTimeId());
            preparedStatementForSeat.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();

        }
    }

    /**
     * Returns Ticket object with specified id.
     *
     * @param id <code>Long</code> object to search with.
     * @return Ticket object
     * @see Ticket,DBManager
     */
    public Ticket getTicket(Long id) {
        Ticket ticket = new Ticket();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TICKET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticket = extractTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    /**
     * Returns list of Showtime objects with "planned" status.
     *
     * @return List of Showtime objects
     * @see Showtime,DBManager
     */
    public List<Showtime> getPlannedShowtimes() {
        List<Showtime> showtimeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PLANNED_SHOWTIMES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtimeList.add(extractShowtime(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (showtimeList.isEmpty()) {
            return null;
        }
        return showtimeList;
    }

    /**
     * Returns String status of specified seat in specified by id showtime.
     *
     * @param key        <code>String</code> object representing seat id for search.
     * @param showtimeId <code>Long</code> object representing showtime id for search.
     * @return String seat status
     * @see Showtime,DBManager
     */
    public String getSeatStatus(String key, Long showtimeId) {
        String status = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SEAT_STATUS);
            preparedStatement.setString(1, key);
            preparedStatement.setLong(2, showtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Returns list of Ticket objects for specified user.
     *
     * @param user <code>User</code> object to search with.
     * @return List of Ticket objects
     * @see Ticket,DBManager
     */
    public List<Ticket> getUserTickets(User user) {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_TICKETS);
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ticketList.add(extractTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    /**
     * Extracts Ticket objects from ResultSet object.
     *
     * @param resultSet <code>ResultSet</code> object to extract from.
     * @return Ticket object
     * @see DBManager,Ticket,ResultSet
     */
    public Ticket extractTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setUserId(resultSet.getLong("userId"));
        ticket.setShowTimeId(resultSet.getLong("showTimeId"));
        ticket.setSeat(resultSet.getString("seat"));
        return ticket;
    }

    /**
     * Returns list of Showtime objects with dates within following month.
     *
     * @param firstDay <code>Date</code> object representing first day of the month to search with.
     * @return List of Showtime objects
     * @see Showtime,DBManager
     */
    public List<Showtime> getShowtimesForMonth(Date firstDay) {
        List<Showtime> showtimeList = new ArrayList<>();
        Date lastDay = Date.valueOf(firstDay.toLocalDate().plusMonths(1));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIMES_FOR_MONTH);
            preparedStatement.setDate(1, firstDay);
            preparedStatement.setDate(2, lastDay);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtimeList.add(extractShowtime(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimeList;
    }

    /**
     * Returns list of Showtime objects with dates within next 7 days.
     *
     * @return List of Showtime objects
     * @see Showtime,DBManager
     */
    public List<List<Showtime>> getShowtimesForWeek() {
        List<List<Showtime>> weekList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        long dateLong = date.getTime();
        for (int i = 0; i < 7; i++) {
            try {
                List<Showtime> showtimeList = new ArrayList<>();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIMES_FOR_WEEK);
                preparedStatement.setDate(1, new Date(dateLong));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    showtimeList.add(extractShowtime(resultSet));
                }
                showtimeList.sort((o1, o2) -> (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime()));
                weekList.add(showtimeList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dateLong = dateLong + 86_400_000;
        }
        return weekList;
    }

    /**
     * Updates status to "finished" of Showtimes which end time has passed.
     *
     * @see Showtime,DBManager
     */
    public void updatePastShowtimeStatuses() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PAST_SHOWTIMES);
            preparedStatement.setDate(1, Date.valueOf(localDate));
            preparedStatement.setDate(2, Date.valueOf(localDate));
            preparedStatement.setTime(3, Time.valueOf(localTime));
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
        }
    }


}
