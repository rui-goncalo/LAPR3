package lapr.project.ui;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;
import lapr.project.model.ShipIMO;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReader;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();
        menu.runMenu();
    }
}