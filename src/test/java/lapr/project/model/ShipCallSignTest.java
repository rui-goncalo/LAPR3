package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipCallSignTest {
    public ShipCallSignTest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testShipCallSign() {
        System.out.println("shipCallSign()");
        ArrayList<ShipData> shipArray = new ArrayList<>();
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        ShipCallSign shipCallSign = new ShipCallSign(ship);
        Ship expRes = new Ship(ship.getMmsi(), ship.getDynamicShip(), ship.getName(), ship.getImo(), ship.getCallSign(), ship.getVessel(), ship.getLength(), ship.getWidth(), ship.getDraft(), ship.getCargo());

        assertEquals(expRes.toString(), shipCallSign.toString(), "should be equal");
    }

    @Test
    public void testShipCallSign2() {
        System.out.println("shipCallSign2()");
        ShipCallSign shipCallSign = new ShipCallSign("callSign");
        Ship expRes = new Ship(0, null, null, 0, "callSign", 0, 0, 0, 0, 0);

        assertEquals(expRes.toString(), shipCallSign.toString(), "should be equal");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo()");
        ArrayList<ShipData> shipArray = new ArrayList<>();
        Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 10.0);

        ShipCallSign o = new ShipCallSign(ship);
        ShipCallSign o2 = new ShipCallSign(ship);

        assertEquals(0, o.compareTo(o2));
    }
}
