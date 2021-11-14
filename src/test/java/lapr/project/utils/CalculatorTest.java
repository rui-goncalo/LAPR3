package lapr.project.utils;

import java.time.LocalDateTime;
import lapr.project.model.ShipData;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class CalculatorTest {
    
    public CalculatorTest() {
    }

    
    /**
     * Test of distanceBetween method, of class Calculator.
     */

    @Test
    public void testDistanceBetween() {
        System.out.println("distanceBetween");
        
//      TEST1
        double lat1 = 0;
        double lon1 = 0;
        double lat2 = 10;
        double lon2 = 10;
        
        double expResult = 1569;   

//      TEST2
//        double lat1 = 20.5;
//        double lon1 = -32;
//        double lat2 = 60.231;
//        double lon2 = -40.31;
//        double expResult = 4466;

//      TEST3
//        double lat1 = 91;
//        double lon1 = 181;
//        double lat2 = 0;
//        double lon2 = 0;
//        double expResult = null;

        double result = Math.round((Calculator.distanceBetween(lat1, lon1, lat2, lon2))/1000);
        assertEquals(expResult, result, 0.0);
    }

    
    /**
     * Test of totalDistance method, of class Calculator.
     */
    @Test
    public void testTotalDistance() {
        System.out.println("totalDistance");
        
//      TEST1
        ArrayList<ShipData> shipDataList = new ArrayList<>();
        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  24.34573, -85.12394, 9,    119.9, 3,   'B'));
        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 1, 5,  0),  24.7,     -85,       8,    116.6, 4,   'B'));
        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 1, 10, 0),  24.8,     -84.56,    8,    120.8, 10,  'B'));
        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 1, 15, 0),  24.9,     -84.3,     3,    116.8, 20,  'B'));
        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 1, 20, 0),  25,       -84.2,     10,   113.3, 1,   'B'));
        double result = Math.round(Calculator.totalDistance(shipDataList)/1000);        
        double expResult = Math.round(41.34 + 45.8 + 28.49 + 15.01);
        
 //      TEST2
//        ArrayList<ShipData> shipDataList = null;
//        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 0, 0,  0),  10,      10,       9,    119.9, 3,   'B'));
//        shipDataList.add(new ShipData(LocalDateTime.of(2020, 1, 0, 5,  0),  10,      40,       8,    116.6, 4,   'B'));
//        double result = Calculator.totalDistance(shipDataList)/100;        
//        double expResult = 3284;

        assertEquals(expResult, result, 0);
    }

    /**
     * Test of convertToDateViaInstant method, of class Calculator.
     */
    @Test
    public void testConvertToDateViaInstant() {
        System.out.println("convertToDateViaInstant");
        LocalDateTime dateToConvert = LocalDateTime.of(2021, 11, 14, 0, 0);
        Date expResult = new Date(121,10,14);
        Date result = Calculator.convertToDateViaInstant(dateToConvert);
        assertEquals(expResult, result);
    }
    
}
