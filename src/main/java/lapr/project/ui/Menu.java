package lapr.project.ui;

import lapr.project.data.MakeDBConnection;
import lapr.project.model.*;
import lapr.project.structures.AVL;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.structures.KDTree;
import lapr.project.utils.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author Rui Gonçalves - 1191831
 * @author João Teixeira - 1180590
 */
public class Menu {

    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    private static final String BIG_SHIP_FILE = "src/data/bships.csv";
    private static final String SMALL_SHIP_FILE = "src/data/sships.csv";
    private static final String BIG_PORTS_FILE = "src/data/bports.csv";
    private static final String SMALL_PORTS_FILE = "src/data/sports.csv";
    private static final String BORDERS_FILE = "src/data/borders.csv";
    private static final String COUNTRIES_FILE = "src/data/countries.csv";
    private static final String SEADISTS_FILE = "src/data/seadists.csv";

    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static ArrayList<Port> portsArray = new ArrayList<>();
    private static ArrayList<Country> countriesArray = new ArrayList<>();
    private static ArrayList<Border> borderArray = new ArrayList<>();

    private static final AVL<ShipMMSI> mmsiAVL = new AVL<>();
    private static final AVL<ShipIMO> imoAVL = new AVL<>();
    private static final AVL<ShipCallSign> csAVL = new AVL<>();

    private static final KDTree<Port> portTree = new KDTree<>();

    private static Ship currentShip = null;

    private static AdjacencyMatrixGraph<String, Integer> capitalMatrix = new AdjacencyMatrixGraph<>();
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();


    /**
     * Opens the main menu with all the options for users.
     */
    public static void mainMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            do {
                String[] options = {"Exit\n", "Imports", "Management", "DataBase Query"};
                printFrontMenu("Main Menu", options, true);
                insertPortsIntoMatrix();
                choice = getInput("Please make a selection: ", sc);

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        menuImport(sc);
                        break;
                    case 2:
                        if (shipArray.isEmpty() && portsArray.isEmpty()) {
                            System.out.println(ANSI_RED_BACKGROUND
                                    + "Please import Ships and Ports first."
                                    + ANSI_RESET);
                            break;
                        }
                        if (shipArray.isEmpty()) {
                            System.out.println(ANSI_RED_BACKGROUND
                                    + "Please import Ships first."
                                    + ANSI_RESET);
                            break;
                        }
                        if (portsArray.isEmpty()) {
                            System.out.println(ANSI_RED_BACKGROUND
                                    + "Please import Ports first."
                                    + ANSI_RESET);
                            break;
                        }
                        menuManageShips(sc);
                        break;
                    case 3:
                        dbQueriesMenu(sc);
                        break;
                }

            } while (choice != 0);
        }
    }

    /**
     * Opens the menu for imports.
     *
     * @param sc scanner to read input from the user
     */
    private static void menuImport(Scanner sc) {
        int choice;

        String[] options = {"Go Back\n", "Small Ship File CSV", "Big Ship File CSV\n", "Small Ports File CSV", "Big Ports File CSV",};
        printMenu("Import Ships", options, true);

        choice = getInput("Please make a selection: ", sc);

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
        }
    }

    /**
     * Opens the menu for managing Ships.
     *
     * @param sc scanner to read input from the user
     */
    private static void menuManageShips(Scanner sc) {
        int choice;

        do {

            String[] options = {"Go Back\n", "Show all Ships", "Search by Ship", "Search Ship Pairs\n",
                    "Create Summary of Ships", "View Summaries by Ship", "Get TOP N Ships\n", "Get Nearest Port", "Querys DB"};
            printMenu("Manage Ships", options, true);
            choice = getInput("Please make a selection: ", sc);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    for (Ship ship : Menu.mmsiAVL.inOrder()) {
                        ship.printShip();
                    }
                    break;
                case 2:
                    menuSearch(sc);
                    break;
                case 3:
                    ArrayList<Calculator.ShipPair> pairs = Calculator.searchShipPairs(shipArray);
                    for (Calculator.ShipPair shipPair : pairs) {
                        System.out.println(shipPair.getFirstShip().getMmsi() + " + " + shipPair.getSecondShip().getMmsi());
                    }
                    break;
                case 4:
                    generateSummaries();
                    break;
                case 5:
                    Ship currentShip = null;
                    choice = getInput("Ship's MMSI: ", sc);
                    for (Ship ship : shipArray) {
                        if (ship.getMmsi() == choice) {
                            currentShip = ship;
                        }
                    }

                    if (currentShip != null) {
                        if (currentShip.getSummary() != null) {
                            printSummary(currentShip.getSummary());
                        } else {
                            System.out.println(ANSI_RED_BACKGROUND
                                    + "Please import Summaries first."
                                    + ANSI_RESET);
                        }
                    } else {
                        System.out.println(ANSI_RED_BACKGROUND
                                + "Sorry, no Ship found with this MMSI."
                                + ANSI_RESET);
                    }
                    break;
                case 6:
                    if (shipArray.get(0).getSummary() == null) {
                        System.out.println("Summaries must be created first.");
                        break;
                    }
                    choice = getInput("TOP N Ships:\nN = ", sc);
                    getTopNShips(choice);
                    break;
                case 7:
                    Scanner scanner = new Scanner(System.in);
                    System.out.print(" > Please insert ship's CallSign: ");
                    String callSign = scanner.nextLine();

                    if (csAVL.find(new ShipCallSign(callSign)) != null) {
                        currentShip = csAVL.find(new ShipCallSign(callSign));
                        LocalDateTime date = DateMenu.readDate(scanner, "Insert date: ");
                        ShipData data = currentShip.getDataByDate(date);

                        if (data != null) {
                            Port nearestPort = portTree.findNearestNeighbour(
                                    data.getLatitude(),
                                    data.getLongitude());
                            System.out.println("Nearest Port: " + nearestPort.getName());
                        }
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 8:
                    //queriesBDDAD(sc);
                    //dbQueriesMenu(sc);
                    break;
            }

        } while (choice != 0);
    }

    /**
     * Opens the menu for searching ships.
     *
     * @param sc scanner to read input from the user
     */
    private static void menuSearch(Scanner sc) {
        int choice;
        Scanner scan = new Scanner(System.in);

        do {
            String[] options = {"Go Back\n", "Search by MMSI", "Search by IMO", "Search by Call Sign"};
            printMenu("Search Ship", options, true);
            choice = getInput("Please make a selection: ", sc);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.print("Please insert ship's MMSI: ");
                    String mmsi = scan.nextLine();
                    if (mmsiAVL.find(new ShipMMSI(Integer.parseInt(mmsi))) != null) {
                        Menu.currentShip = mmsiAVL.find(new ShipMMSI(Integer.parseInt(mmsi)));
                        menuShowShip(sc);
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 2:
                    System.out.print("Please insert ship's IMO: ");
                    String imo = scan.nextLine();
                    if (imoAVL.find(new ShipIMO(Integer.parseInt(imo))) != null) {
                        Menu.currentShip = imoAVL.find(new ShipIMO(Integer.parseInt(imo)));
                        menuShowShip(sc);
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 3:
                    System.out.print("Please insert ship's CallSign:");
                    String callSign = scan.nextLine();
                    if (csAVL.find(new ShipCallSign(callSign)) != null) {
                        Menu.currentShip = csAVL.find(new ShipCallSign(callSign));
                        menuShowShip(sc);
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;

            }
        } while (choice != 0);
    }

    /**
     * Opens the menu for acessing ship information.
     *
     * @param sc scanner to read input from the user
     */
    private static void menuShowShip(Scanner sc) {
        int choice;

        do {

            String[] options = {"Go Back\n", "Current Ship Information", "Current Ship Records"};
            printMenu("Show Ship", options, true);
            choice = getInput("Please make a selection: ", sc);


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


    /**
     * Opens the menu for database queries.
     *
     * @param sc scanner to read input from the user
     */
    private static void dbQueriesMenu(Scanner sc) {
        int choice;
        Scanner scan = new Scanner(System.in);

        do {
            String[] options = {"Go Back\n", "Current situation of a specific container", "Containers to be offloaded in the next Port"};
            printMenu("Show Ships", options, true);
            choice = getInput("Please make a selection: ", sc);

            switch (choice) {
                case 1:
                    System.out.println("Container Number: ");

                    FunctionsDB.getCurrentContainerInfo(scan.nextInt());
                    break;
                case 2:
                    int mmsi = getInput("Insert Ship's MMSI: ", sc);
                    if (mmsiAVL.find(new ShipMMSI(mmsi)) != null) {
                        currentShip = mmsiAVL.find(new ShipMMSI(mmsi));
                        FunctionsDB.getGetContainersOffloaded(currentShip);
                    }
                    break;
                case 0:
//                        try {
//                            connection.close();
//                        } catch (SQLException e) {
//                            System.out.println("An error occurred: " + e);
//                        }
                    break;
                default:
                    System.out.println("Sorry, this option is invalid.");
                    break;
            }
        } while (choice != 0);
    }


    private static void queriesBDDAD(Scanner sc) {
        int choice;
        Connection connection = MakeDBConnection.makeConnection();
        if (connection != null) {
            do {
                choice = getInput("Option: ", sc);

                switch (choice) {
                    case 1:
                        try (Statement statement = connection.createStatement()) {
                            ResultSet rs = statement.executeQuery(FunctionsDB.us205(sc));
                            while (rs.next()) {
                                System.out.println("Container ID: " + rs.getInt(1) +
                                        ", Payload: " + rs.getDouble(2) +
                                        ", Tare: " + rs.getDouble(3) +
                                        ", Gross: " + rs.getDouble(4) +
                                        ", ISO Code: " + rs.getInt(5) +
                                        ", Container Type: " + rs.getInt(6) +
                                        ", Container Position X: " + rs.getInt(7) +
                                        ", Container Position Y: " + rs.getInt(8) +
                                        ", Container Position Z: " + rs.getInt(9) +
                                        ", Port Name: " + rs.getString(10) +
                                        ", Country: " + rs.getString(11));
                                if (rs.getInt(12) != 0) {
                                    System.out.println(", Temperature: " + rs.getInt(12));
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                        break;
                }
            } while (choice != 0);
        }
    }


    /**
     * Utility to print the front menu in an organized manner.
     *
     * @param title    menu title to be shown
     * @param options  number of options
     * @param showExit whether to show exit option or not
     */
    private static void printFrontMenu(String title, String[] options, boolean showExit) {

        System.out.println(
                "\n+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n" +
                        "           CARGO APP 103 > " + title +
                        "\n+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

        for (int i = 0; i < options.length; i++) {
            if (i == 0 && showExit || i > 0) {
                System.out.println("  " + i + " > " + options[i]);
            }
        }

        System.out.println(ANSI_YELLOW
                + "\n   Note: Please import ships and ports first."
                + ANSI_RESET);
        System.out.println("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

    }

    /**
     * Utility to print the menus in an organized manner.
     *
     * @param title    menu title to be shown
     * @param options  number of options
     * @param showExit whether to show exit option or not
     */
    private static void printMenu(String title, String[] options, boolean showExit) {

        System.out.println(
                "\n+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n" +
                        "  CARGO APP 103 > " + title +
                        "\n+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

        for (int i = 0; i < options.length; i++) {
            if (i == 0 && showExit || i > 0) {
                System.out.println("  " + i + " > " + options[i]);
            }
        }

        System.out.println("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

    }

    /**
     * Prompts for and veriies the user input.
     *
     * @param prompt Prompt to be shown to the user
     * @param sc     user input
     * @return user input
     */
    public static int getInput(String prompt, Scanner sc) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.next();
            System.out.print(prompt);
        }

        return sc.nextInt();
    }

    /**
     * Inserts the ships from shipArray into the trees.
     */
    private static void insertShips() {
        for (Ship ship : Menu.shipArray) {
            Menu.mmsiAVL.insert(new ShipMMSI(ship));
            Menu.imoAVL.insert(new ShipIMO(ship));
            Menu.csAVL.insert(new ShipCallSign(ship));
        }
    }

    /**
     * Generates ship summaries.
     */
    private static void generateSummaries() {
        for (Ship ship : shipArray) {
            ship.setSummary(new Summary(ship));
        }
        shipArray.sort(new ShipCompare().reversed());
        System.out.println("Summaries created.");
    }

    /**
     * Returns the top n ships in most distance travelled.
     *
     * @param n number of ships to return.
     */
    private static void getTopNShips(int n) {
        if (n > shipArray.size()) {
            System.out.println("The chosen number is great than the amount of ships available.");
            return;
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            Ship current = shipArray.get(i);
            System.out.printf("- %.2fkm > %s\n", current.getSummary().getTravelledDistance(), current.getName());
        }
    }

    /**
     * Inserts ports from the portsArray into the KDtree.
     *
     * @return false if collection is empty or true if it sucessfully inserted
     */
    private static boolean insertPorts() {

        if (portsArray == null) return false;

        List<KDTree.Node<Port>> nodesPorts = new ArrayList<>();
        for (Port port : portsArray) {
            KDTree.Node<Port> node = new KDTree.Node<>(port.getLatitude(), port.getLongitude(), port);
            nodesPorts.add(node);
        }
        portTree.buildTree(nodesPorts);
        return true;
    }

    /**
     * Prints a ship's summary.
     *
     * @param summaryShip ship summary to be printed
     */
    private static void printSummary(Summary summaryShip) {
        System.out.println(
                "\nDeparture Latitude: " + summaryShip.getDepartLat() +
                        "\nDeparture Longitude: " + summaryShip.getDepartLon() +
                        "\nArrival Latitude: " + summaryShip.getArrLat() +
                        "\nArrival Longitude: " + summaryShip.getArrLon() +
                        "\nDeparture Time: " + summaryShip.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                        "\nArrival Schedule: " + summaryShip.getArrivalTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                        "\nTravel's Time: " + summaryShip.getDays() + " days " + summaryShip.getHours() + " hours " + summaryShip.getMinutes() + " minutes" +
                        "\nMax SOG: " + summaryShip.getMaxSog() +
                        "\nMean SOG: " + summaryShip.getMeanSog() +
                        "\nMax COG: " + summaryShip.getMaxCog() +
                        "\nMean COG: " + summaryShip.getMeanCog() +
                        "\nTravelled distance: " + summaryShip.getTravelledDistance() +
                        "\nDelta distance: " + summaryShip.getDeltaDistance()
        );
    }

    private static void insertCapitalsIntoMatrix() {
        countriesArray = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
        borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);

        for (Country country : countriesArray) {
            capitalMatrix.insertVertex(country.getCapital());
        }

        for(Border border : borderArray) {
            String capital1 = border.getCountry1().getCapital();
            String capital2 = border.getCountry2().getCapital();

            capitalMatrix.insertEdge(capital1, capital2, 1);
        }
        System.out.println("Matriz criada");

    }

    private static void insertPortsIntoMatrix() {
        portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
        countriesArray = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);

        for (Port port : portsArray) {
            portMatrix.insertVertex(port);
        }
        HashMap<String, Double> distanceMap = new HashMap<>();
        for (int i = 0; i < portsArray.size() - 1; i++) { // sempre que for um porto diferente, é criado um novo HashMap
            distanceMap = new HashMap<>();
            for (int j = i + 1; j < portsArray.size(); j++) {
                Port firstPort = portsArray.get(i);
                Port secondPort = portsArray.get(j);
                if (firstPort.getCountry().equals(secondPort.getCountry())) {
                    portMatrix.insertEdge(firstPort, secondPort, 1);
                } else {
                    double distanceToPort = Calculator.getDistance(firstPort.getLatitude(), firstPort.getLongitude(),
                            secondPort.getLatitude(), secondPort.getLongitude());
                    distanceMap.put(secondPort.getName(), distanceToPort);
                }
            }
            ArrayList<Double> distanceArray = new ArrayList<>();

            HashMap<String, Double> orderedDistance = distanceMap
                    .entrySet()
                    .stream().sorted(Comparator.comparingDouble(e -> e.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> {
                                throw new AssertionError();
                            },
                            LinkedHashMap::new));

            orderedDistance.entrySet().forEach(System.out::println);

            for (Double distance : distanceArray) {
                for (Entry<String, Double> entry : distanceMap.entrySet()) {
                    if (entry.getValue() == distance) {
                        orderedDistance.put(entry.getKey(), distance);
                    }
                }
            }
            for (Entry<String, Double> entry : orderedDistance.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            System.out.println("fggfdgfd");
        }

        Port nearestPort = null;
        double distance = 0.0;
        for(Country country : countriesArray) {
            for (Port port : portsArray) {
                if(port.getCountry().equals(country.getName())) {
                    if (distance == 0.0) {
                        distance = Calculator.getDistance(country.getLatitude(), country.getLongitude(),
                                port.getLatitude(), port.getLongitude());
                        nearestPort = port;
                    } else {
                        double distanceToCapital = Calculator.getDistance(country.getLatitude(), country.getLongitude(),
                                port.getLatitude(), port.getLongitude());
                        if (distanceToCapital < distance) {
                            distance = distanceToCapital;
                            nearestPort = port;
                        }
                    }
                }
            }
            if (nearestPort != null) {
                portMatrix.insertEdge(nearestPort, nearestPort, 1);
            }
        }

        System.out.println("Matriz criada");

    }

}