package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new RuntimeException("db.properties not found");
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error with loading db.properties", e);
        }
    }

    private ConnectionManager() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }
}