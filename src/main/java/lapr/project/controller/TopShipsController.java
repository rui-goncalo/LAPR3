package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.*;
import lapr.project.utils.BST;


public class TopShipsController {
    
    protected static class ShipByDistance implements Comparable<ShipByDistance>{
        
        private Ship ship;
        private int traveledDistance;
        
        public ShipByDistance(Ship ship, int traveledDistance) {
          this.ship = ship;
          this.traveledDistance = traveledDistance;
        }
        
        //rever travelled distance int?
        public int compareTo(ShipByDistance ship){
            return traveledDistance - ship.traveledDistance;
        }
      }
    
    public static ArrayList<Ship> getNTopShips(int n, LocalDateTime start, LocalDateTime end, BST<Ship> shipTree){
        ArrayList<Ship> topShips = new ArrayList<>(); //list de return
        ArrayList<ShipByDistance> shipsToSort= new ArrayList<>();
        return topShips;
    }
}
