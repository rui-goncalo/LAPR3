package lapr.project.data;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
public class LoadDBFiles {

    public static ArrayList<Ship> readShipDB() {
        ArrayList<Ship> shipArray = new ArrayList<>();
        Connection connection = MakeDBConnection.makeConnection();

        if(connection == null) {
            return null;
        }

        try (Statement stmtShip = connection.createStatement();
             Statement stmtDynamic = connection.createStatement()) {
            ResultSet rsShip = stmtShip.executeQuery("SELECT MMSI, NAME, IMO, NUMBER_ENERGY_GEN, GEN_POWER_OUTPUT, CALLSIGN, VESSEL, LENGTH, WIDTH, CAPACITY, DRAFT, TRANSCEIVER_CLASS, CODE FROM SHIP");
            while (rsShip.next()) {
                Ship ship = new Ship(rsShip.getInt(2), new ArrayList<>(), rsShip.getString(3), rsShip.getInt(4), rsShip.getString(5), rsShip.getInt(6), rsShip.getFloat(7), rsShip.getFloat(8), rsShip.getFloat(9), 0);
                ResultSet rsShipData = stmtDynamic.executeQuery("SELECT BASEDATETIME, LAT, LON, SOG, COG, HEADING, TRANSCEIVERCLASS FROM ShipDynamic WHERE SHIPID = " + rsShip.getInt(1));

                while (rsShipData.next()) {
                    ship.addDynamicShip(new ShipData(rsShipData.getTimestamp(1).toLocalDateTime(), rsShipData.getDouble(2), rsShipData.getDouble(3), rsShipData.getDouble(4), rsShipData.getFloat(5), rsShipData.getInt(6), rsShipData.getString(7).charAt(0)));
                }

                shipArray.add(ship);
            }
        } catch (SQLException e) {
            System.out.println("Access failed: " + e + ".");
            return null;
        }
        return shipArray;
    }
}
*/
