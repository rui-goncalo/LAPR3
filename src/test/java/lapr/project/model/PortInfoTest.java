package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortInfoTest {

    PortInfo newPortInfo = new PortInfo(
            "Europe",
            29002,
            "Liverpool");

    @Test
    public void testGetCountry() {
        assertEquals("Europe", newPortInfo.getCountry());
    }

    @Test
    public void testGetId() {
        assertEquals(29002, newPortInfo.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Liverpool", newPortInfo.getName());
    }

}
