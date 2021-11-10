package lapr.project.ui;
import lapr.project.model.CSVReader;
import lapr.project.model.Menu;
import lapr.project.model.Ship;

import lapr.project.model.ShipIMO;
import lapr.project.tree.BST;
import lapr.project.utils.Sumary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws Exception {

        Menu menu = new Menu();
        menu.runMenu();


     /*   CSVReader reader = new CSVReader();
        ArrayList<Ship> shipArray = reader.readCSV();

        BST<ShipIMO> shipIMOBST = new BST<>();


        for(Ship ship : shipArray) {

            shipIMOBST.insert(new ShipIMO(ship));

        }

        shipIMOBST.printTree("");

*/

       /* ArrayList<Object> sumary = null;
        Ship s = null;

        BST<Ship> shipTree = new BST<>();

        for (Ship ship : shipArray) {
            //System.out.println(ship.getMmsi());
            if(ship.getMmsi() == 212180000){
                s = ship;
            }
            shipTree.insert(ship);
//            System.out.println(shipTree);
        }

        sumary = Sumary.createSumary(s,"MMSI");

        for (Object o : sumary) {
            System.out.println(o.toString());
        }*/


//        shipTree.printTree("");
//
//        System.out.println(shipArray.get(0).getDynamicShip().get(0).getDateTime());
//        Ship ship = shipTree.findCallSignOrIMO("9192387", false); // false quando é o imo, true quando é o callsign
//        if (ship != null) {
//            System.out.println(ship.getMmsi());
//            System.out.println(ship.getImo());
//            System.out.println(ship.getCallSign());
//            System.out.println(ship.getDynamicShip().get(0).getDateTime());
//        }



        //size, comparar dynamic
    }
}