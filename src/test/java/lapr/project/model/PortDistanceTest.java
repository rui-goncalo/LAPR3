
package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortDistanceTest {

    Port newPort = new Port("Europe",
            "United Kingdom",
            29002,
            "Liverpool",
            53.46666667,
            -3.033333333);

    PortDistance newPortDistance = new PortDistance(newPort, 53.46666667);

    @Test
    public void testGetPort() {
        assertEquals(newPort, newPortDistance.getPort());
    }

    @Test
    public void testGetDistance() {
        assertEquals(53.46666667, newPortDistance.getDistance());
    }

    @Test
    public void testCompareTo() {
        PortDistance o = new PortDistance(newPort, 53.46666667);
        PortDistance o2 = new PortDistance(newPort, 53.46666667);

        assertEquals(0, o.compareTo(o2));    }
}
