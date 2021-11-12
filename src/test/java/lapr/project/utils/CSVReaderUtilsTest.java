package lapr.project.utils;

import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public final class CSVReaderUtilsTest {

    @Test
    public void testParseShipCSV() throws Exception {
        ArrayList<Ship> shipArray = CSVReaderUtils.readCSV("src/data/sships.csv");
        assertEquals(22, shipArray.size());
        assertEquals(210950000, shipArray.get(0).getMmsi());
        assertEquals("VARAMO", shipArray.get(0).getName());
        assertEquals(9395044, shipArray.get(0).getImo());
        assertEquals("C4SQ2", shipArray.get(0).getCallSign());
        assertEquals(70, shipArray.get(0).getVessel());
        assertEquals(166, shipArray.get(0).getLength());
        assertEquals(25, shipArray.get(0).getWidth());
        assertEquals(9.5f, shipArray.get(0).getDraft());
        assertEquals(0, shipArray.get(0).getCargo());

        assertEquals(25, shipArray.get(0).getDynamicShip().size());
        assertEquals("2020-12-31T17:19", shipArray.get(0).getDynamicShip().get(0).getDateTime().toString());
        assertEquals(42.97875, shipArray.get(0).getDynamicShip().get(0).getLatitude());
        assertEquals(-66.97001, shipArray.get(0).getDynamicShip().get(0).getLongitude());
        assertEquals(12.9, shipArray.get(0).getDynamicShip().get(0).getSog());
        assertEquals(13.1, shipArray.get(0).getDynamicShip().get(0).getCog());
        assertEquals(355, shipArray.get(0).getDynamicShip().get(0).getHeading());
        assertEquals('B', shipArray.get(0).getDynamicShip().get(0).getTransceiver());
    }


    @Test
    public void testVerifyShip() throws Exception{
        ArrayList<Ship> shipArrayList = CSVReaderUtils.readCSV("src/data/sships.csv");
        assertFalse(CSVReaderUtils.verifyShip("210950000", shipArrayList) == -1);
        assertTrue(CSVReaderUtils.verifyShip("2109500001", shipArrayList) != 1);


    }
 }








    /*int mmsi = 210950000;
    int day = 31;
    int month = 12;
    int year = 2020;
    int hour = 18;
    int minute = 31;

    @Test
    public void testRead() throws Exception {
//        ArrayList<Ship> shipTest = CSVReader.readCSV();
//        assertEquals(mmsi, shipTest.get(0).getMmsi());
    }

    @Test
    public void testSort() throws Exception{
//        ArrayList<Ship> shipTest = CSVReader.sortByDate();
//        System.out.println(shipTest.get(0).getDynamicShip().get(0).getDateTime());
//        assertEquals(this.day, shipTest.get(0).getDynamicShip().get(0).getDateTime().getDayOfMonth());
//        assertEquals(this.month, shipTest.get(0).getDynamicShip().get(0).getDateTime().getMonthValue());
//        assertEquals(this.year, shipTest.get(0).getDynamicShip().get(0).getDateTime().getYear());
//        assertEquals(this.hour, shipTest.get(0).getDynamicShip().get(0).getDateTime().getHour());
//        assertEquals(this.minute, shipTest.get(0).getDynamicShip().get(0).getDateTime().getMinute());

    }*/
