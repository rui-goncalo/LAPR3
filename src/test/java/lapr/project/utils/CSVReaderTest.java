package lapr.project.utils;

import lapr.project.model.Port;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;


public class CSVReaderTest {

    @Test
    public void testReadCSV() throws Exception {

        ArrayList<Ship> arrayship = CSVReaderUtils.readShipCSV("src/data/sships.csv");
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

    @Test
    void readPorts() throws Exception {
        ArrayList<Port> ports = CSVReaderUtils.readPortCSV("src/data/portTest.csv");
        assertEquals(1, ports.size());
        assertEquals("Europe", ports.get(0).getContinent());
        assertEquals("United Kingdom", ports.get(0).getCountry());
        assertEquals(29002, ports.get(0).getId());
        assertEquals("Liverpool", ports.get(0).getName());
        assertEquals(53.46666667, ports.get(0).getLat());
        assertEquals(-3.033333333, ports.get(0).getLon());
    }

    @Test
    void readPortsExp() throws Exception {
        ArrayList<Port> ports = CSVReaderUtils.readPortCSV("src/data/1234.csv");
        ArrayList<Port> expectRes = null;
        assertEquals(ports, expectRes);
    }

}
