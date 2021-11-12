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
        assertEquals("2020-12-31T18:31", shipArray.get(0).getDynamicShip().get(0).getDateTime().toString());
        assertEquals(43.22513, shipArray.get(0).getDynamicShip().get(0).getLatitude());
        assertEquals(-66.96725, shipArray.get(0).getDynamicShip().get(0).getLongitude());
        assertEquals(11.7, shipArray.get(0).getDynamicShip().get(0).getSog());
        assertEquals(5.5, shipArray.get(0).getDynamicShip().get(0).getCog());
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
