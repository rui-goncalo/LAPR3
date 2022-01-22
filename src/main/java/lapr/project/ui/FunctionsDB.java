package lapr.project.ui;

import lapr.project.data.MakeDBConnection;
import lapr.project.model.*;

import lapr.project.utils.Calculator;
import java.sql.*;
public class FunctionsDB {

    private final static String GET_CONTAINERS_NEXT_PORT =
            "SELECT C.*, PC.CONTAINER_X, PC.CONTAINER_Y, PC.CONTAINER_Z, L.NAME, CN.NAME AS \"COUNTRY\", CR.TEMPERATURE\n" +
                    "FROM TRIP T INNER JOIN ARRIVAL A on T.ID = A.TRIPID INNER JOIN CARGO_MANIFEST CM on CM.ID = A.CARGO_MANIFESTID INNER JOIN TYPE_CARGO_MANIFEST TCM on TCM.ID = CM.TYPE_CARGO_MANIFESTID\n" +
                    "INNER JOIN CONTAINER_CARGO_MANIFEST CCM on CM.ID = CCM.CARGO_MANIFESTID_CARGO_MANIFEST INNER JOIN CONTAINER C on CCM.CONTAINERID_CONTAINER = C.ID\n" +
                    "INNER JOIN POS_CONTAINER PC ON PC.ID = CCM.POS_CONTAINERID\n" +
                    "INNER JOIN LOCATION L on L.ID = A.DESTINATIONLOCATIONID\n" +
                    "INNER JOIN COUNTRY CN ON CN.ID = L.COUNTRYID\n" +
                    "INNER JOIN CONTAINER_REFRIGERATED CR ON C.CONTAINER_REFRIGERATEDID = CR.ID\n" +
                    "WHERE T.SHIPMMSI = ? AND A.DESTINATIONLOCATIONID = ? AND TCM.DESIGNATION = ?\n" +
                    "ORDER BY A.ARRIVAL_DATE ASC\n";

    private final static String GET_SHIP_ARRIVAL_LOCATIONS =
            "SELECT L.*\n" +
                    "FROM LOCATION L\n" +
                    "INNER JOIN ARRIVAL A ON A.DESTINATIONLOCATIONID = L.ID\n" +
                    "INNER JOIN TRIP T ON A.TRIPID = T.ID\n" +
                    "WHERE T.SHIPMMSI = ?";

    private final static String GET_SHIPS_AVAILABLE_MONDAY = "SELECT s.mmsi AS \"Available Ships\", t.final_date AS \"Finished Trip\"\n" +
            "FROM Ship s\n" +
            "         INNER JOIN Cargo_Manifest cm ON s.mmsi = cm.shipmmsi\n" +
            "         INNER JOIN Type_Cargo_Manifest tcm ON cm.type_cargo_manifestId = tcm.id\n" +
            "         INNER JOIN Arrival a ON a.cargo_manifestId = cm.id\n" +
            "         INNER JOIN Trip t ON t.id = a.tripId\n" +
            "    AND tcm.id = 2\n" +
            "    AND t.final_date < TRUNC(sysdate, 'DAY')+8";

    //private final static String GET_OCCUPANCY_RATE_WAREHOUSE = ""

    public static void getGetContainersNextPort(Ship ship, String loading) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        try {
            connection = MakeDBConnection.makeConnection();
            statement = connection.prepareStatement(GET_SHIP_ARRIVAL_LOCATIONS);
            statement.setString(1, String.valueOf(ship.getMmsi()));
            rs = statement.executeQuery();

            int nearestPort = 0;
            double distance = 0.0;
            ShipData data = ship.getLastDynamicData();

            while (rs.next()) {
                if (distance == 0.0) {
                    distance = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                            rs.getDouble("latitude"), rs.getDouble("longitude"));
                    nearestPort = rs.getInt("id");
                } else {
                    double distanceToPort = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                            rs.getDouble("latitude"), rs.getDouble("longitude"));
                    if (distanceToPort < distance) {
                        distance = distanceToPort;
                        nearestPort = rs.getInt("id");
                    }
                }
            }
            System.out.println("Nearest Port: " + nearestPort);

            statement = connection.prepareStatement(GET_CONTAINERS_NEXT_PORT);
            statement.setString(1, String.valueOf(ship.getMmsi()));
            statement.setString(2, String.valueOf(nearestPort));
            statement.setString(3, String.valueOf(loading));

            rs = statement.executeQuery();


            while (rs.next()) {
                if (loading.equals("loading")) {
                    System.out.print("Container ID: " + rs.getInt("ID"));
                    System.out.print(", Payload: " + rs.getDouble("Payload"));
                    System.out.print(", Tare: " + rs.getString("Tare"));
                    System.out.print(", Gross: " + rs.getDouble("Gross"));
                    System.out.print(", ISO Code: " + rs.getInt("ISO_Code"));
                    System.out.print(", Port Name: " + rs.getString("name"));
                    System.out.print(", Country: " + rs.getString("country"));
                    if (rs.getInt("Container_RefrigeratedId") != 0) {
                        System.out.println(", Temperature: " + rs.getInt("Temperature"));
                    }
                } else {
                    System.out.print("Container ID: " + rs.getInt("ID"));
                    System.out.print(", Payload: " + rs.getDouble("Payload"));
                    System.out.print(", Tare: " + rs.getString("Tare"));
                    System.out.print(", Gross: " + rs.getDouble("Gross"));
                    System.out.print(", ISO Code: " + rs.getInt("ISO_Code"));
                    System.out.print(", Container Position X: " + rs.getInt("Container_x"));
                    System.out.print(", Container Position Y: " + rs.getInt("Container_y"));
                    System.out.print(", Container Position Z: " + rs.getInt("Container_z"));
                    System.out.print(", Port Name: " + rs.getString("name"));
                    System.out.print(", Country: " + rs.getString("country"));
                    if (rs.getInt("Container_RefrigeratedId") != 0) {
                        System.out.println(", Temperature: " + rs.getInt("Temperature"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to access database: " + e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to access database: " + e);
            }
        }
    }


    public static void shipsAvailableMonday() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = MakeDBConnection.makeConnection();
            statement = connection.prepareStatement(GET_SHIPS_AVAILABLE_MONDAY);

            rs = statement.executeQuery();

            while (rs.next()) {
                System.out.print("Ship MMSI: " + rs.getInt("Available Ships") + " ");
                System.out.print("Finished Trip: " + rs.getString("Finished Trip") + "\n");
            }
        } catch (
                SQLException e) {
            System.out.println("Failed to access database: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to access database: " + e);
            }
        }
    }

}