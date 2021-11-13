package lapr.project.utils;

import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesUtilsTest {

    @Test
    public void testCoordinatesUtils() {

        ArrayList<Ship> shiparray = new ArrayList<>();

        double distanceTravelled = CoordinatesUtils.distance(24.83657, -78.50441, 25.83611, -78.50481);
        double distanceTravelled1 = CoordinatesUtils.distance(25.83657, -78.50441, 25.83611, -78.50481);

        assertTrue(distanceTravelled > 10);
        assertFalse(distanceTravelled1 > 10);

    }

}
