package lapr.project.utils;

import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class CSVReaderTest {

    @Test
    public void testCARALHO() throws Exception {

        ArrayList<Ship> arrayship = CSVReaderUtils.readCSV("src/data/sships.csv");
        assertEquals(22, arrayship.size());
        assertEquals(210950000, arrayship.get(0).getMmsi());
        assertEquals("VARAMO", arrayship.get(0).getName());
        assertEquals(9395044, arrayship.get(0).getImo());
        assertEquals("C4SQ2", arrayship.get(0).getCallSign());
        assertEquals(70, arrayship.get(0).getVessel());
        assertEquals(166, arrayship.get(0).getLength());
        assertEquals(25, arrayship.get(0).getWidth());
        assertEquals(9.5, arrayship.get(0).getDraft());
        assertEquals(0, arrayship.get(0).getCargo());

        assertEquals(25, arrayship.get(0).getDynamicShip().size());
        assertEquals("2020-12-31T18:31", arrayship.get(0).getDynamicShip().get(0).getDateTime().toString());
        assertEquals(43.22513, arrayship.get(0).getDynamicShip().get(0).getLatitude());
        assertEquals(-66.96725, arrayship.get(0).getDynamicShip().get(0).getLongitude());
        assertEquals(11.7, arrayship.get(0).getDynamicShip().get(0).getSog());
        assertEquals(5.5, arrayship.get(0).getDynamicShip().get(0).getCog());
        assertEquals(355, arrayship.get(0).getDynamicShip().get(0).getHeading());
        assertEquals('B', arrayship.get(0).getDynamicShip().get(0).getTransceiver());
    }
}
