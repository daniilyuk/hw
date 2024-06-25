package ru.antonov.jdbc.simple;

import java.sql.*;

public class Application {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            createTable();
            insert();
            selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        // jdbc:sqlite:jdb.db
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hw7_database?user=postgres&password=post21");
        statement = connection.createStatement();
    }

    public static void createTable() throws SQLException {
        statement.executeUpdate(
                "" +
                        "create table if not exists users (" +
                        "    id          bigserial primary key," +
                        "    username    varchar(255)" +
                        ")"
        );
    }

    public static void insert() throws SQLException {
        for (int i = 1; i < 5; i++) {
            statement.executeUpdate("insert into users (username) values ('user" + i + "');");
        }
    }

    public static void selectAll() {
        try (ResultSet rs = statement.executeQuery("select * from users;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
