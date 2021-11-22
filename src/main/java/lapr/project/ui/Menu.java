package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.BST;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.Calculator;
import lapr.project.utils.ShipCompare;
import lapr.project.utils.Summary;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Rui Gonçalves - 1191831
 * @author João Teixeira - 1180590
 */
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

            String[] options = {"Exit\n", "Import Ships", "Manage Ships"};
            printMenu("Main Menu", options, true);
            choice = getInput("Please make a selection", 3);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    menuImport();
                    break;
                case 2:
                    if (shipArray.isEmpty()) {
                        System.out.println("Please import ships first.");
                        break;
                    }
                    menuManageShips();
                    break;
            }

        } while (choice != 0);
    }

    private static void menuImport() {
        int choice;

        String[] options = {"Go Back\n", "Small Ship File", "Big Ship File", "Custom File"};
        printMenu("Import Ships", options, true);
        choice = getInput("Please make a selection", 3);

        switch (choice) {
            case 0:
                break;
            case 1:
                if (!shipArray.isEmpty()) {
                    shipArray.clear();
                }
                try {
                    shipArray = CSVReaderUtils.readCSV(SMALL_SHIP_FILE);
                    insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                if (!shipArray.isEmpty()) {
                    shipArray.clear();
                }
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

            String[] options = {"Go Back\n", "Show all Ships", "Search by Ship", "Search Ship Pairs", "Create Summary of Ships", "Get TOP N Ships", "Search Ship By Date"};
            printMenu("Manage Ships", options, true);
            choice = getInput("Please make a selection", 3);

            Scanner sc = new Scanner(System.in);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    for (Ship ship : Menu.mmsiBST.inOrder()) {
                        ship.printShip();
                    }
                    break;
                case 2:
                    menuSearch();
                    break;
                case 3:
                    ArrayList<Calculator.ShipPair> pairs = Calculator.searchShipPairs(shipArray);
                    for (Calculator.ShipPair shipPair : pairs) {
                        System.out.println(shipPair.getFirstShip().getMmsi() + " + " + shipPair.getSecondShip().getMmsi());
                    }
                    break;
                case 4:
                    generateReports();
                    break;
                case 5:
                    if (shipArray.get(0).getSummary() == null) {
                        System.out.println("Reports must be created first.");
                        break;
                    }
                    int option = readInt(sc, "TOP N Ships:\nN = ");
                    getTopNShips(option);
                    break;
                case 6:
                    LocalDateTime startDate = null;
                    LocalDateTime endDate = null;

                    sc.nextLine();
                    startDate = readDate(sc, "Start Date: ");
                    endDate = readDate(sc, "End Date: ");
            }

        } while (choice != 0);
    }

    private static void menuSearch() {
        int choice;
        Scanner scan = new Scanner(System.in);

        do {
            String[] options = {"Go Back\n", "> Search by MMSI", "> Search by IMO", "> Search by Call Sign"};
            printMenu("Search Ship", options, true);
            choice = getInput("Please make a selection", 4);

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

            }
        } while (choice != 0);
    }

    private static void menuShowShip() {
        int choice;

        do {

            String[] options = {"Go Back\n", "> Current Ship Information", "> Current Ship Records"};
            printMenu("Show Ship", options, true);
            choice = getInput("Please make a selection", 3);


            switch (choice) {
                case 0:
                    break;
                case 2:
                    System.out.println("Ship MMSI: " + Menu.currentShip.getMmsi());
                    for (ShipData data : Menu.currentShip.getDynamicShip()) {
                        System.out.println(data.toString());
                    }
                    break;
                case 1:
                    System.out.println(Menu.currentShip.toString());
                    break;
            }
        } while (choice != 0);
    }

    private static int getInput(String prompt, int optionCount) {
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > optionCount) {
            try {
                System.out.print(" > " + prompt + ": ");
                choice = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        return choice;
    }

    private static void retrieveFilePath() {
        if (!shipArray.isEmpty()) {
            shipArray.clear();
        }
        System.out.print(" > Please insert file path: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        try {
            Menu.shipArray = CSVReaderUtils.readCSV(path);
            insertShips();
        } catch (Exception e) {
            System.out.println("> Invalid path, please try again.");
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

        for (int i = 0; i < options.length; i++) {
            if (i == 0 && showExit || i > 0) {
                System.out.println("  " + i + " > " + options[i]);
            }
        }

        System.out.println("+-----------------------------+");

    }

    public static LocalDateTime readDate(Scanner sc, String msg) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.print("Format: dd/MM/yyyy HH:mm\n");
        System.out.print(msg);
        LocalDateTime dateTime;

        String str = sc.nextLine();

        try {
            dateTime = LocalDateTime.parse(str, format);
        } catch (Exception e) {
            System.out.print("Invalid date.\n");
            return null;
        }
        return dateTime;
    }

    public static int readInt(Scanner sc, String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.next();
            System.out.print(msg);
        }

        return sc.nextInt();
    }

    private static void generateReports() {
        for (Ship ship : shipArray) {
            ship.setSummary(new Summary(ship));
        }
        shipArray.sort(new ShipCompare().reversed());
        System.out.println("Reports created.");
    }

    private static void getTopNShips(int n) {
        if (n > shipArray.size()) {
            System.out.println("The chosen number is great than the amount of ships available.");
            return;
        }

        System.out.println();
        for (int i = 0; i < n; i++) {
            Ship current = shipArray.get(i);
            System.out.printf("- %.5fkm - %s\n", current.getSummary().getTravelledDistance(), current.getName());
        }
    }
}