package com.epam.dao;

import com.epam.dao.entity.Entity;
import com.epam.dao.entity.Ticket;
import com.epam.dao.exception.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TicketDAO implements DAO{
    private final ConnectionPool connectionPool;

    private static final Logger logger = LogManager.getLogger(TicketDAO.class);

    public TicketDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final String INSERT_TICKET = "INSERT INTO tickets VALUES (DEFAULT,?,?,?,'purchased')";
    private static final String GET_TICKET_BY_ID = "SELECT * FROM tickets WHERE id=?";
    private static final String GET_USERS_TICKETS = "SELECT * FROM tickets WHERE userId=?";
    private static final String GET_SHOWTIME_TICKETS = "SELECT * FROM tickets WHERE showTimeId=?";
    private static final String UPDATE_TICKET_STATUS = "UPDATE tickets SET status=? where id=?";
    private static final String UPDATE_SEAT_STATUS = "UPDATE seats SET status=? WHERE seat=? AND showtimeId=?";
    private static final String UPDATE_USER_BALANCE = "UPDATE users SET balance=balance+? WHERE id=?";


    @Override
    public Ticket findById(long id) throws DBException {
        Ticket ticket = new Ticket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TICKET_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticket = extract(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return ticket;
    }

    @Override
    public Ticket extract(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setUserId(resultSet.getLong("userId"));
        ticket.setShowTimeId(resultSet.getLong("showTimeId"));
        ticket.setSeat(resultSet.getString("seat"));
        ticket.setStatus(resultSet.getString("status"));
        return ticket;
    }

    @Override
    public void insert(Entity entity) throws DBException {
        Connection connection = null;
        try {
            Ticket ticket = (Ticket) entity;
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementForTicket = connection.prepareStatement(INSERT_TICKET);
            preparedStatementForTicket.setLong(1, ticket.getUserId());
            preparedStatementForTicket.setLong(2, ticket.getShowTimeId());
            preparedStatementForTicket.setString(3, ticket.getSeat());
            preparedStatementForTicket.execute();
            PreparedStatement preparedStatementForSeat = connection.prepareStatement(UPDATE_SEAT_STATUS);
            preparedStatementForSeat.setString(1, "occupied");
            preparedStatementForSeat.setString(2, ticket.getSeat());
            preparedStatementForSeat.setLong(3, ticket.getShowTimeId());
            preparedStatementForSeat.execute();
            PreparedStatement preparedStatementForUserBalance = connection.prepareStatement(UPDATE_USER_BALANCE);
            preparedStatementForUserBalance.setLong(1, -75L);
            preparedStatementForUserBalance.setLong(2, ticket.getUserId());
            preparedStatementForUserBalance.execute();
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


    public void refundTicket(Ticket ticket) throws DBException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementUserBalance = connection.prepareStatement(UPDATE_USER_BALANCE);
            preparedStatementUserBalance.setLong(1, 75L);
            preparedStatementUserBalance.setLong(2, ticket.getUserId());
            preparedStatementUserBalance.execute();
            PreparedStatement preparedStatementTicketStatus = connection.prepareStatement(UPDATE_TICKET_STATUS);
            preparedStatementTicketStatus.setString(1, "refunded");
            preparedStatementTicketStatus.setLong(2, ticket.getId());
            preparedStatementTicketStatus.execute();
            PreparedStatement preparedStatementForSeat = connection.prepareStatement(UPDATE_SEAT_STATUS);
            preparedStatementForSeat.setString(1, "vacant");
            preparedStatementForSeat.setString(2, ticket.getSeat());
            preparedStatementForSeat.setLong(3, ticket.getShowTimeId());
            preparedStatementForSeat.execute();
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
     * Returns list of Ticket objects for specified user.
     *
     * @param userId <code>User</code> object to search with.
     * @return List of Ticket objects
     * @see Ticket
     */
    public List<Ticket> getUserTickets(Long userId) throws DBException {
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_TICKETS)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ticketList.add(extract(resultSet));
            }

            ticketList.sort((o2, o1) -> {
                if (Objects.equals(o1.getStatus(), "purchased") && (Objects.equals(o2.getStatus(), "canceled") || Objects.equals(o2.getStatus(), "refunded"))) {
                    return 1;
                }
                if (Objects.equals(o1.getStatus(), "refunded") && Objects.equals(o2.getStatus(), "canceled")) {
                    return 1;
                }
                if (Objects.equals(o1.getStatus(), "refunded") && Objects.equals(o2.getStatus(), "purchased")) {
                    return -1;
                }
                if (Objects.equals(o1.getStatus(), "canceled") && (Objects.equals(o2.getStatus(), "refunded") || Objects.equals(o2.getStatus(), "purchased"))) {
                    return -1;
                }
                return (int) (o1.getId() - o2.getId());
            });

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return ticketList;
    }
}
