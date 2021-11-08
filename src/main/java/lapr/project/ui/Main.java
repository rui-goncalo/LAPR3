package lapr.project.ui;
import lapr.project.model.CSVReader;
import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {


        ArrayList<Ship> shipArray = CSVReader.readCSV();


        for (Ship ship : shipArray) {
            System.out.println(ship.getMmsi());
        }

    }
}

