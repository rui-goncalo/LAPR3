package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.Calculator;

/**
 *
 * @author 1180590
 */
public class TopShipsController {
    
    /**
     * Nested class for sorting the ships by traveled distance
     */
    protected class ShipByDistance implements Comparable<ShipByDistance>{
        
        private final Ship ship;
        private final double traveledDistance;
        
        /**
         *
         * @param ship ship to associate a traveled distance to
         * @param traveledDistance traveled distance of a ship in a certain period of time
         */
        public ShipByDistance(Ship ship, double traveledDistance) {
          this.ship = ship;
          this.traveledDistance = traveledDistance;
        }
        
        // accessor methods

        /**
         *
         * @return ship
         */
        public Ship getShip() { return ship; }

        /**
         *
         * @return traveled distance
         */
        public double getTraveledDistance() { return traveledDistance; }
        
        @Override
        public int compareTo(ShipByDistance ship){
            if(traveledDistance < ship.traveledDistance){return -1;}
            if(traveledDistance > ship.traveledDistance){return 1;}
            return 0;
        }
        
        /**
         * Equals function to implement Comparable correctly
         * @param ship to compare
         * @return
         */
        public boolean equals(ShipByDistance ship){
            return this.ship == ship.getShip();
        }
      }
    
    private ArrayList<Ship> topShipList;
    private ArrayList<Double> meanSogList;
    
    /**
     * Constructor for the Menu class to recieve the sorted lists
     */
    public TopShipsController(){
        topShipList = new ArrayList<>();
        meanSogList = new ArrayList<>();
    }
    
    /**
     * topShipList to add the Controller class
     * Used to test the get methods correctly
     * @param topShipList
     */
    public void setTopShips(ArrayList<Ship> topShipList){
        this.topShipList = topShipList;
    }
    
    /**
     * meanSogList to add the Controller class
     * Used to test the get methods correctly
     * @param meanSogList
     */
    public void setMeanSogs(ArrayList<Double> meanSogList){
        this.meanSogList = meanSogList;
    }
    
    /**
     *
     * @return topShipList
     */
    public  ArrayList<Ship> getTopShips(){
        return topShipList;
    }
    
    /**
     *
     * @return meanSogList
     */
    public  ArrayList<Double> getMeanSogs(){
        return meanSogList;
    }
    

    /**
     * Creates 2 sorted lists containing the Ships with the most traveled distance and their respective MeanSOG's
     * @param n number of Ships to return
     * @param start starting date (leave as null if none)
     * @param end end date (leave as null if none)
     * @param shipTree Tree containing all the ships
     */
    public void getNTopShips(int n, LocalDateTime start, LocalDateTime end, BST<ShipIMO> shipTree){
        ArrayList<Ship> topShips = new ArrayList<>(); //list de return
        ArrayList<ShipByDistance> shipsToSort= new ArrayList<>();
        ArrayList<ShipData> dynamicShip;
        Double meanSog = 0.0;
        
        for(Ship ship: shipTree.posOrder()){
            dynamicShip = ship.filterShipData(start, end);
            shipsToSort.add(new ShipByDistance(ship, Calculator.totalDistance(dynamicShip)));
        }
        
        shipsToSort.sort(null);
        //depois de shipsToSort estar sorted é so retornar pelo topShips
        for(int i = 0; i<n && i<shipsToSort.size(); i++){
            topShips.add(shipsToSort.get(i).getShip());
        } 
        topShipList = topShips;
        
        for (Ship ship : topShipList) {
            dynamicShip = ship.filterShipData(start, end);
            if (dynamicShip.isEmpty()) {
                meanSogList.add(0.0);
            } else {
                for (ShipData shipData : dynamicShip) {
                    meanSog += shipData.getSog();
                }
                meanSog= meanSog / dynamicShip.size();
                meanSogList.add(meanSog);
            }
        }
    }
}
