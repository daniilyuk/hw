package pattern;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:test";
        String user = "sa";
        String password = "";

        try (Connection originalConnection = DriverManager.getConnection(url, user, password)) {
            TransactionConnection connection = new ConnectionProxy(originalConnection);

            try {
                connection.beginTransaction();

                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS products (id INT PRIMARY KEY, name VARCHAR(255), price DECIMAL(10,2))");
                statement.executeUpdate("INSERT INTO products (id, name, price) VALUES (1, 'A', 100)");

                connection.commitTransaction();

            } catch (SQLException e) {
                connection.rollbackTransaction();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}