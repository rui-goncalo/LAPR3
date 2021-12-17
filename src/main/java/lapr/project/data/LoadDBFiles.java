package lapr.project.data;

import lapr.project.model.Port;
import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class LoadDBFiles {

    public static ArrayList<Ship> readShipDB() {
        ArrayList<Ship> shipArray = new ArrayList<>();
        Connection connection = MakeDBConnection.makeConnection();

        if (connection == null) {
            return null;
        }
        try (Statement stmtShip = connection.createStatement();
             Statement stmtShipData = connection.createStatement()) {
            ResultSet rsShip = stmtShip.executeQuery("SELECT MMSI, NAME, IMO, NUMBER_ENERGY_GEN, GEN_POWER_OUTPUT, CALLSIGN, VESSEL, LENGTH, WIDTH, CAPACITY, DRAFT, TRANSCEIVER_CLASS, CODE FROM SHIP");
            while (rsShip.next()) {
                Ship ship = new Ship(
                        rsShip.getInt("MMSI"),
                        new ArrayList<>(),
                        rsShip.getString("NAME"),
                        rsShip.getInt("IMO"),
                        rsShip.getString("CALLSIGN"),
                        rsShip.getInt("VESSEL"),
                        rsShip.getDouble("LENGTH"),
                        rsShip.getDouble("WIDTH"),
                        rsShip.getDouble("DRAFT"),
                        0);
                ResultSet rsShipData = stmtShipData.executeQuery(
                        "SELECT M.DATE_T, M.SOG, M.COG, M.HEADING, L.LATITUDE, L.LONGITUDE, S.TRANSCEIVER_CLASS\n" +
                                "FROM LOCATION L INNER JOIN MESSAGE M on L.ID = M.LOCATIONID\n" +
                                "                INNER JOIN SHIP S ON S.MMSI = M.SHIPMMSI " +
                                "WHERE S.MMSI = " + rsShip.getInt(1));

                while (rsShipData.next()) {
                    ship.addDynamicShip(new ShipData(
                            rsShipData.getTimestamp("DATE_T").toLocalDateTime(),
                            rsShipData.getDouble("LATITUDE"),
                            rsShipData.getDouble("LONGITUDE"),
                            rsShipData.getInt("SOG"),
                            rsShipData.getDouble("COG"),
                            rsShipData.getDouble("HEADING"),
                            rsShipData.getString("TRANSCEIVER_CLASS").charAt(0)));
                }
                shipArray.add(ship);
            }
        } catch (SQLException e) {
            System.out.println("Access failed: " + e + ".");
            return null;
        }
        return shipArray;
    }

    public static ArrayList<Port> readPortDB() {
        ArrayList<Port> portArray = new ArrayList<>();
        Connection connection = MakeDBConnection.makeConnection();

        if (connection == null) {
            return null;
        }
        try (Statement stmtPort = connection.createStatement()) {
            ResultSet rsPort = stmtPort.executeQuery(
                    "SELECT CNT.NAME AS CONTINENT_NAME, CNTRY.NAME AS COUNTRY_NAME, L.ID, L.NAME AS PORT_NAME, L.LATITUDE, L.LONGITUDE, TL.DESCRIPTION\n" +
                            "FROM COUNTRY CNTRY INNER JOIN CONTINENT CNT on CNTRY.CONTINENTID = CNT.ID\n" +
                            "INNER JOIN LOCATION L ON L.COUNTRYID = CNTRY.ID\n" +
                            "INNER JOIN TYPE_LOCATION TL ON L.TYPE_LOCATIONID = TL.ID\n" +
                            "WHERE TL.ID = 1");

            while (rsPort.next()) {
                Port port = new Port(
                        rsPort.getString("CONTINENT_NAME"),
                        rsPort.getString("COUNTRY_NAME"),
                        rsPort.getInt("ID"),
                        rsPort.getString("PORT_NAME"),
                        rsPort.getDouble("LATITUDE"),
                        rsPort.getDouble("LONGITUDE"));

                portArray.add(port);
            }
        }catch (SQLException e) {
            System.out.println("Access failed: " + e + ".");
            return null;
        }
        return portArray;
    }
}

