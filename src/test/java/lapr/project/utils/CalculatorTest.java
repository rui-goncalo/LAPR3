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

    @Test
    public void testCalculateContainersPosition() {
        int nContainers = 10;
        double contHeight = 2.0, contLength = 1.0, contWidth = 2.0, s_length = 20.0, s_width = 10.0;
        ArrayList<Calculator.ContainerInfo> result = Calculator.calculateContainersPosition(nContainers, contHeight, contLength, contWidth, s_length, s_width);
        ArrayList<Calculator.ContainerInfo> resultNull = Calculator.calculateContainersPosition(300, contHeight, contLength, contWidth, s_length / 2, s_width / 2);
        ArrayList<Calculator.ContainerInfo> expResult = new ArrayList<Calculator.ContainerInfo>();
        ArrayList<Calculator.ContainerInfo> expResultNull = null;
        System.out.println(result);
        expResult.add(new Calculator.ContainerInfo("container1",0.5,1.0,0.0,1.0,0.0,2.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container2",19.5,9.0,19.0,20.0,10.0,8.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container3",1.5,1.0,1.0,2.0,0.0,2.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container4",18.5,9.0,18.0,19.0,10.0,8.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container5",2.5,1.0,2.0,3.0,0.0,2.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container6",17.5,9.0,17.0,18.0,10.0,8.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container7",3.5,1.0,3.0,4.0,0.0,2.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container8",16.5,9.0,16.0,17.0,10.0,8.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container9",4.5,1.0,4.0,5.0,0.0,2.0,0.0));
        expResult.add(new Calculator.ContainerInfo("container10",15.5,9.0,15.0,16.0,10.0,8.0,0.0));

        assertEquals(resultNull,expResultNull);
    }

    @Test
    public void testContainerInfo() {
        Calculator.ContainerInfo containerInfo = new Calculator.ContainerInfo("cont1", 2.0, 1.0, 0.0, 4.0, 0.0, 2.0, 0.0);
        assertEquals(containerInfo.getContainer(), "cont1");
        assertEquals(containerInfo.getXxCm(), 2.0);
        assertEquals(containerInfo.getYyCm(), 1.0);
        assertEquals(containerInfo.getHeight(), 0.0);
        assertEquals(containerInfo.getXxIn(), 0.0);
        assertEquals(containerInfo.getXxFin(), 4.0);
        assertEquals(containerInfo.getYyIn(), 0.0);
        assertEquals(containerInfo.getYyFin(), 2.0);
    }
}