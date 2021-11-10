package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 123456789;

        assertEquals(expRes, ship.getMmsi(), "Should be equal");
    }

    @Test
    public void testGetLength() {
        System.out.println("getLength()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 294.13;

        assertEquals(expRes, ship.getLength(), "Should be equal");
    }

    @Test
    public void testGetWidth() {
        System.out.println("getWidth()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 32.31;

        assertEquals(expRes, ship.getWidth(), "Should be equal");
    }

    @Test
    public void testGetDraft() {
        System.out.println("getDraft()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 11.89;

        assertEquals(expRes, ship.getDraft(), "Should be equal");
    }

    @Test
    public void testGetCargo() {
        System.out.println("getCargo()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        double expRes = 10.0;

        assertEquals(expRes, ship.getCargo(), "Should be equal");
    }

    @Test
    public void testGetImo() {
        System.out.println("getImo()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 1234567;

        assertEquals(expRes, ship.getImo(), "Should be equal");
    }

    @Test
    public void testGetCallSign() {
        System.out.println("getCallSign()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        String expRes = "callsign";

        assertEquals(expRes, ship.getCallSign(), "Should be equal");
    }

    @Test
    public void testGetName() {
        System.out.println("getName()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        String expRes = "Primeiro";

        assertEquals(expRes, ship.getName(), "Should be equal");
    }

    @Test
    public void testGetVessel() {
        System.out.println("getCallSign()");

        Ship ship = new Ship(123456789, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);
        int expRes = 1;

        assertEquals(expRes, ship.getVessel(), "Should be equal");
    }

    @Test
    public void testfilterShipData() {
    }
}
