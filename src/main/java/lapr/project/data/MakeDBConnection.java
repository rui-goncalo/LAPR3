package lapr.project.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author Rui Gonçalves - 119183311
 */
public class MakeDBConnection {

    /**
     * Creates a connection to a database
     *
     * @return Connection object
     */
    public static Connection makeConnection() {
        try (InputStream inputStream = MakeDBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties(System.getProperties());
            properties.load(inputStream);
            System.setProperties(properties);
        } catch (IOException e) {
            System.out.println("Failed initialize inputStream for application.properties: " + e);
        }

        String url = System.getProperty("database.url");
        String username = System.getProperty("database.username");
        String password = System.getProperty("database.password");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e);
            return null;
        }
    }
}
