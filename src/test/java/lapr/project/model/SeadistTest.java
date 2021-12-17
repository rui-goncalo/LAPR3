package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeadistTest {

        Seadist newSeadist = new Seadist(
                "Denmark",
                10358,
                "Aarhus",
                "Turkey",
                246265,
                "Ambarli",
                3673);

        @Test
        public void testGetFromCountry() {
            assertEquals("Denmark", newSeadist.getFromCountry());
        }

        @Test
        public void testGetFromPortId() {
            assertEquals(10358, newSeadist.getFromPortId());
        }

        @Test
        public void testGetFromPort() {
            assertEquals("Aarhus", newSeadist.getFromPort());
        }

        @Test
        public void testGetToCountry() {
            assertEquals("Turkey", newSeadist.getToCountry());
        }

        @Test
        public void testGetToPortId() {
            assertEquals(246265, newSeadist.getToPortId());
        }

        @Test
        public void testGetToPort() {
            assertEquals("Ambarli", newSeadist.getToPort());
        }

        @Test
        public void testGetSeaDistance() {
            assertEquals(3673, newSeadist.getSeaDistance());
        }


}
