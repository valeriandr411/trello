package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCPostgreSQL {
    private JDBCPostgreSQL() {
    }

    public static String getPassword(String login) {
        final String sql = String.format("SELECT * FROM users WHERE login = '%s'", login);
        String password;

        try (Connection connection = ConnectionManager.connect();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            result.next();
            password = result.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return password;
    }

    public static String getToken(String login) {
        final String sql = String.format("SELECT * FROM users WHERE login = '%s'", login);
        String token;

        try (Connection connection = ConnectionManager.connect();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            result.next();
            token = result.getString("token");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    public static String getKey(String login) {
        final String sql = String.format("SELECT * FROM users WHERE login = '%s'", login);
        String key;

        try (Connection connection = ConnectionManager.connect();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            result.next();
            key = result.getString("key");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}