package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;


import java.util.ArrayList;

public class Calculator {

    /**
     * Calculates distance between two latitude and longitude points and
     * convert it to a distance in kilometers. Uses Haversine method as its base.
     *
     * @param lat1 Start point.
     * @param lon1 Start point.
     * @param lat2 End point.
     * @param lon2 End point.
     * @return distance in Km's.
     */

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == lat2 && lon1 == lon2)
            return 0;
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return dist;
        }
    }

    /**
     * Nested static class for a pair of ships.
     */
    public static class ShipPair {
        private final Ship firstShip;
        private final Ship secondShip;

        public ShipPair(Ship firstShip, Ship secondShip) {
            this.firstShip = firstShip;
            this.secondShip = secondShip;
        }

        public Ship getFirstShip() {
            return firstShip;
        }

        public Ship getSecondShip() {
            return secondShip;
        }
    }

    /**
     * Given the pairs of ships, check the combination of
     * ships with arrival/departures less than 5km's within
     * an ArrayList
     *
     * @param shipArray the list to use during the search
     * @return Pairs of ships with arrival/departure nearest (both less than 5km's)
     */
    public static ArrayList<ShipPair> searchShipPairs(ArrayList<Ship> shipArray) {
        ArrayList<Ship> shipCandidates = new ArrayList<>();

        for (Ship ship : shipArray) {
            ShipData firstDynamic = ship.getFirstDynamicData();
            ShipData lastDynamic = ship.getLastDynamicData();

            double travelledDistance = getDistance(firstDynamic.getLatitude(),
                    firstDynamic.getLongitude(),
                    lastDynamic.getLatitude(),
                    lastDynamic.getLongitude());

            if (travelledDistance > 10)
                shipCandidates.add(ship);
        }

        ArrayList<ShipPair> shipPairs = new ArrayList<>();

        for (int i = 0; i < shipCandidates.size() - 1; i++) {
            for (int j = i + 1; j < shipCandidates.size(); j++) {
                Ship currentShip = shipCandidates.get(i);
                Ship nextShip = shipCandidates.get(j);

                ShipData currentFirstDynamic = currentShip.getFirstDynamicData();
                ShipData currentLastDynamic = currentShip.getLastDynamicData();

                ShipData nextFirstDynamic = nextShip.getFirstDynamicData();
                ShipData nextLastDynamic = nextShip.getLastDynamicData();

                double departureDistance = getDistance(currentFirstDynamic.getLatitude(),
                        currentFirstDynamic.getLongitude(),
                        nextFirstDynamic.getLatitude(),
                        nextFirstDynamic.getLongitude());

                double arrivalDistance = getDistance(currentLastDynamic.getLatitude(),
                        currentLastDynamic.getLongitude(),
                        nextLastDynamic.getLatitude(),
                        nextLastDynamic.getLongitude());

                if (departureDistance < 5 || arrivalDistance < 5)
                    shipPairs.add(new ShipPair(currentShip, nextShip));
            }
        }
        return shipPairs;
    }

}
