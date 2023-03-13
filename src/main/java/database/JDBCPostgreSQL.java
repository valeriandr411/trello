package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCPostgreSQL {
    private final static String STATEMENT = "SELECT * FROM users WHERE login = '%s'";

    private JDBCPostgreSQL() {
    }

    /**
     * Метод, делающий запрос к БД и возвращающий пароль пользователя
     *
     * @param login логин пользователя
     * @return пароль пользователя
     */
    public static String getPassword(String login) {
        final String sql = String.format(STATEMENT, login);
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

    /**
     * Метод, делающий запрос к БД и возвращающий токен пользователя
     *
     * @param login логин пользователя
     * @return токен пользователя
     */
    public static String getToken(String login) {
        final String sql = String.format(STATEMENT, login);
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

    /**
     * Метод, делающий запрос к БД и возвращающий ключ пользователя
     *
     * @param login логин пользователя
     * @return ключ пользователя
     */
    public static String getKey(String login) {
        final String sql = String.format(STATEMENT, login);
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