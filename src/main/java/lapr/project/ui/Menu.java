package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.CoordinatesUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private final String BIG_SHIP_FILE = "src/data/bships.csv";
    private final String SMALL_SHIP_FILE = "src/data/sships.csv";

    private ArrayList<Ship> shipArray = new ArrayList<>();
    private final BST<ShipMMSI> mmsiBST = new BST<>();
    private final BST<ShipIMO> imoBST = new BST<>();
    private final BST<ShipCallSign> csBST = new BST<>();

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
            this.shipArray = CSVReaderUtils.readCSV(path);
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
                    this.shipArray = CSVReaderUtils.readCSV(SMALL_SHIP_FILE);
                    this.insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    this.shipArray = CSVReaderUtils.readCSV(BIG_SHIP_FILE);
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
        System.out.println("3) Show ships with close departure/arrival coordinates");
        System.out.println("2) Show all ships");
        System.out.println("1) Search ship");
        System.out.println("0) Exit");
        int choice = this.getInput(4);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                this.printSearch();
                break;
            case 2:
                for (Ship ship : mmsiBST.inOrder()) {
                    ship.printShip();
                }
                break;
            case 3:
                ArrayList<Ship> shipArrayList = new ArrayList<>();
                for (Ship ship : mmsiBST.inOrder()) {
                    ArrayList<ShipData> shipData = ship.getDynamicShip();
                    double initialLat = shipData.get(0).getLatitude();
                    double initialLong = shipData.get(0).getLongitude();

                    double finalLat = shipData.get(shipData.size() - 1).getLatitude();
                    double finalLong = shipData.get(shipData.size() - 1).getLongitude();

                    double distance = CoordinatesUtils.distance(initialLat, initialLong, finalLat, finalLong);
                    if (distance > 10) {
                        shipArrayList.add(ship);
//                        System.out.println("MMSI: " + ship.getMmsi());
//                        System.out.println(distance);
                    }
                }
                ArrayList<ArrayList<Ship>> shipPairArrayList = new ArrayList<>();
                ArrayList<Ship> pairArrayList;
                for (int i = 0; i < shipArrayList.size(); i++) {
                    for (int j = 1; j < shipArrayList.size(); j++) {
                        Ship ship = shipArrayList.get(i);

                        Ship newShip = shipArrayList.get(j);
                        if (ship.getMmsi() != newShip.getMmsi()) {
                            ArrayList<ShipData> oldData = ship.getDynamicShip();
                            double initialOldLat = oldData.get(0).getLatitude();
                            double initialOldLong = oldData.get(0).getLongitude();

                            double finalOldLat = oldData.get(oldData.size() - 1).getLatitude();
                            double finalOldLong = oldData.get(oldData.size() - 1).getLongitude();

                            ArrayList<ShipData> shipData = newShip.getDynamicShip();
                            double initialLat = shipData.get(0).getLatitude();
                            double initialLong = shipData.get(0).getLongitude();

                            double finalLat = shipData.get(shipData.size() - 1).getLatitude();
                            double finalLong = shipData.get(shipData.size() - 1).getLongitude();

                            double departureDistance = CoordinatesUtils.distance(initialOldLat,
                                    initialOldLong, initialLat, initialLong);
                            double arrivalDistance = CoordinatesUtils.distance(finalOldLat,
                                    finalOldLong, finalLat, finalLong);
                            if (departureDistance < 5 || arrivalDistance < 5) {
                                for(ArrayList<Ship> shipPairArray : shipPairArrayList) {
                                    // Falta ver se par de ships já foi adicionado
                                }
                                pairArrayList = new ArrayList<>();
                                pairArrayList.add(ship);
                                pairArrayList.add(newShip);
                                shipPairArrayList.add(pairArrayList);
                                System.out.println(departureDistance);
                                System.out.println(arrivalDistance);
                                System.out.println();
                            }
                        }
//                    if (i == 0) {
//                        oldShip = shipArrayList.get(i);
//                    } else {
//                        ArrayList<ShipData> oldData = oldShip.getDynamicShip();
//                        double initialOldLat = oldData.get(0).getLatitude();
//                        double initialOldLong = oldData.get(0).getLongitude();
//
//                        double finalOldLat = oldData.get(oldData.size() - 1).getLatitude();
//                        double finalOldLong = oldData.get(oldData.size() - 1).getLongitude();
//
//                        ArrayList<ShipData> shipData = shipArrayList.get(i).getDynamicShip();
//                        double initialLat = shipData.get(0).getLatitude();
//                        double initialLong = shipData.get(0).getLongitude();
//
//                        double finalLat = shipData.get(shipData.size() - 1).getLatitude();
//                        double finalLong = shipData.get(shipData.size() - 1).getLongitude();
//
//                        double departureDistance = CoordinatesUtils.distance(initialOldLat,
//                                initialOldLong, initialLat, initialLong);
//                        double arrivalDistance = CoordinatesUtils.distance(finalOldLat,
//                                finalOldLong, finalLat, finalLong);
//
//                        System.out.println("Old Ship MMSI: " + oldShip.getMmsi() +
//                                " Ship MMSI: " + shipArrayList.get(i).getMmsi());
//                        System.out.println("Departure: " + departureDistance);
//                        System.out.println("Arrival: " + arrivalDistance);
//                        oldShip = shipArrayList.get(i);


                    }
                }
                for (ArrayList<Ship> pairArray : shipPairArrayList) {
                    System.out.println(pairArray.get(0));
                    System.out.println(pairArray.get(1));
                    System.out.println();
                }
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
                if (csBST.find(new ShipCallSign(callSign)) != null) {
                    ShipCallSign ship = csBST.find(new ShipCallSign(callSign));
                    this.shipMenu(ship);
                } else {
                    System.out.println("Ship not found");
                }
                break;
            case 2:
                System.out.println("Please insert ship IMO to search:");
                String imo = scan.nextLine();
                if (imoBST.find(new ShipIMO(Integer.parseInt(imo))) != null) {
                    ShipIMO ship = imoBST.find(new ShipIMO(Integer.parseInt(imo)));
                    this.shipMenu(ship);
                } else {
                    System.out.println("Ship not found");
                }
                break;
            case 3:
                System.out.println("Please insert ship MMSI to search:");
                String mmsi = scan.nextLine();
                if (mmsiBST.find(new ShipMMSI(Integer.parseInt(mmsi))) != null) {
                    ShipMMSI ship = mmsiBST.find(new ShipMMSI(Integer.parseInt(mmsi)));
                    this.shipMenu(ship);
                } else {
                    System.out.println("Ship not found");
                }
                break;
        }
    }

    private void shipMenu(Ship ship) {
        System.out.println("Please make a selection: ");
        System.out.println("2) Show all ship movements");
        System.out.println("1) Show ship information");
        System.out.println("0) Exit");
        int choice = getInput(3);
        switch (choice) {
            case 0:
                this.exit();
                break;
            case 1:
                System.out.println(ship.toString());
                break;
            case 2:
                System.out.println("Ship MMSI: " + ship.getMmsi());
                for (ShipData data : ship.getDynamicShip()) {
                    System.out.println(data.toString());
                }
                break;
        }
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
