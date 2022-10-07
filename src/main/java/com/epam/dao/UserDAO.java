package com.epam.dao;

import com.epam.dao.entity.Entity;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDAO implements DAO{
    private final ConnectionPool connectionPool;

    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    public UserDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final String GET_USER_BY = "SELECT * FROM users WHERE ";
    private static final String INSERT_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,md5(?),'client',0)";
    private static final String UPDATE_USER_BALANCE = "UPDATE users SET balance=balance+? WHERE id=?";

    /**
     * Returns User object based on SQL statement and search parameter.
     *
     * @param statement       SQL statement to use in search
     * @param searchParameter parameter with which to identify a user
     * @return <code>User</code> object
     * @see User
     */
    private User getUser(String searchParameter, String statement) throws DBException {
        User user = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, searchParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DBException();
        }
        return user;
    }

    public User findUserById(Long id) throws DBException {
        User user = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY + "id=?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DBException();
        }
        return user;
    }

    /**
     * Returns User object based on with a specified email.
     *
     * @param email email to search by
     * @return <code>User</code> object
     * @see User
     */
    public User findUserByEMail(String email) throws DBException {
        return getUser(email, GET_USER_BY + "email=?");
    }

    /**
     * Returns User object based on with a specified phone number.
     *
     * @param phoneNumber phone number to search by
     * @return <code>User</code> object
     * @see User
     */
    public User findUserByPhoneNumber(String phoneNumber) throws DBException {
        return getUser(phoneNumber, GET_USER_BY + "phone_number=?");
    }

    /**
     * Returns User object based on with a specified login.
     *
     * @param login login to search by
     * @return <code>User</code> object
     * @see User
     */
    public User findUserByLogin(String login) throws DBException {
        return getUser(login, GET_USER_BY + "login=?");
    }


    public Long getUserBalance(Long userId) throws DBException {
        Long balance = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY + "id=?")) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getLong("balance");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
        return balance;
    }

    /**
     * Extracts User object from ResultSet object.
     *
     * @param resultSet ResultSet object to extract User from
     * @return <code>User</code> object
     * @see User
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
        user.setBalance(resultSet.getLong("balance"));
        return user;
    }

    public void updateUserBalance(Long id, Long balance) throws DBException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BALANCE)) {
            preparedStatement.setLong(1, balance);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }
    }


    @Override
    public User findById(long id) throws DBException {
        User user = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY + "id=?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DBException();
        }
        return user;
    }

    @Override
    public User extract(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        user.setBalance(resultSet.getLong("balance"));
        return user;
    }

    @Override
    public void insert(Entity entity) throws DBException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

            User user = (User) entity;
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.execute();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DBException();
        }

    }
}
