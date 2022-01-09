package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShipCompareTest {

    ArrayList<ShipData> sd = new ArrayList<>();

    LocalDateTime time1 = LocalDateTime.of(2020, 12, 31, 12, 28, 0);

    Ship ship = new Ship(229961000, sd, "ARABELLA", 9700122, "9HA3752", 70, 199, 32, 14.4, 0);

    ShipData sd1 = new ShipData(time1, 26.23188, -130.33667, 0.1, 82.8, 0, 'A');


    @Test
    public void compare() {
        ship.addDynamicShip(sd1);
        Summary summary = new Summary(ship);
        assertEquals(0.0, summary.getTravelledDistance());
    }

}

