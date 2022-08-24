package DB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;
    DataSource dataSource;

    private ConnectionPool() {
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

    public static ConnectionPool getInstance() {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }


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
