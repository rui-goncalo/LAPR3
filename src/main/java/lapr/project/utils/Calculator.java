package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import lapr.project.data.FreightAdjacencyMatrixGraph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Calculator {
    
    //In Kg/m^3
    private static final double SEAWATER_DENSITY = 1026;
    
    //In m/s^2
    private static final double GRAV_ACCELERATION = 9.8;
    
    private static final int CONTAINER_MASS = 500;
    private static final double MINUTES_IN_A_DEGREE = 60;
    private static final double NAUTICAL_MILES_TO_STATUTE_MILES = 1.1515;
    private static final double STATUTE_MILES_TO_KILOMETERS = 1.609344;
    
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
            dist = dist * MINUTES_IN_A_DEGREE * NAUTICAL_MILES_TO_STATUTE_MILES;
            dist = dist * STATUTE_MILES_TO_KILOMETERS;
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

    
    //US420
    //The total mass placed on the vessel
    public static int totalMassPlaced(int numContainers){
        return numContainers*CONTAINER_MASS;
    }
    
    //The pressure exerted by the cargo in the water
    public static double pressureExerted(double shipArea, double mass){
        double weight = mass * GRAV_ACCELERATION;
        return Math.round( (weight / shipArea) * 100) /100.0;
    }
    
    //The height difference after adding the cargo
    public static double heightDifference(double initialDraft, double shipWidth, double shipLength, double placedMass){
        double shipArea = shipWidth * shipLength;
        double displacedVol = placedMass / SEAWATER_DENSITY;
        double draftChange = displacedVol/shipArea;
        return draftChange;
    }

    public static <V, E> List<LinkedList<V>> allCycles(FreightAdjacencyMatrixGraph<V, E> g) {

        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();
        for (V vOrig : g.vertices()) {
            boolean[] visited = new boolean[g.numVertices()];
            allPaths(g, vOrig, vOrig, visited, path, paths);

        }
        return paths;
    }

    public static <V, E> void allPaths(FreightAdjacencyMatrixGraph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                       LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        path.push(vOrig);
        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj == vDest) {
                path.push(vDest);
                LinkedList<V> pathClone = (LinkedList<V>) path.clone();
                Collections.reverse(pathClone);
                paths.add(pathClone);

                path.pop();
            } else {
                if (!visited[g.key(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        path.pop();
    }
}
