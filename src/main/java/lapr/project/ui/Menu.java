package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.CoordinatesUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static final String BIG_SHIP_FILE = "src/data/bships.csv";
    private static final String SMALL_SHIP_FILE = "src/data/sships.csv";

    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static final BST<ShipMMSI> mmsiBST = new BST<>();
    private static final BST<ShipIMO> imoBST = new BST<>();
    private static final BST<ShipCallSign> csBST = new BST<>();
    private static Ship currentShip = null;

    public static void mainMenu() {

        int choice;

        do {

            String[] options = {"Exit", "Import Menu", "Manage Ships"};
            printMenu("Main Menu", options, true);
            choice = getInput("Please make a selection", 2);

            switch (choice) {
                case 0: break;
                case 1:
                    menuImport();
                    break;
                case 2:
                    menuManageShips();
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
                    System.out.println(" > Please insert ship's CallSign:");
                    String callSign = scan.nextLine();
                    if (csBST.find(new ShipCallSign(callSign)) != null) {
                        Menu.currentShip = csBST.find(new ShipCallSign(callSign));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
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
                    menuFilterRecordsByDate();
                    break;
                case 1:
                    System.out.println(Menu.currentShip.toString());
                    break;
                    //TODO:filter by period/date
            }
        } while (choice != 0);
    }

/*    public static void menuFilterRecordsByDate() {
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
                "  APP NAME > " + title +
                "\n+-----------------------------+");

        for(int i = 0; i < options.length; i++) {
            if (i == 0 && showExit || i > 0) {
                System.out.println("  " + i + " - " + options[i]);
            }
        }

        System.out.println("+-----------------------------+");

    }


}