package lapr.project.ui;
import lapr.project.model.CSVReader;
import lapr.project.model.Ship;
import lapr.project.utils.BST;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {


        ArrayList<Ship> shipArray = CSVReader.readCSV();

        BST<Ship> shipTree = new BST<>();

        for (Ship ship : shipArray) {
            //System.out.println(ship.getMmsi());

            shipTree.insert(ship);
//            System.out.println(shipTree);
        }
        shipTree.printTree("");

        Ship ship = shipTree.findImo(9192387);
        //System.out.println(ship.getMmsi());
        //System.out.println(ship.getImo());
        //System.out.println(ship.getCallSign());
        //System.out.println(ship.getDynamicShip().get(2).getDateTime());


        //size, comparar dynamic
    }
}