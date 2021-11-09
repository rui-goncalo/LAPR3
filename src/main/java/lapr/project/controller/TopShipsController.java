package lapr.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lapr.project.model.*;
import lapr.project.utils.*;


public class TopShipsController {
    
    //deixar nested ou passar pra pair?
    protected class ShipByDistance implements Comparable<ShipByDistance>{
        
        private Ship ship;
        private int traveledDistance;
        
        public ShipByDistance(Ship ship, int traveledDistance) {
          this.ship = ship;
          this.traveledDistance = traveledDistance;
        }
        
        // accessor methods
        public Ship getShip() { return ship; }
        public int getTraveledDistance() { return traveledDistance; }
        
        //rever traveled distance int?
        public int compareTo(ShipByDistance ship){
            return traveledDistance - ship.traveledDistance;
        }
      }
    
    //rever: metodo static?
    public ArrayList<Ship> getNTopShips(int n, LocalDateTime start, LocalDateTime end, BST<Ship> shipTree){
        ArrayList<Ship> topShips = new ArrayList<>(); //list de return
        ArrayList<ShipByDistance> shipsToSort= new ArrayList<>();
        
        //TODO falta filtrar as entradas em dynamicShip por datas
        //     neste momento calcula totaldistance
        
        //metodo quebra aqui por static (ShipByDistance não o é)
        for(Ship ship: shipTree.posOrder()){
            shipsToSort.add(new ShipByDistance(ship, Calculator.totalDistance(ship.getDynamicShip())));
        }
        
        shipsToSort.sort(null);
        //depois de shipsToSort estar sorted é so retornar pelo topShips
        for(int i = 0; i<n ; i++){
            topShips.add(shipsToSort.get(i).getShip());
        } 
        return topShips;
    }
}
