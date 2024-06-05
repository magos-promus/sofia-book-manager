package sofia.book.manager;

import java.sql.*;

/**
 * Utility class for database connections.
 */
public class DatabaseConnection {

    /**
     * Private constructor to prevent instantiation.
     */
    private DatabaseConnection() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Establishes a connection to the database.
     *
     * @param password The password for the database connection.
     * @return A Connection object.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection(String password) throws SQLException {
        String url = "jdbc:mariadb://localhost/sofiadb";
        String user = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
