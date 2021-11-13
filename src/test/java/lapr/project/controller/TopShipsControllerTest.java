package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import lapr.project.model.ShipIMO;
import lapr.project.tree.BST;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TopShipsControllerTest {
    
    /**
     * Test of getNTopShips method, of class TopShipsController.
     */
    @Test
    public void testGetNTopShips() {
        System.out.println("getNTopShips");
        int n = 5;
        //SHIPS
        //VESSEL TYPE 70
        //SHIP1
        ArrayList<ShipData> shipDataList1 = new ArrayList<>();
        shipDataList1.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  42.97875, -66.97001, 12.9, 13.1,  355, 'B'));
        shipDataList1.add(new ShipData(LocalDateTime.of(2020, 1, 1, 5,  0),  42.92236, -66.97243, 12.5, 2.4,   358, 'B'));
        shipDataList1.add(new ShipData(LocalDateTime.of(2020, 1, 1, 10, 0),  42.7698,  -66.9759,  13.3, 3.7,   356, 'B'));
        shipDataList1.add(new ShipData(LocalDateTime.of(2020, 1, 1, 15, 0),  42.96912, -66.97061, 12.7, -55.4, 358, 'B'));
        shipDataList1.add(new ShipData(LocalDateTime.of(2020, 1, 1, 20, 0),  42.95969, -66.97106, 12.9, 8.1,   358, 'B'));
        Ship ship1 = new Ship(210950000, shipDataList1, "VARAMO", 9395044, "C4SQ2", 70, 166, 25, 9.5, 0);
        
        //SHIP2
        ArrayList<ShipData> shipDataList2 = new ArrayList<>();
        shipDataList2.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  24.34573, -85.12394, 11.7, 119.9, 117, 'B'));
        shipDataList2.add(new ShipData(LocalDateTime.of(2020, 1, 1, 5,  0),  24.14301, -84.72268, 11.7, 116.6, 114, 'B'));
        shipDataList2.add(new ShipData(LocalDateTime.of(2020, 1, 1, 10, 0),  24.28016, -85.00316, 11.3, 120.8, 118, 'B'));
        shipDataList2.add(new ShipData(LocalDateTime.of(2020, 1, 1, 15, 0),  24.20221, -84.85411, 11.3, 116.8, 117, 'B'));
        shipDataList2.add(new ShipData(LocalDateTime.of(2020, 1, 1, 20, 0),  24.11445, -84.65529, 11.6, 113.3, 110, 'B'));
        Ship ship2 = new Ship(212180000, shipDataList2, "SAITA", 9643544, "5BBA4", 70, 228, 32, 14.4, 0);

        //SHIP3
        ArrayList<ShipData> shipDataList3 = new ArrayList<>();
        shipDataList3.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  55.09307, -167.63625, 3.5, -61.6, 232, 'B'));
        Ship ship3 = new Ship(212351000, shipDataList3, "HYUNDAI SINGAPORE", 9305685, "5BZP3", 70, 303, 40, 14.5, 0);
        
        //VESSEL TYPE 35
        //SHIP 4
        ArrayList<ShipData> shipDataList4 = new ArrayList<>();
        shipDataList4.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  24.34573, -85.12394, 9,    119.9, 3,   'B'));
        shipDataList4.add(new ShipData(LocalDateTime.of(2020, 1, 1, 5,  0),  24.7,     -85,       8,    116.6, 4,   'B'));
        shipDataList4.add(new ShipData(LocalDateTime.of(2020, 1, 1, 10, 0),  24.8,     -84.56,    8,    120.8, 10,  'B'));
        shipDataList4.add(new ShipData(LocalDateTime.of(2020, 1, 1, 15, 0),  24.9,     -84.3,     3,    116.8, 20,  'B'));
        shipDataList4.add(new ShipData(LocalDateTime.of(2020, 1, 1, 20, 0),  25,       -84.2,     10,   113.3, 1,   'B'));
        Ship ship4 = new Ship(300000000, shipDataList4, "POLAR EXPRESS", 9643543, "5BBA3", 35, 100, 15, 9, 0);
        
        //SHIP 5
        ArrayList<ShipData> shipDataList5 = new ArrayList<>();
        shipDataList5.add(new ShipData(LocalDateTime.of(2020, 1, 1, 0,  0),  24,       -85,       0,    0,     1,   'B'));
        Ship ship5 = new Ship(300000001, shipDataList5, "LITERAL_BOULDER", 9643542, "5BBA2", 35, 120,202, 10, 0);
           
        //TIME
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 1, 1);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 1, 1);
        
        ArrayList<ShipIMO> shipList= new ArrayList<>();
        shipList.add(new ShipIMO(ship1));
        shipList.add(new ShipIMO(ship2));
        shipList.add(new ShipIMO(ship3));
        shipList.add(new ShipIMO(ship4));
        shipList.add(new ShipIMO(ship5));
        BST<ShipIMO> shipTree = new BST<>();
        
        for (ShipIMO ship : shipList) {
            shipTree.insert(ship);
        }
        
        TopShipsController instance = new TopShipsController();
        ArrayList<Ship> expResult = instance.getNTopShips(n, start, end, shipTree);
        ArrayList<Ship> result = instance.getNTopShips(n, start, end, shipTree);
        assertEquals(expResult, result);
    } 

}
