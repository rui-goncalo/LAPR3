package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.tree.AVL;
import lapr.project.tree.KDTree;
import lapr.project.utils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rui Gonçalves - 1191831
 * @author João Teixeira - 1180590
 */
public class Menu {

    private static final String BIG_SHIP_FILE = "src/data/bships.csv";
    private static final String SMALL_SHIP_FILE = "src/data/sships.csv";
    private static final String BIG_PORTS_FILE = "src/data/bports.csv";
    private static final String SMALL_PORTS_FILE = "src/data/sports.csv";

    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static ArrayList<Port> portsArray = new ArrayList<>();

    private static final AVL<ShipMMSI> mmsiAVL = new AVL<>();
    private static final AVL<ShipIMO> imoAVL = new AVL<>();
    private static final AVL<ShipCallSign> csAVL = new AVL<>();

    private static final KDTree<Port> portTree = new KDTree<>();

    private static Ship currentShip = null;

    public static void mainMenu() {
        int choice;
            do {
                String[] options = {"Exit\n", "Imports", "Manages"};
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
                            System.out.println("Please import Ships and Ports first.");
                            break;
                        }
                        menuManageShips();
                        break;
                }

            } while (choice != 0);
    }

    private static void menuImport() {
        int choice;

        String[] options = {"Go Back\n", "Small Ship File", "Big Ship File", "Small Ports File", "Big Ports File", "Custom File"};
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
                    shipArray = CSVReaderUtils.readShipCSV(SMALL_SHIP_FILE);
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
                    shipArray = CSVReaderUtils.readShipCSV(BIG_SHIP_FILE);
                    insertShips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                if (!portsArray.isEmpty()) {
                    portsArray.clear();
                }
                try {
                    portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
                    insertPorts();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                if (!portsArray.isEmpty()) {
                    portsArray.clear();
                }
                try {
                    portsArray = CSVReaderUtils.readPortCSV(BIG_PORTS_FILE);
                    insertPorts();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                retrieveFilePath();
                break;
        }
    }

    private static void insertPorts() {
        if (!portsArray.isEmpty()) {
            portsArray.clear();
        }

        if (portsArray == null) menuImport();

        List<KDTree.Node<Port>> nodesPorts = new ArrayList<>();
        for (Port port : portsArray) {
            KDTree.Node<Port> node = new KDTree.Node<>(port.getLat(), port.getLon(), port);
            nodesPorts.add(node);
        }
        portTree.buildTree(nodesPorts);
    }


    private static void menuManageShips() {
        int choice;

        do {

            String[] options = {"Go Back\n", "Show all Ships", "Search by Ship", "Search Ship Pairs",
                    "Create Summary of Ships", "Get TOP N Ships", "Get Nearest Port"};
            printMenu("Manage Ships", options, true);
            choice = getInput("Please make a selection", 3);

            Scanner sc = new Scanner(System.in);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    for (Ship ship : Menu.mmsiAVL.inOrder()) {
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

                    LocalDateTime date;

                    System.out.print(" > Please insert ship's CallSign:");
                    String callSign = sc.nextLine();
                    if (csAVL.find(new ShipCallSign(callSign)) != null) {
                        currentShip = csAVL.find(new ShipCallSign(callSign));
                        date = DateMenu.readDate(sc, "Insert date: ");
                        ShipData data = currentShip.getDataByDate(date);
                        if (data != null) {
                            Port nearestPort = portTree.findNearestNeighbour(
                                    data.getLatitude(),
                                    data.getLongitude());
                            System.out.println(nearestPort.getName());
                        }

                    } else {
                        System.out.println("Ship not found");
                    }


                    break;
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
                    System.out.print("Please insert ship's MMSI: ");
                    String mmsi = scan.nextLine();
                    if (mmsiAVL.find(new ShipMMSI(Integer.parseInt(mmsi))) != null) {
                        Menu.currentShip = mmsiAVL.find(new ShipMMSI(Integer.parseInt(mmsi)));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 2:
                    System.out.print("Please insert ship's IMO: ");
                    String imo = scan.nextLine();
                    if (imoAVL.find(new ShipIMO(Integer.parseInt(imo))) != null) {
                        Menu.currentShip = imoAVL.find(new ShipIMO(Integer.parseInt(imo)));
                        menuShowShip();
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 3:
                    System.out.print("Please insert ship's CallSign:");
                    String callSign = scan.nextLine();
                    if (csAVL.find(new ShipCallSign(callSign)) != null) {
                        Menu.currentShip = csAVL.find(new ShipCallSign(callSign));
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
            Menu.shipArray = CSVReaderUtils.readShipCSV(path);
            insertShips();
        } catch (Exception e) {
            System.out.println("> Invalid path, please try again.");
            retrieveFilePath();
        }
    }

    private static void insertShips() {
        for (Ship ship : Menu.shipArray) {
            Menu.mmsiAVL.insert(new ShipMMSI(ship));
            Menu.imoAVL.insert(new ShipIMO(ship));
            Menu.csAVL.insert(new ShipCallSign(ship));
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