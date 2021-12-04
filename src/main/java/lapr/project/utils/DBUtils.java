package lapr.project.utils;

import lapr.project.data.MakeDBConnection;
import lapr.project.model.Port;
import lapr.project.model.ShipCallSign;
import lapr.project.model.ShipData;
import lapr.project.model.ShipMMSI;
import lapr.project.ui.DateMenu;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DBUtils {

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
            "SELECT C.*, PC.*, L.NAME, CN.NAME\n" +
                    "FROM TRIP T INNER JOIN ARRIVAL A on T.ID = A.TRIPID INNER JOIN CARGO_MANIFEST CM on CM.ID = A.CARGO_MANIFESTID INNER JOIN TYPE_CARGO_MANIFEST TCM on TCM.ID = CM.TYPE_CARGO_MANIFESTID\n" +
                    "INNER JOIN CONTAINER_CARGO_MANIFEST CCM on CM.ID = CCM.CARGO_MANIFESTID_CARGO_MANIFEST INNER JOIN CONTAINER C on CCM.CONTAINERID_CONTAINER = C.ID\n" +
                    "INNER JOIN POS_CONTAINER PC ON PC.ID = CCM.POS_CONTAINERID\n" +
                    "INNER JOIN LOCATION L on L.ID = A.DESTINATIONLOCATIONID\n" +
                    "INNER JOIN COUNTRY CN ON CN.ID = L.COUNTRYID\n" +
                    "WHERE T.SHIPMMSI = ? AND A.DESTINATIONLOCATIONID = ? AND TCM.DESIGNATION = 'unloading'\n" +
                    "ORDER BY A.ARRIVAL_DATE ASC\n";

    private final static String GET_SHIP_ARRIVAL_LOCATIONS =
            "SELECT L.*\n" +
                    "FROM LOCATION L\n" +
                    "INNER JOIN ARRIVAL A ON A.DESTINATIONLOCATIONID = L.ID\n" +
                    "INNER JOIN TRIP T ON A.TRIPID = T.ID\n" +
                    "WHERE T.SHIPMMSI = ?";



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
                System.out.print(", Current Location: " + rs.getString("Current Location\n"));
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

    public static void getGetContainersOffloaded(ShipMMSI ship) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
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
                } else {
                    double distanceToPort = Calculator.getDistance(data.getLatitude(), data.getLongitude(),
                            rs.getDouble("latitude"), rs.getDouble("longitude"));
                    if (distanceToPort < distance) {
                        nearestPort = rs.getInt("id");
                    }
                }
            }
            statement = connection.prepareStatement(GET_CONTAINERS_OFFLOADED);
            statement.setString(1, String.valueOf(ship.getMmsi()));
            statement.setString(2, String.valueOf(nearestPort));

            rs = statement.executeQuery();

            while (rs.next()) {

                System.out.print("Container ID: " + rs.getDouble("Container ID"));
                System.out.print(", Type Container: " + rs.getDouble("Type Container"));
                System.out.print(", Current Location: " + rs.getString("Current Location\n"));
            }

        } catch (SQLException e) {
            System.out.println("Failed to access database: " + e);
        }


        //TODO: percorrer a lista de arrivals de um determinado navio, comparar as coordenadas,
        // ver qual é o porto que está mais próximo do navio e q tenha um manifesto de descarga

    }
}