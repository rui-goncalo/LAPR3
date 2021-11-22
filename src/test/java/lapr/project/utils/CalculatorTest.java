package lapr.project.utils;


import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    public void testGetDistance() {
        ShipData dynamic1 = new ShipData(LocalDateTime.of(2020, 12, 31, 16, 53, 0), 25.83657f, -78.50441f, 0.8f, -143.9f, 146, 'A');
        ShipData dynamic2 = new ShipData(LocalDateTime.of(2020, 12, 31, 18, 56, 0), 25.83611f, -78.50491f, 1.1f, 34.1f, 83, 'A');

        assertEquals(0.07192612f, Calculator.getDistance(dynamic1.getLatitude(), dynamic1.getLongitude(), dynamic2.getLatitude(), dynamic2.getLongitude()));
        assertEquals(0, Calculator.getDistance(dynamic1.getLatitude(), dynamic1.getLongitude(), dynamic1.getLatitude(), dynamic1.getLongitude()));
    }

    @Test
    public void testSearchShipPairs() {
        ShipData dynamic1 = new ShipData(LocalDateTime.of(2020, 12, 31, 13, 17, 0), 40.51396f, -73.98419f, 10.4f, 115.8f, 118, 'B');
        ShipData dynamic2 = new ShipData(LocalDateTime.of(2020, 12, 31, 16, 9, 0), 40.13844f, -73.83115f, 10.5f, 183.3f, 185, 'B');

        Ship ship1 = new Ship(563076200, new ArrayList<>(), "MAERSK GATESHEAD", 9235543, "9V6210", 71, 292, 32, 10.5f, 71);
        Ship ship2 = new Ship(636015975, new ArrayList<>(), "AGIOS DIMITRIOS", 9349605, "D5DU8", 70, 299, 40, 11.1f, 70);

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
}