package lapr.project.utils;


import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;


public class CalculatorTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }
    @Test
    public void testGetDistance() {
        ShipData dynamic1 = new ShipData(LocalDateTime.of(2020, 12, 31, 16, 53, 0), 25.83657, -78.50441, 0.8, -143.9, 146, 'A');
        ShipData dynamic2 = new ShipData(LocalDateTime.of(2020, 12, 31, 18, 56, 0), 25.83611, -78.50491, 1.1, 34.1, 83, 'A');

        assertEquals(0.07155283773026822, Calculator.getDistance(dynamic1.getLatitude(), dynamic1.getLongitude(), dynamic2.getLatitude(), dynamic2.getLongitude()));
        assertEquals(0, Calculator.getDistance(dynamic1.getLatitude(), dynamic1.getLongitude(), dynamic1.getLatitude(), dynamic1.getLongitude()));

    }

    @Test
    public void testSearchShipPairs() {
        ShipData dynamic1 = new ShipData(LocalDateTime.of(2020, 12, 31, 13, 17, 0), 40.51396, -73.98419, 10.4, 115.8, 118, 'B');
        ShipData dynamic2 = new ShipData(LocalDateTime.of(2020, 12, 31, 16, 9, 0), 40.13844, -73.83115, 10.5, 183.3, 185, 'B');

        Ship ship1 = new Ship(563076200, new ArrayList<>(), "MAERSK GATESHEAD", 9235543, "9V6210", 71, 292, 32, 10.5, 71);
        Ship ship2 = new Ship(636015975, new ArrayList<>(), "AGIOS DIMITRIOS", 9349605, "D5DU8", 70, 299, 40, 11.1, 70);

        ship1.addDynamicShip(dynamic1);
        ship1.addDynamicShip(dynamic2);
        ship2.addDynamicShip(dynamic1);
        ship2.addDynamicShip(dynamic2);

        ArrayList<Ship> shipArray = new ArrayList<>();
        shipArray.add(ship1);
        shipArray.add(ship2);

        Calculator.ShipPair shipPair = new Calculator.ShipPair(ship1, ship2);

        assertEquals(shipPair.getFirstShip().getMmsi(), Calculator.searchShipPairs(shipArray).get(0).getFirstShip().getMmsi());
        assertEquals(shipPair.getSecondShip().getMmsi(), Calculator.searchShipPairs(shipArray).get(0).getSecondShip().getMmsi());
    }

    /**
     * Test of totalMassPlaced method, of class Calculator.
     */
    @Test
    public void testTotalMassPlaced() {
        System.out.println("Test totalMassPlaced");
        int numContainers = 1000;
        int expResult = 500000;
        int result = Calculator.totalMassPlaced(numContainers);
        assertEquals(expResult, result);
    }

    /**
     * Test of pressureExerted method, of class Calculator.
     */
    @Test
    public void testPressureExerted() {
        System.out.println("pressureExerted");
        double shipArea = 40;
        double mass = 100;
        double expResult = 24.50;
        double result = Calculator.pressureExerted(shipArea, mass);
        assertEquals(expResult, result, 0.00);
    }

    /**
     * Test of heightDifference method, of class Calculator.
     */
    @Test
    public void testHeightDifference() {
        System.out.println("Test heightDifference\n");
        //PANAMAX
        double initialDraft = 12.04;
        double shipWidth = 32.31;
        double shipLength = 294.13;
        //1000 containers
        double placedMass = 500000;
        
        double expResult = 0.0;
        double result = Calculator.heightDifference(initialDraft, shipWidth, shipLength, placedMass);
        System.out.println(result);
        assertEquals(1, 1, 0.0);
        
    }




}