package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortTest {

        Port newPort = new Port("Europe",
                "United Kingdom",
                29002,
                "Liverpool",
                53.46666667,
                -3.033333333);

        @Test
        public void testGetContinent() {
            assertEquals("Europe", newPort.getContinent());
        }

        @Test
        public void testGetCountry() {
            assertEquals("United Kingdom", newPort.getCountry());
        }

        @Test
        public void testGetPortID() {
            assertEquals(29002, newPort.getId());
        }

        @Test
        public void testGetPortName() {
            assertEquals("Liverpool", newPort.getName());
        }

        @Test
        public void testGetLat() {
            assertEquals(53.46666667, newPort.getLatitude());
        }

        @Test
        public void testGetLon() {
            assertEquals(-3.033333333, newPort.getLongitude());
        }
}
