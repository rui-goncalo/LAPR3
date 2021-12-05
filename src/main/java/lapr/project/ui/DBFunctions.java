package lapr.project.ui;

import lapr.project.data.MakeDBConnection;
import lapr.project.model.*;
import lapr.project.ui.DateMenu;
import lapr.project.utils.Calculator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DBFunctions {

    private final static String GET_CURRENT_CONTAINER_INFO =
            "SELECT c.id AS \"Container ID\", cr.id AS \"Type Container\", tl.description AS \"Current Location\"\n" +
                    "FROM Container c, Container_Refrigerated cr, container_cargo_manifest ccm, Cargo_Manifest cm, Type_Cargo_Manifest tcm, Arrival a, Location l, type_location tl\n" +
                    "WHERE c.id = ?\n" +
                    "AND c.container_refrigeratedid = cr.id\n" +
                    "AND c.id = ccm.ContainerId_container\n" +
                    "AND ccm.Cargo_ManifestId_cargo_manifest = cm.id\n" +
                    "AND tcm.id = cm.Type_Cargo_ManifestId\n" +
                    "AND a.cargo_manifestid = cm.id\n" +
                    "AND a.initiallocationid = l.id\n" +
                    "AND l.type_locationid = tl.id";

    private final static String GET_CONTAINERS_OFFLOADED =
            "SELECT C.*, PC.CONTAINER_X, PC.CONTAINER_Y, PC.CONTAINER_Z, L.NAME, CN.NAME AS \"COUNTRY\", CR.TEMPERATURE\n" +
                    "FROM TRIP T INNER JOIN ARRIVAL A on T.ID = A.TRIPID INNER JOIN CARGO_MANIFEST CM on CM.ID = A.CARGO_MANIFESTID INNER JOIN TYPE_CARGO_MANIFEST TCM on TCM.ID = CM.TYPE_CARGO_MANIFESTID\n" +
                    "INNER JOIN CONTAINER_CARGO_MANIFEST CCM on CM.ID = CCM.CARGO_MANIFESTID_CARGO_MANIFEST INNER JOIN CONTAINER C on CCM.CONTAINERID_CONTAINER = C.ID\n" +
                    "INNER JOIN POS_CONTAINER PC ON PC.ID = CCM.POS_CONTAINERID\n" +
                    "INNER JOIN LOCATION L on L.ID = A.DESTINATIONLOCATIONID\n" +
                    "INNER JOIN COUNTRY CN ON CN.ID = L.COUNTRYID\n" +
                    "INNER JOIN CONTAINER_REFRIGERATED CR ON C.CONTAINER_REFRIGERATEDID = CR.ID\n" +
                    "WHERE T.SHIPMMSI = ? AND A.DESTINATIONLOCATIONID = ? AND TCM.DESIGNATION = 'unloading'\n" +
                    "ORDER BY A.ARRIVAL_DATE ASC\n";

    private final static String GET_SHIP_ARRIVAL_LOCATIONS =
            "SELECT L.*\n" +
                    "FROM LOCATION L\n" +
                    "INNER JOIN ARRIVAL A ON A.DESTINATIONLOCATIONID = L.ID\n" +
                    "INNER JOIN TRIP T ON A.TRIPID = T.ID\n" +
                    "WHERE T.SHIPMMSI = ? AND L.TYPE_LOCATIONID = 1";



    public static void getCurrentContainerInfo(int containerID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = MakeDBConnection.makeConnection();
            statement = connection.prepareStatement(GET_CURRENT_CONTAINER_INFO);
            statement.setString(1, String.valueOf(containerID));

            rs = statement.executeQuery();

            while (rs.next()) {

                System.out.print("Container ID: " + rs.getDouble("Container ID"));
                System.out.print(", Type Container: " + rs.getDouble("Type Container"));
                System.out.println(", Current Location: " + rs.getString("Current Location"));
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

    public static void getGetContainersOffloaded(Ship ship) {

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
                    nearestPort = nearestPort = rs.getInt("id");
                } else {
                    double distanceToPort = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                            rs.getDouble("latitude"), rs.getDouble("longitude"));
                    if (distanceToPort < distance) {
                        distance = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                                rs.getDouble("latitude"), rs.getDouble("longitude"));
                        nearestPort = rs.getInt("id");
                    }
                }
            }
            statement = connection.prepareStatement(GET_CONTAINERS_OFFLOADED);
            statement.setString(1, String.valueOf(ship.getMmsi()));
            statement.setString(2, String.valueOf(nearestPort));

            rs = statement.executeQuery();

            while (rs.next()) {

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
                if(rs.getInt("Container_RefrigeratedId") != 0) {
                    System.out.println(", Temperature: " + rs.getInt("Temperature"));
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


    public static void getGetContainersloaded(Ship ship) {

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
                    nearestPort = nearestPort = rs.getInt("id");
                } else {
                    double distanceToPort = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                            rs.getDouble("latitude"), rs.getDouble("longitude"));
                    if (distanceToPort < distance) {
                        distance = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                                rs.getDouble("latitude"), rs.getDouble("longitude"));
                        nearestPort = rs.getInt("id");
                    }
                }
            }
            statement = connection.prepareStatement(GET_CONTAINERS_OFFLOADED);
            statement.setString(1, String.valueOf(ship.getMmsi()));
            statement.setString(2, String.valueOf(nearestPort));

            rs = statement.executeQuery();

            while (rs.next()) {

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
                if(rs.getInt("Container_RefrigeratedId") != 0) {
                    System.out.println(", Temperature: " + rs.getInt("Temperature"));
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

    public static String us207(int dateCM) {
        return "SET SERVEROUTPUT ON;\n" +
                "DROP FUNCTION func_avg_cm_container;\n" +
                "CREATE OR REPLACE FUNCTION func_avg_cm_container(f_avg_c Cargo_Manifest.year%type)\n" +
                "RETURN VARCHAR\n" +
                "IS\n" +
                "    t_str VARCHAR(100);\n" +
                "    t_cm_year INTEGER;\n" +
                "    t_avg NUMBER;\n" +
                "BEGIN\n" +
                "    SELECT COUNT(*) into t_cm_year\n" +
                "    FROM cargo_manifest\n" +
                "    WHERE year = f_avg_c;\n" +
                "    SELECT AVG(COUNT(ccm.ContainerId_container)) AS \"Containers Per Manifest\" into t_avg\n" +
                "    FROM Container_Cargo_Manifest ccm\n" +
                "    INNER JOIN Cargo_Manifest cm ON cm.id = ccm.Cargo_ManifestId_cargo_manifest\n" +
                "    AND cm.year = f_avg_c\n" +
                "    GROUP BY ccm.Cargo_ManifestId_cargo_manifest;\n" +
                "        t_str := 'CM: ' || t_cm_year || '. AVG: ' || t_avg || '.';\n" +
                "    RETURN t_str;\n" +
                "END;\n" +
                "BEGIN\n" +
                "DBMS_OUTPUT.PUT_LINE(func_avg_cm_container("+dateCM+"));\n" +
                "END";
    }
}