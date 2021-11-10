package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.Calculator;


public class TopShipsController {
    
    //deixar nested ou passar pra pair?
    protected class ShipByDistance implements Comparable<ShipByDistance>{
        
        private Ship ship;
        private double traveledDistance;
        
        public ShipByDistance(Ship ship, double traveledDistance) {
          this.ship = ship;
          this.traveledDistance = traveledDistance;
        }
        
        // accessor methods
        public Ship getShip() { return ship; }
        public double getTraveledDistance() { return traveledDistance; }
        
        //rever traveled distance int?
        @Override
        public int compareTo(ShipByDistance ship){
            if(traveledDistance < ship.traveledDistance){return -1;}
            if(traveledDistance > ship.traveledDistance){return 1;}
            return 0;
        }
      }
    
    //rever: metodo static?
/*    public ArrayList<Ship> getNTopShips(int n, LocalDateTime start, LocalDateTime end, BST<ShipIMO> shipTree){
        ArrayList<Ship> topShips = new ArrayList<>(); //list de return
        ArrayList<ShipByDistance> shipsToSort= new ArrayList<>();
        ArrayList<ShipData> dynamicShip;
        
        //TODO tests and catch empty dynamicShip's
        //     
        
        //metodo quebra aqui por static (ShipByDistance não o é)
        for(Ship ship: shipTree.posOrder()){
            dynamicShip = ship.filterShipData(start, end);
            shipsToSort.add(new ShipByDistance(ship, Calculator.totalDistance(dynamicShip)));
        }
        
        shipsToSort.sort(null);
        //depois de shipsToSort estar sorted é so retornar pelo topShips
        for(int i = 0; i<n ; i++){
            topShips.add(shipsToSort.get(i).getShip());
        } 
        return topShips;
    } */
}
