package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.CoordinatesUtils;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private static final String BIG_SHIP_FILE = "src/data/bships.csv";
    private static final String SMALL_SHIP_FILE = "src/data/sships.csv";

    private static final int MAX_YEAR = 2022;
    private static final int MIN_YEAR = 0;
    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static final BST<ShipMMSI> mmsiBST = new BST<>();
    private static final BST<ShipIMO> imoBST = new BST<>();
    private static final BST<ShipCallSign> csBST = new BST<>();
    private static Ship currentShip = null;

    public static void mainMenu() {

        int choice;

        do {

            String[] options = {"Exit", "Import Menu", "Manage Ships", "Get the top-N ships"};
            printMenu("Main Menu", options, true);
            choice = getInput("Please make a selection", 3);

            switch (choice) {
                case 0: break;
                case 1:
                    menuImport();
                    break;
                case 2:
                    menuManageShips();
                    break;
                case 3:
                    menuTopShips();
                    break;
            }

        } while (choice != 0);
    }

    private static void menuImport() {
        int choice;

        String[] options = {"Go Back", "Small Ship File", "Big Ship File", "Custom File"};
        printMenu("Import Ships", options, true);
        choice = getInput("Please make a selection",3);

        switch (choice) {
            case 0: break;
            case 1:
                try {
                    shipArray = CSVReaderUtils.readCSV(SMALL_SHIP_FILE);
                    insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    shipArray = CSVReaderUtils.readCSV(BIG_SHIP_FILE);
                    insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                retrieveFilePath();
                break;
        }

    }

    private static void menuManageShips() {
        int choice;

        do {

            String[] options = {"Go Back", "Show all ships", "Search ship"};
            printMenu("Manage Ships", options, true);
            choice = getInput("Please make a selection",3);

            switch (choice) {
                case 0: break;
                case 1:
                    for (Ship ship : Menu.mmsiBST.inOrder()) {
                        ship.printShip();
                    }
                    break;
                case 2:
                    menuSearch();
                    break;
            }

        } while (choice != 0);
    }

    private static void menuSearch() {
        int choice;
        Scanner scan = new Scanner(System.in);
        boolean showShip;

        do {
            String[] options = {"Go Back", "Search by MMSI", "Search by IMO", "Search by Call Sign"};
            printMenu("Search Ship", options, true);
            choice = getInput("Please make a selection",4);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.print(" > Please insert ship's MMSI: ");
                    String mmsi = scan.nextLine();
                    if (mmsiBST.find(new ShipMMSI(Integer.parseInt(mmsi))) != null) {
                        Menu.currentShip = mmsiBST.find(new ShipMMSI(Integer.parseInt(mmsi)));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 2:
                    System.out.print(" > Please insert ship's IMO: ");
                    String imo = scan.nextLine();
                    if (imoBST.find(new ShipIMO(Integer.parseInt(imo))) != null) {
                        Menu.currentShip = imoBST.find(new ShipIMO(Integer.parseInt(imo)));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 3:
                    System.out.print(" > Please insert ship's CallSign:");
                    String callSign = scan.nextLine();
                    if (csBST.find(new ShipCallSign(callSign)) != null) {
                        Menu.currentShip = csBST.find(new ShipCallSign(callSign));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;

                case 4:
                    ArrayList<Ship> ships = new ArrayList<>();
                    for (Ship ship : mmsiBST.inOrder()) {
                        ArrayList<ShipData> shipData = ship.getDynamicShip();

                        double distance = CoordinatesUtils.distance(shipData.get(0).getLatitude(),
                                shipData.get(0).getLongitude(),
                                shipData.get(shipData.size() - 1).getLatitude(),
                                shipData.get(shipData.size() - 1).getLongitude());

                        if(distance > 10) {
                            ships.add(ship);
                            System.out.println("MMSI: " + ship.getMmsi());
                            System.out.println(distance);
                        }
                    }

                    ArrayList<ArrayList<Ship>> shipPairArrayList = new ArrayList<>();
                    ArrayList<Ship> pairArrayList;
                    for (int i = 0; i < ships.size(); i++) {
                        for (int j = 1; j < ships.size(); j++) {
                            Ship ship = ships.get(i);

                            Ship newShip = ships.get(j);
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
                                    for (ArrayList<Ship> shipPairArray : shipPairArrayList) {
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
                        }
                    }
            }
        } while (choice != 0);
    }

    private static void menuShowShip() {
        int choice;

        do {

            String[] options = {"Go Back", "Show Ship Information", "Show Ship Records"};
            printMenu("Show Ship", options, true);
            choice = getInput("Please make a selection",3);


            switch (choice) {
                case 0: break;
                case 2:
                    System.out.println("Ship MMSI: " + Menu.currentShip.getMmsi());
                    for (ShipData data : Menu.currentShip.getDynamicShip()) {
                        System.out.println(data.toString());
                    }
                    //menuFilterRecordsByDate();
                    break;
                case 1:
                    System.out.println(Menu.currentShip.toString());
                    break;
                //TODO:filter by period/date
            }
        } while (choice != 0);
    }

    /*public static void menuFilterRecordsByDate() {
        int choice;
        // TODO: Terminar
        do {

            String[] options = {"Go Back", "Show All", "Filter by Date", "Filter by Period"};
            printMenu("Filter by Date", options, true);
            choice = getInput("Please make a selection",3);


            switch (choice) {
                case 0: break;
                case 1:
                    System.out.println("Ship MMSI: " + Menu.currentShip.getMmsi());
                    for (ShipData data : Menu.currentShip.getDynamicShip()) {
                        System.out.println(data.toString());
                    }
                    break;
                case 2:
                    System.out.println("Ship MMSI: " + Menu.currentShip.getMmsi());
                    for (ShipData data : Menu.currentShip.getDynamicShip()) {
                        if (data.getDateTime().equals(DATA INSERIDA)) System.out.println(data.toString());
                    }
                    break;
                case 3:
                    System.out.println("Ship MMSI: " + Menu.currentShip.getMmsi());
                    for (ShipData data : Menu.currentShip.getDynamicShip()) {
                        if (data.getDateTime().equals(DATA INSERIDA)) System.out.println(data.toString());
                    }
                    break;
            }
        } while (choice != 0);
    }*/

    private static int getInput(String prompt, int optionCount) {
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > optionCount) {
            try {
                System.out.print(" > " + prompt + ": " );
                choice = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        return choice;
    }

    private static void retrieveFilePath() {
        System.out.print(" > Please insert file path: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        try {
            Menu.shipArray = CSVReaderUtils.readCSV(path);
            insertShips();
        } catch (Exception e) {
            System.out.println("Invalid path, please try again.");
            retrieveFilePath();
        }
    }

    private static void insertShips() {
        for (Ship ship : Menu.shipArray) {
            Menu.mmsiBST.insert(new ShipMMSI(ship));
            Menu.imoBST.insert(new ShipIMO(ship));
            Menu.csBST.insert(new ShipCallSign(ship));
        }
    }

    private static void printMenu(String title, String[] options, boolean showExit) {

        System.out.println(
                "\n\n\n+-------------------------------+\n" +
                        "  CARGO APP 103 > " + title +
                        "\n+-----------------------------+");

        for(int i = 0; i < options.length; i++) {
            if (i == 0 && showExit || i > 0) {
                System.out.println("  " + i + " - " + options[i]);
            }
        }

        System.out.println("+-----------------------------+");

    }

/*
    private static ArrayList<ShipData> getTimePeriod(String date) {

        for(ShipData data : currentShip.getDynamicShip()) {
            if (data.getDateTime().equals(date)) {
                System.out.println(data.toString());
            }
        }
        return;
    }*/
    
    private static void menuTopShips() {
        int choice;
        int n = 0;
        Scanner scan = new Scanner(System.in);
        int initialYear = -1;
        int initialMonth = 13;
        int initialDay = 35;
        LocalDateTime initialDate = null;
        int finalYear = -1;
        int finalMonth = 13;
        int finalDay = 35;
        LocalDateTime finalDate = null;
        boolean tryAgain = true;
        
        while (tryAgain == true) {
            //Number of ships
            System.out.println("How many ships?");
            while (n == 0) {
                System.out.println("Choose a value above 0.");
                n = Integer.parseInt(scan.nextLine());
            }

            //Initial Date
            System.out.println("Input initial date or press '0' for none.");
            System.out.println("Which starting year?");
            choice = Integer.parseInt(scan.nextLine());
            while (choice != 0) {
                while (choice < 0 || choice > 2022) {
                    System.out.println("Choose a valid year.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                initialYear = choice;

                System.out.println("Which starting month?");
                choice = Integer.parseInt(scan.nextLine());
                while (choice < 1 || choice > 12) {
                    System.out.println("Choose a valid month.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                initialMonth = choice;
                
                System.out.println("Which starting day?");
                choice = Integer.parseInt(scan.nextLine());
                while (choice < 1 || choice > 31) {
                    System.out.println("Choose a valid day.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                initialDay = choice;
                try {
                    initialDate = LocalDateTime.of(initialYear, initialMonth, initialDay, 0, 0);
                } catch (DateTimeException e) {
                    System.out.println("INVALID date please input the values again.");
                    continue;
                }
                initialDate = LocalDateTime.of(initialYear, initialMonth, initialDay, 0, 0);
                choice = 0;
            }
            
            //Final Date
            System.out.println("Input final date or press '0' for none.");
            System.out.println("Which final year?");
            choice = Integer.parseInt(scan.nextLine());
            while (choice != 0) {
                while (choice < 0 || choice > 2022) {
                    System.out.println("Choose a valid year.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                finalYear = choice;

                System.out.println("Which final month?");
                choice = Integer.parseInt(scan.nextLine());
                while (choice < 1 || choice > 12) {
                    System.out.println("Choose a valid month.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                finalMonth = choice;
                
                System.out.println("Which final day?");
                choice = Integer.parseInt(scan.nextLine());
                while (choice < 1 || choice > 31) {
                    System.out.println("Choose a valid day.");
                    choice = Integer.parseInt(scan.nextLine());
                }
                finalDay = choice;
                try {
                    finalDate = LocalDateTime.of(finalYear, finalMonth, finalDay, 0, 0);
                } catch (DateTimeException e) {
                    System.out.println("INVALID date please input the values again.");
                    continue;
                }
                finalDate = LocalDateTime.of(initialYear, initialMonth, initialDay, 0, 0);
            }
            
            if (initialDate != null || finalDate != null) {
                if (initialDate.isAfter(finalDate)) {
                    System.out.println("INVALID dates please input the values again.");
                    continue;
                }
            }
            
            System.out.printf("Number of Ships: %d%n", n);
            if(initialDate==null){
                System.out.println("Inital date: none");
            }else{
                System.out.printf("Initial date: %d%n", initialDate.toString());
            }
            if(finalDate==null){
                System.out.println("Final date: none");
            }else{
                System.out.printf("Final date: %d%n", initialDate.toString());
            }
            System.out.println("Review your inputs, and press '1' to continue or '0' to restart:");
            choice = Integer.parseInt(scan.nextLine());
            
            if(choice==1){
                tryAgain = false;
            }
        }
    }
}