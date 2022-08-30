package com.epam.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool implementation .
 *
 * @author Apache
 * @version 1.0
 */
public class ConnectionPool {
//    private static ConnectionPool instance;
    private DataSource dataSource;

    public ConnectionPool() {
        Context context;

        DataSource dataSource = null;
        this.dataSource = null;
        try {
            context = new InitialContext();
            Context envContext = (Context) context.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/cinema");

        } catch (NamingException e) {
            e.printStackTrace();
        }
        this.dataSource = dataSource;

    }

    /**
     * Returns existing ConnectionPool instance or creates one if none exist.
     *
     * @return <code>ConnectionPool</code> object
     */
 /*   public static ConnectionPool getInstance() {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }*/

    /**
     * Returns connection established with data source.
     *
     * @return <code>Connection</code> object
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
