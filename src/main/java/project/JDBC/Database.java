package project.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String URL  = "jdbc:mysql://localhost:3306/keystores?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "2991";

    private Database() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
