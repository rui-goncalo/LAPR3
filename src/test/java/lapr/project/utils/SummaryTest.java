package lapr.project.utils;

import lapr.project.model.ShipData;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SummaryTest {

    ArrayList<ShipData> sd = new ArrayList<>();

    LocalDateTime time1 = LocalDateTime.of(2020, 12, 31, 12, 28, 0);
    LocalDateTime time2 = LocalDateTime.of(2020, 12, 31, 23, 31, 0);
    LocalDateTime time3 = LocalDateTime.of(2021, 1, 1, 5, 31, 0);

    ShipData sd1 = new ShipData(time1, 26.23188, -130.33667, 0.1, 82.8, 0, 'A');
    ShipData sd2 = new ShipData(time2, 74.23184, -150.33702, 0.1, 34.6, 0, 'A');
    ShipData sd3 = new ShipData(time3, 44.23184, -170.33702, 0.1, 34.6, 0, 'A');

    Ship ship = new Ship(229961000, sd, "ARABELLA", 9700122, "9HA3752", 70, 199, 32, 14.4, 0);

    @Test
    public void createSummary() {
        ship.addDynamicShip(sd1);
        ship.addDynamicShip(sd2);
        ship.addDynamicShip(sd3);
        Summary summary = new Summary(ship);

        assertEquals(sd1, summary.getDepartureData());
        assertEquals(sd3, summary.getArrivalData());
        assertEquals(time1, summary.getDepartureTime());
        assertEquals(time3, summary.getArrivalTime());
        assertEquals(sd1.getLatitude(), summary.getDepartLat());
        assertEquals(sd1.getLongitude(), summary.getDepartLon());
        assertEquals(sd3.getLatitude(), summary.getArrLat());
        assertEquals(sd3.getLongitude(), summary.getArrLon());
        assertEquals(0, summary.getDays());
        assertEquals(17, summary.getHours());
        assertEquals(3, summary.getMinutes());
        assertEquals(sd1.getSog(), summary.getMaxSog());
        assertEquals((sd1.getSog() + sd2.getSog() + sd3.getSog()) / 3, summary.getMeanSog());
        assertEquals(sd1.getCog(), summary.getMaxCog());
        assertEquals((sd1.getCog() + sd2.getCog() + sd3.getCog()) / 3, summary.getMeanCog());
        assertEquals(8944.417534364107, summary.getTravelledDistance());
        assertEquals(4092.2812091548367, summary.getDeltaDistance());
    }
}