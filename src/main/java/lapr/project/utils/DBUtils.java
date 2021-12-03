package lapr.project.utils;

import lapr.project.data.MakeDBConnection;

import java.sql.*;

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


    //TODO: percorrer a lista de arrivals de um determinado navio, comparar as coordenadas,
    // ver qual é o porto que está mais próximo do navio e q tenha um manifesto de descarga
}