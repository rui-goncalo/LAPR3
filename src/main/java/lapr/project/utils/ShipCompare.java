package lapr.project.utils;

import lapr.project.model.Ship;

import java.util.Comparator;

public class ShipCompare implements Comparator<Ship> {
    @Override
    public int compare(Ship s1, Ship s2) {
        return Double.compare(s1.getSummary().getTravelledDistance(), s2.getSummary().getTravelledDistance());
    }
}
