package database;

import java.sql.*;

public class DatabaseOperator {
    private Connection connection;
    private static DatabaseOperator instance;
    private DatabaseOperator() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pao","root","root");

        } catch (SQLException e) {
            System.out.println("Can't reach the database...");
            System.exit(1);
        }
    }
    public static DatabaseOperator getInstance() {
        if (instance == null) {
            instance = new DatabaseOperator();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
