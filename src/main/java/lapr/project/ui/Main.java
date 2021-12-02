package lapr.project.ui;

import lapr.project.data.MakeDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Main {
    public static void main(String[] args) {
        Menu.mainMenu();

        Connection connection = MakeDBConnection.makeConnection();
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM CONTAINER";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.print("ID: " + rs.getDouble("id"));
                    System.out.print(", PAYLOAD: " + rs.getDouble("payload"));
                    System.out.print(", TARE: " + rs.getDouble("tare"));
                    System.out.print(", GROSS: " + rs.getDouble("gross"));
                    System.out.print(", ISO_CODE: " + rs.getString("iso_code"));
                    System.out.print(", CONTAINER_REFRIGERATED: " + rs.getDouble("container_refrigeratedid\n"));
                }
            } catch (SQLException e) {
                System.out.println("Failed to access database: " + e);
            }
        }
    }
}