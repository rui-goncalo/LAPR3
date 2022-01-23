package lapr.project.utils;

import lapr.project.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;


public class CSVReaderUtilsTest {

    @Test
    public void testReadCSV() throws Exception {

        ArrayList<Ship> arrayship = CSVReaderUtils.readShipCSV("data/sships.csv");
        assertEquals(23, arrayship.size());
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
    void testReadPorts() {

        ArrayList<Port> ports = CSVReaderUtils.readPortCSV("data/sports.csv");
        assertEquals(22, ports.size());
        assertEquals("Europe", ports.get(0).getContinent());
        assertEquals("United Kingdom", ports.get(0).getCountry());
        assertEquals(29002, ports.get(0).getId());
        assertEquals("Liverpool", ports.get(0).getName());
        assertEquals(53.46666667, ports.get(0).getLatitude());
        assertEquals(-3.033333333, ports.get(0).getLongitude());

        ports = CSVReaderUtils.readPortCSV("");
        assertEquals(0, ports.size());
    }

    @Test
    void testreadPortsExp() {
        ArrayList<Port> expectRes = null;
        assertEquals(null, expectRes);
    }

    @Test
    void testReadCountry() {
        ArrayList<Country> countries = CSVReaderUtils.readCountryCSV("data/countries.csv");
        assertEquals(68, countries.size());
        assertEquals("Europe", countries.get(0).getContinent());
        assertEquals("CY", countries.get(0).getAlpha2());
        assertEquals("CYP", countries.get(0).getAlpha3());
        assertEquals("Cyprus", countries.get(0).getName());
        assertEquals(0.85, countries.get(0).getPopulation());
        assertEquals("Nicosia", countries.get(0).getCapital());
        assertEquals(35.16666667, countries.get(0).getLatitude());
        assertEquals(33.366667, countries.get(0).getLongitude());

        countries = CSVReaderUtils.readCountryCSV("");
        assertEquals(0, countries.size());


    }

    @Test
    void testReadSeadists() {
        ArrayList<Seadist> seadists = CSVReaderUtils.readSeadistsCSV("data/seadists.csv");
        assertEquals(3401, seadists.size());
        assertEquals("Denmark", seadists.get(0).getFromCountry());
        assertEquals(10358, seadists.get(0).getFromPortId());
        assertEquals("Aarhus", seadists.get(0).getFromPort());
        assertEquals("Turkey", seadists.get(0).getToCountry());
        assertEquals(246265, seadists.get(0).getToPortId());
        assertEquals("Ambarli", seadists.get(0).getToPort());
        assertEquals(3673, seadists.get(0).getSeaDistance());

        seadists = CSVReaderUtils.readSeadistsCSV("");
        assertEquals(0, seadists.size());
    }


    @Test
    void testReadBorder() {
        ArrayList<Country> countries = CSVReaderUtils.readCountryCSV("data/countries.csv");
        if (countries != null) {
            assertEquals(68, countries.size());
            assertEquals("Europe", countries.get(0).getContinent());
            assertEquals("CY", countries.get(0).getAlpha2());
            assertEquals("CYP", countries.get(0).getAlpha3());
            assertEquals("Cyprus", countries.get(0).getName());
            assertEquals(0.85, countries.get(0).getPopulation());
            assertEquals("Nicosia", countries.get(0).getCapital());
            assertEquals(35.16666667, countries.get(0).getLatitude());
            assertEquals(33.366667, countries.get(0).getLongitude());

            ArrayList<Border> borderArr = CSVReaderUtils.readBordersCSV("data/borders.csv");
            if (borderArr != null) {
                assertEquals(119, borderArr.size());
                assertEquals("Russia", borderArr.get(borderArr.size() - 1).getCountry1().getName());
                assertEquals("Ukraine", borderArr.get(borderArr.size() - 1).getCountry2().getName());
            }

            borderArr = CSVReaderUtils.readBordersCSV("");
            assertEquals(0, borderArr.size());
        }


    }

}
