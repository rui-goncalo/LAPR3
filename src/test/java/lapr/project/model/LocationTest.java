package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

        Location newLocation = new Location(
                "Liverpool",
                "Europe");

    @Test
    public void testGetContinent() {
        assertEquals("Europe", newLocation.getContinent());
    }

    @Test
        public void testGetName() {
            assertEquals("Liverpool", newLocation.getName());
        }
}
