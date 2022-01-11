package lapr.project.model;

import lapr.project.utils.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShipIMOTest {
    public ShipIMOTest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testShipIMO() {
        ArrayList<ShipData> shipArray1 = new ArrayList<>();
        Ship ship = new Ship(123456781, shipArray1, "first", 1234561, "callsign1", 1, 294.13, 32.31, 11.89, 10.0);

        ShipIMO shipIMO = new ShipIMO(ship);
        Ship expRes = new Ship(ship.getMmsi(), ship.getDynamicShip(), ship.getName(), ship.getImo(), ship.getCallSign(), ship.getVessel(), ship.getLength(), ship.getWidth(), ship.getDraft(), ship.getCargo());

        assertEquals(expRes.toString(), shipIMO.toString(), "should be equal");
    }

    @Test
    public void testShipIMO2() {
        ShipIMO shipIMO = new ShipIMO(1234567);
        Ship expRes = new Ship(0, null, null, 1234567, null, 0, 0, 0, 0, 0);

        assertEquals(expRes.toString(), shipIMO.toString(), "should be equal");
    }

    @Test
    public void testCompareTo() {
        ArrayList<ShipData> shipArray = new ArrayList<>();
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        ShipIMO o = new ShipIMO(ship);
        ShipIMO o2 = new ShipIMO(ship);

        assertEquals(0, o.compareTo(o2));
    }
}