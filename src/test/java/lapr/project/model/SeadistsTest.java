package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeadistsTest {

        Seadists newSeadists = new Seadists(
                "Denmark",
                10358,
                "Aarhus",
                "Turkey",
                246265,
                "Ambarli",
                3673);

        @Test
        public void testGetFromCountry() {
            assertEquals("Denmark", newSeadists.getFromCountry());
        }

        @Test
        public void testGetFromPortId() {
            assertEquals(10358, newSeadists.getFromPortId());
        }

        @Test
        public void testGetFromPort() {
            assertEquals("Aarhus", newSeadists.getFromPort());
        }

        @Test
        public void testGetToCountry() {
            assertEquals("Turkey", newSeadists.getToCountry());
        }

        @Test
        public void testGetToPortId() {
            assertEquals(246265, newSeadists.getToPortId());
        }

        @Test
        public void testGetToPort() {
            assertEquals("Ambarli", newSeadists.getToPort());
        }

        @Test
        public void testGetSeaDistance() {
            assertEquals(3673, newSeadists.getSeaDistance());
        }


}
