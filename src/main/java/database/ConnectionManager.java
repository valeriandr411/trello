package database;

import utils.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String DB_URL = "db.url";
    private static final String USER = "db.user";
    private static final String PASS = "db.password";

    private ConnectionManager() {
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(DB_URL),
                    PropertiesUtil.get(USER),
                    PropertiesUtil.get(PASS)
            );
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
