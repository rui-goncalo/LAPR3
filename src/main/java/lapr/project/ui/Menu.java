package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private final String BIG_SHIP_FILE = "src/data/bships.csv";
    private final String SMALL_SHIP_FILE = "src/data/sships.csv";

    private ArrayList<Ship> shipArray = new ArrayList<>();
    private BST<ShipMMSI> mmsiBST = new BST<>();
    private BST<ShipIMO> imoBST = new BST<>();
    private BST<ShipCallSign> csBST = new BST<>();

    public void runMenu() {
        printHeader();
        while (true) {
            this.printMenu();
            int choice = getInput(2);
            switch (choice) {
                case 0:
                    this.exit();
                    break;
                case 1:
                    this.printImport();
                    break;
            }
        }
    }

    private void retrieveFilePath() {
        System.out.println("Please insert file path:");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        try {
            this.shipArray = CSVReader.readCSV(path);
            this.insertShips();
        } catch (Exception e) {
            System.out.println("Invalid path, please try again.");
            this.retrieveFilePath();
        }
    }

    private void printImportList() {
        System.out.println("Please make a selection: ");
        System.out.println("2) Big Ship File");
        System.out.println("1) Small Ship File");
        System.out.println("0) Exit");
        int choice = getInput(3);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                try {
                    this.shipArray = CSVReader.readCSV(SMALL_SHIP_FILE);
                    this.insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    this.shipArray = CSVReader.readCSV(BIG_SHIP_FILE);
                    this.insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void insertShips() {
        for (Ship ship : this.shipArray) {
            this.mmsiBST.insert(new ShipMMSI(ship));
            this.imoBST.insert(new ShipIMO(ship));
            this.csBST.insert(new ShipCallSign(ship));
        }
        this.printAfterInsert();
    }

    private void printAfterInsert() {
        System.out.println("Please make a selection: ");
        System.out.println("2) Show all ships");
        System.out.println("1) Search ship");
        System.out.println("0) Exit");
        int choice = this.getInput(3);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                this.printSearch();
                break;
            case 2:
                this.mmsiBST.printShips();
                break;
        }
    }

    private void printSearch() {
        System.out.println("Please make a selection: ");
        System.out.println("3) Search by MMSI");
        System.out.println("2) Search by IMO");
        System.out.println("1) Search by Call Sign");
        System.out.println("0) Exit");
        int choice = this.getInput(4);
        System.out.println(choice);
        Scanner scan = new Scanner(System.in);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                System.out.println("Please insert ship CallSign to search:");
                String callSign = scan.nextLine();
                ShipCallSign shipCallSign = csBST.find(new ShipCallSign(callSign));
                this.shipMenu();
                break;
            case 2:
                System.out.println("Please insert ship IMO to search:");
                String imo = scan.nextLine();
                ShipIMO shipIMO = imoBST.find(new ShipIMO(Integer.parseInt(imo)));
                this.shipMenu();
                break;
            case 3:
                System.out.println("Please insert ship MMSI to search:");
                String mmsi = scan.nextLine();
                ShipMMSI shipMMSI = mmsiBST.find(new ShipMMSI(Integer.parseInt(mmsi)));
                this.shipMenu();
                break;
        }
    }

    private void shipMenu() {
        System.out.println("Please make a selection: ");
        System.out.println("2) Show all ship movements");
        System.out.println("1) Show ship information");
        System.out.println("0) Exit");
        int choice = getInput(3);

    }

    private void printImport() {
        System.out.println("Please make a selection: ");
        System.out.println("2) Import From File");
        System.out.println("1) Import From List");
        System.out.println("0) Exit");
        int choice = getInput(3);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                printImportList();
                break;
            case 2:
                this.retrieveFilePath();
                break;
        }
    }

    private void printHeader() {
        System.out.println("+-----------------------------+");
        System.out.println("|         Welcome to our      |");
        System.out.println("|        Menu Application     |");
        System.out.println("+-----------------------------+");
    }

    private void printMenu() {
        System.out.println("Please make a selection: ");
        System.out.println("1) Import Files");
        System.out.println("0) Exit");
    }

    private int getInput(int optionCount) {
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > optionCount) {
            try {
                System.out.println("\nEnter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        return choice;
    }

    private void exit() {
        System.exit(0);
    }
}
