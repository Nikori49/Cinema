package com.epam.dao;

import com.epam.dao.entity.Entity;
import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.Ticket;
import com.epam.dao.exception.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class ShowtimeDAO implements DAO{

    private final ConnectionPool connectionPool;

    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    public ShowtimeDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final String GET_SHOWTIME_BY_DATE = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId WHERE show_times.date=? ORDER BY show_times.id";
    private static final String GET_SHOWTIME_BY_FILM_ID = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId WHERE show_times.filmId=? ORDER BY show_times.id";
    private static final String GET_SHOWTIME_BY_ID = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId WHERE show_times.id=? ORDER BY show_times.id";
    private static final String CANCEL_SHOWTIME_BY_ID = "UPDATE cinema.show_times SET status='canceled' WHERE show_times.id=?";
    private static final String GET_SHOWTIMES_FOR_MONTH = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId  WHERE show_times.date>=? AND show_times.date<=? ORDER BY show_times.id";
    private static final String GET_SHOWTIMES_FOR_WEEK = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId WHERE show_times.date=? AND show_times.status='planned' ORDER BY show_times.id";
    private static final String UPDATE_PAST_SHOWTIMES = "UPDATE show_times SET status = 'finished' WHERE NOT status = 'canceled' and (date<? OR (date = ? AND endTime < ?))";
    private static final String GET_PLANNED_SHOWTIMES = "SELECT * FROM show_times join  seats ON show_times.id=seats.showtimeId  ORDER BY show_times.id";
    private static final String UPDATE_SEAT_STATUS = "UPDATE seats SET status=? WHERE seat=? AND showtimeId=?";
    private static final String INSERT_SHOWTIME = "INSERT INTO show_times VALUES (DEFAULT,?,?,?,?,?)";
    private static final String INSERT_SEATS = "INSERT INTO seats VALUES (DEFAULT,?,?,'vacant')";
    private static final String GET_SEAT_STATUS = "SELECT * FROM seats WHERE seat=? AND showtimeId=?";
    private static final String UPDATE_USER_BALANCE = "UPDATE users SET balance=balance+? WHERE id=?";
    private static final String UPDATE_TICKET_STATUS = "UPDATE tickets SET status=? where id=?";
    private static final String GET_SHOWTIME_TICKETS = "SELECT * FROM tickets WHERE showTimeId=?";


    public void updateSeatStatus(Long showtimeId, String seat, String status) throws DBException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SEAT_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, seat);
            preparedStatement.setLong(3, showtimeId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
    }

    /**
     * Returns list of Showtime objects with "planned" status.
     *
     * @return List of Showtime objects
     * @see Showtime
     */
    public List<Showtime> getPlannedShowtimes() throws DBException {
        List<Showtime> showtimeList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PLANNED_SHOWTIMES)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtimeList.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
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
     * @see Showtime
     */
    public String getSeatStatus(String key, Long showtimeId) throws DBException {
        String status = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SEAT_STATUS)) {

            preparedStatement.setString(1, key);
            preparedStatement.setLong(2, showtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return status;
    }


    @Override
    public Showtime findById(long id) throws DBException {
        Showtime showtime = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtime = extract(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return showtime;
    }

    @Override
    public Showtime extract(ResultSet resultSet) throws SQLException {
        Showtime showtime = new Showtime();
        showtime.setId(resultSet.getLong("show_times.id"));
        showtime.setFilmId(resultSet.getLong("filmId"));
        showtime.setDate(resultSet.getDate("date"));
        showtime.setStatus(resultSet.getString("show_times.status"));
        showtime.setStartTime(resultSet.getTime("startTime"));
        showtime.setEndTime(resultSet.getTime("endTime"));
        if (Objects.equals(showtime.getStatus(), "planned") &&
                (showtime.getDate().compareTo(Date.valueOf(LocalDate.now())) < 0
                        || (showtime.getDate().compareTo(Date.valueOf(LocalDate.now())) == 0 && showtime.getEndTime().getTime() < Time.valueOf(LocalTime.now()).getTime()))) {
            showtime.setStatus("finished");
        }
        TreeMap<String, String> seatMap = new TreeMap<>();
        int counter = 0;
        do {
            seatMap.put(resultSet.getString("seat"), resultSet.getString("seats.status"));
            counter++;
        } while (resultSet.next() && counter < 288);

        showtime.setSeats(seatMap);

        return showtime;
    }

    /**
     * Inserts Showtime object into <code>show_times</code> table and related seats into <code>seats</code> table.
     *
     * @param entity <code>Showtime</code> object to insert
     * @see Showtime ,java.util.Map
     */
    @Override
    public void insert(Entity entity) throws DBException {
        Connection connection = null;
        Showtime showtime = (Showtime) entity;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatementInsertShowTime = connection.prepareStatement(INSERT_SHOWTIME, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);

            preparedStatementInsertShowTime.setLong(1
                    , showtime.getFilmId());
            preparedStatementInsertShowTime.setDate(2, showtime.getDate());
            preparedStatementInsertShowTime.setString(3, showtime.getStatus());
            preparedStatementInsertShowTime.setTime(4, showtime.getStartTime());
            preparedStatementInsertShowTime.setTime(5, showtime.getEndTime());
            preparedStatementInsertShowTime.execute();
            ResultSet resultSet = preparedStatementInsertShowTime.getGeneratedKeys();
            while (resultSet.next()) {
                showtime.setId(resultSet.getLong(1));
            }
            TreeMap<String, String> seatMap = new Utils().fillSeatMap();
            for (String seat : seatMap.keySet()) {
                PreparedStatement preparedStatementInsertSeat = connection.prepareStatement(INSERT_SEATS);
                preparedStatementInsertSeat.setLong(1, showtime.getId());
                preparedStatementInsertSeat.setString(2, seat);
                preparedStatementInsertSeat.execute();
            }
            connection.commit();


        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
    }



    /**
     * Returns list of Showtime objects with specified date.
     *
     * @param date <code>Date</code> object to search with.
     * @return List of Showtime objects
     * @see Showtime,Date
     */
    public List<Showtime> getShowtimeForDate(Date date) throws DBException {
        List<Showtime> showtimeList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_DATE)) {

            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Showtime showtime = extract(resultSet);
                showtimeList.add(showtime);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return showtimeList;
    }

    /**
     * Returns list of Showtime objects with specified film id.
     *
     * @param filmId <code>Long</code> object to search with.
     * @return List of Showtime objects
     * @see Showtime, Film
     */
    public List<Showtime> getShowtimeForFilm(Long filmId) throws DBException {
        List<Showtime> showtimeList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_BY_FILM_ID)) {

            preparedStatement.setLong(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Showtime showtime = extract(resultSet);

                showtimeList.add(showtime);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }


        return showtimeList;
    }



    /**
     * Updates status of Showtime  with specified id to "canceled".
     *
     * @param id <code>Long</code> object to search with.
     * @see Showtime
     */
    public void cancelShowtime(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Showtime showtime = findById(id);
            PreparedStatement preparedStatementCancelShowtime = connection.prepareStatement(CANCEL_SHOWTIME_BY_ID);
            preparedStatementCancelShowtime.setLong(1, id);
            preparedStatementCancelShowtime.execute();
            PreparedStatement preparedStatementShowtimeTickets = connection.prepareStatement(GET_SHOWTIME_TICKETS);
            preparedStatementShowtimeTickets.setLong(1, showtime.getId());
            ResultSet resultSet = preparedStatementShowtimeTickets.executeQuery();
            List<Ticket> ticketList = new ArrayList<>();
            while (resultSet.next()) {
                ticketList.add(extractTicket(resultSet));
            }
            if (!ticketList.isEmpty()) {
                for (Ticket t : ticketList) {
                    if (!Objects.equals(t.getStatus(), "refunded")) {
                        PreparedStatement preparedStatementUserBalance = connection.prepareStatement(UPDATE_USER_BALANCE);
                        preparedStatementUserBalance.setLong(1, 75L);
                        preparedStatementUserBalance.setLong(2, t.getUserId());
                        preparedStatementUserBalance.execute();
                    }
                    PreparedStatement preparedStatementTicketStatus = connection.prepareStatement(UPDATE_TICKET_STATUS);
                    preparedStatementTicketStatus.setString(1, "canceled");
                    preparedStatementTicketStatus.setLong(2, t.getId());
                    preparedStatementTicketStatus.execute();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            logger.error(e.getMessage(), e);
            throw new DBException();
        }

    }




    /**
     * Extracts Ticket objects from ResultSet object.
     *
     * @param resultSet <code>ResultSet</code> object to extract from.
     * @return Ticket object
     * @see Ticket,ResultSet
     */
    private Ticket extractTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setUserId(resultSet.getLong("userId"));
        ticket.setShowTimeId(resultSet.getLong("showTimeId"));
        ticket.setSeat(resultSet.getString("seat"));
        ticket.setStatus(resultSet.getString("status"));
        return ticket;
    }

    /**
     * Returns list of Showtime objects with dates within following month.
     *
     * @param firstDay <code>Date</code> object representing first day of the month to search with.
     * @return List of Showtime objects
     * @see Showtime
     */
    public List<Showtime> getShowtimesForMonth(Date firstDay) throws DBException {
        List<Showtime> showtimeList = new ArrayList<>();
        Date lastDay = Date.valueOf(firstDay.toLocalDate().plusMonths(1));
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIMES_FOR_MONTH)) {

            preparedStatement.setDate(1, firstDay);
            preparedStatement.setDate(2, lastDay);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                showtimeList.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return showtimeList;
    }

    /**
     * Returns list of Showtime objects with dates within next 7 days.
     *
     * @return List of Showtime objects
     * @see Showtime
     */
    public List<List<Showtime>> getShowtimesForWeek() throws DBException {
        List<List<Showtime>> weekList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        long dateLong = date.getTime();
        for (int i = 0; i < 7; i++) {
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIMES_FOR_WEEK)) {
                List<Showtime> showtimeList = new ArrayList<>();

                preparedStatement.setDate(1, new Date(dateLong));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    showtimeList.add(extract(resultSet));
                }
                showtimeList.sort((o1, o2) -> (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime()));
                weekList.add(showtimeList);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DBException();
            }
            dateLong = dateLong + 86_400_000;
        }
        return weekList;
    }

    /**
     * Updates status to "finished" of Showtimes which end time has passed.
     *
     * @see Showtime
     */
    public void updatePastShowtimeStatuses() throws DBException {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PAST_SHOWTIMES)) {

            preparedStatement.setDate(1, Date.valueOf(localDate));
            preparedStatement.setDate(2, Date.valueOf(localDate));
            preparedStatement.setTime(3, Time.valueOf(localTime));
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
    }

}
