package lapr.project.ui;
import lapr.project.model.CSVReader;
import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import lapr.project.tree.BST;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


class Main {
    public static void main(String[] args) throws Exception {


        ArrayList<ArrayList<ShipData>> shipDataList = CSVReader.readCSV();
        for (int i = 0; i < shipDataList.size(); i++) {
            for (int j = 0; j < shipDataList.get(i).size(); j++) {
                System.out.println(shipDataList.get(i).get(j).getLatitude());

            }
        }

        BST<ShipData<E>> tree = new BST<>;


    }
    }


