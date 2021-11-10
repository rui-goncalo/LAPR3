package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {
    public ShipTest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetMmsi() {
        System.out.println("getMmsi()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 123456789;

        assertEquals(expRes, ship.getMmsi(), "Should be equal");
    }

    @Test
    public void testGetLength() {
        System.out.println("getLength()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 294.13;

        assertEquals(expRes, ship.getLength(), "Should be equal");
    }

    @Test
    public void testGetWidth() {
        System.out.println("getWidth()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 32.31;

        assertEquals(expRes, ship.getWidth(), "Should be equal");
    }

    @Test
    public void testGetDraft() {
        System.out.println("getDraft()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 11.89;

        assertEquals(expRes, ship.getDraft(), "Should be equal");
    }

    @Test
    public void testGetCargo() {
        System.out.println("getCargo()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 10.0;

        assertEquals(expRes, ship.getCargo(), "Should be equal");
    }

    @Test
    public void testGetImo() {
        System.out.println("getImo()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 1234567;

        assertEquals(expRes, ship.getImo(), "Should be equal");
    }

    @Test
    public void testGetCallSign() {
        System.out.println("getCallSign()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        String expRes = "callsign";

        assertEquals(expRes, ship.getCallSign(), "Should be equal");
    }

    @Test
    public void testGetName() {
        System.out.println("getName()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        String expRes = "Primeiro";

        assertEquals(expRes, ship.getName(), "Should be equal");
    }

    @Test
    public void testGetVessel() {
        System.out.println("getVessel()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 1;

        assertEquals(expRes, ship.getVessel(), "Should be equal");
    }

    @Test
    public void testToString() {
        System.out.println("toString()");
        ArrayList<ShipData> shipArray = new ArrayList<>();

        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        String expRes = "Ship{" +
                "mmsi=" + ship.getMmsi() +
                ", dynamicShip=" + ship.getDynamicShip() +
                ", name='" + ship.getName() + '\'' +
                ", imo=" + ship.getImo() +
                ", callSign='" + ship.getCallSign() + '\'' +
                ", vessel=" + ship.getVessel() +
                ", length=" + ship.getLength() +
                ", width=" + ship.getWidth() +
                ", draft=" + ship.getDraft() +
                ", cargo=" + ship.getCargo() +
                '}';

        assertEquals(expRes, ship.toString(), "should be equal");
    }

    @Test
    public void testfilterShipData() {
        System.out.println("filterShipData()");
        ArrayList<ShipData> filteredShipData = new ArrayList<>();

        LocalDateTime date1 = LocalDateTime.of(2020, 1, 31, 01, 25);
        LocalDateTime date2 = LocalDateTime.of(2020, 5, 31, 16, 15);
        LocalDateTime date3 = LocalDateTime.of(2020, 9, 30, 18, 03);
        LocalDateTime date4 = LocalDateTime.of(2020, 10, 31, 00, 39);
        LocalDateTime date5 = LocalDateTime.of(2020, 12, 31, 17, 02);

        ShipData shipd1 = new ShipData(date1, 123.45, 321.04, 15.4, 90.0, 40.0, 'B');
        ShipData shipd2 = new ShipData(date2, 132.45, 322.88, 10.4, 51.0, 42.0, 'A');
        ShipData shipd3 = new ShipData(date3, 124.45, 371.74, 11.3, 49.0, 32.0, 'B');
        ShipData shipd4 = new ShipData(date4, 163.45, 331.56, 15.9, 42.0, 43.0, 'A');
        ShipData shipd5 = new ShipData(date5, 723.45, 312.44, 11.1, 40.0, 63.0, 'A');

        filteredShipData.add(shipd1);
        filteredShipData.add(shipd2);
        filteredShipData.add(shipd3);
        filteredShipData.add(shipd4);
        filteredShipData.add(shipd5);

        //ExpRes
        ArrayList<ShipData> filteredData = new ArrayList<>();
        filteredData.add(shipd2);

        ArrayList<ShipData> shipArray = new ArrayList<>();
        shipArray.add(shipd1);
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        //Inicio: 1 de Janeiro 2020 - FIM: 1 de Fevereiro 2020
        assertEquals(filteredData, ship.filterShipData(LocalDateTime.of(2020, 1, 1, 01, 25), LocalDateTime.of(2020, 2, 1, 01, 25)), "Should be equal");
    }
}
