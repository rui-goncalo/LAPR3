package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SumaryTest {

    ArrayList<ShipData> dynamicShip = new ArrayList<>();

    LocalDateTime time1 = LocalDateTime.of(2020,12,31,23,28,00);
    LocalDateTime time2 = LocalDateTime.of(2020, 12, 31, 23, 31, 00);

    ShipData shipData1 = new ShipData(time1, 54.23188,
            -130.33667, 0.1, 82.8,0, 'A');
    ShipData shipData2 = new ShipData(time2, 54.23184,
            -130.33702, 0.1, 34.6,0, 'A');

    Ship ship = new Ship(229961000, dynamicShip, "ARABELLA", 9700122, "9HA3752", 70,
            199, 32, 14.4, 0);

    @Test
    public void createSumary() {
        ship.addDynamicShip(shipData1);
        ship.addDynamicShip(shipData2);
        ArrayList<Object> sumary = Sumary.createSumary(ship,"MMSI");
        ArrayList<Object> sumaryIMO = Sumary.createSumary(ship,"IMO");
        ArrayList<Object> sumaryCallSign = Sumary.createSumary(ship,"CallSign");
        ArrayList<Object> expectRes = new ArrayList<>();
        expectRes.add(229961000);
        expectRes.add("ARABELLA");
        expectRes.add(70);
        expectRes.add(time1);
        expectRes.add(time2);
        expectRes.add(1);
        expectRes.add(2);
        expectRes.add(0.1);
        expectRes.add(0.1);
        expectRes.add(82.8);
        expectRes.add(58.7);
        expectRes.add(54.23188);
        expectRes.add(-130.33667);
        expectRes.add(54.23184);
        expectRes.add(-130.33702);
        expectRes.add(Calculator.totalDistance(ship.getDynamicShip()));
        expectRes.add(Calculator.distanceBetween(54.23188, -130.33667, 54.23184, -130.33702));

//        for (Object o : sumary) {
//            System.out.print(" | " + o);
//        }
//        System.out.printf("\n");
//        for (Object o : expectRes) {
//            System.out.print(" | " + o);
//        }
        assertEquals(expectRes, sumary);
    }

}