package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipMMSITest {
    public ShipMMSITest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testShipMMSI() {
        System.out.println("ShipMMSI()");
        ArrayList<ShipData> shipArray = new ArrayList<>();
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        ShipMMSI ShipMMSI = new ShipMMSI(ship);
        Ship expRes = new Ship(ship.getMmsi(), ship.getDynamicShip(), ship.getName(), ship.getImo(), ship.getCallSign(), ship.getVessel(), ship.getLength(), ship.getWidth(), ship.getDraft(), ship.getCargo());

        assertEquals(expRes.toString(), ShipMMSI.toString());
        //assertEquals(expRes.toString(), ShipMMSI.toString(), "should be equal");
    }

    @Test
    public void testShipMMSI2() {
        System.out.println("ShipMMSI2()");
        ShipMMSI ShipMMSI = new ShipMMSI(1234567);
        Ship expRes = new Ship(1234567, null, null, 0, null, 0, 0, 0, 0, 0);

        assertEquals(expRes.toString(), ShipMMSI.toString(), "should be equal");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo()");
        ArrayList<ShipData> shipArray = new ArrayList<>();
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        Ship ship1 = new Ship(123456788, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        ShipMMSI o1 = new ShipMMSI(ship);
        ShipMMSI o2 = new ShipMMSI(ship);
        assertEquals(0, o1.compareTo(o2), "should be equal");

        ShipMMSI o3 = new ShipMMSI(ship);
        ShipMMSI o4 = new ShipMMSI(ship1);
        assertEquals(1, o3.compareTo(o4), "should be 1");
        assertEquals(-1, o4.compareTo(o3), "should be -1");
    }
}
