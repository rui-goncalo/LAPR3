package lapr.project.ui;

import lapr.project.data.LoadDBFiles;
import lapr.project.data.MakeDBConnection;
import lapr.project.model.*;
import lapr.project.structures.AVL;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.structures.KDTree;
import lapr.project.utils.*;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private static final String BIG_SHIP_FILE = "data/bships.csv";
    private static final String SMALL_SHIP_FILE = "data/sships.csv";
    private static final String BIG_PORTS_FILE = "data/bports.csv";
    private static final String SMALL_PORTS_FILE = "data/sports.csv";

    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static ArrayList<Port> portsArray = new ArrayList<>();

    private static final AVL<ShipMMSI> mmsiAVL = new AVL<>();
    private static final AVL<ShipIMO> imoAVL = new AVL<>();
    private static final AVL<ShipCallSign> csAVL = new AVL<>();

    private static final KDTree<Port> portTree = new KDTree<>();
    private static Ship currentShip = null;

    private static AdjacencyMatrixGraph<String, Integer> capitalBordersMatrix = null;
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = null;

    /**
     * Opens the main menu with all the options for users.
     */
    public static void mainMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            do {
                String[] options = {"Exit\n", "Imports", "Management", "DataBase Queries"};
                printFrontMenu("Main Menu", options, true);
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
                        menuManageCargo(sc);
                        break;
                    case 3:
                        if (shipArray.isEmpty()) {
                            System.out.println(ANSI_RED_BACKGROUND
                                    + "Please import Ships and Ports first."
                                    + ANSI_RESET);
                            break;
                        }
                        dbQueriesMenu(sc);
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

        String[] options = {"Go Back\n", "Small Ship File CSV", "Big Ship File CSV", "Small Ports File CSV",
                "Big Ports File CSV\n", "Load Ships from Database", "Load Ports from Database",
                "Print Border Map"};

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
            case 5:
                if (!shipArray.isEmpty()) {
                    shipArray.clear();
                }
                shipArray = LoadDBFiles.readShipDB();
                System.out.println("Ships are imported with success");
                break;
            case 6:
                if (!portsArray.isEmpty()) {
                    portsArray.clear();
                }
                portsArray = LoadDBFiles.readPortDB();
                System.out.println("Ports are imported with success");
                break;
            case 8:
                //FunctionsGraph.getBorderMap();
        }
    }

    /**
     * Opens the menu for managing Ships.
     *
     * @param sc scanner to read input from the user
     */
    private static void menuManageCargo(Scanner sc) {
        int choice;

        do {

            String[] options = {"Go Back\n", "Show all Ships", "Search by Ship", "Search Ship Pairs\n",
                    "Create Summary of Ships", "View Summaries by Ship", "Get TOP N Ships\n",
                    "Get Nearest Port\n", "Print N Closest Port Matrix", "Print Ports Closest to Capital - same country - Matrix",
                    "Print Capital and Borders Matrix"};
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
                            System.out.println("Nearest Port: " + nearestPort.getName() + "\n" + "Latitude: " + nearestPort.getLatitude() + "\n" + "Longitude: " + nearestPort.getLongitude());
                        }
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case 8:
                    int number = getInput("Insert N Ports: \n", sc);
                    System.out.println(FunctionsGraph.getNClosestPortMatrix(number).toString());
                    break;
                case 9:
                    System.out.println(FunctionsGraph.getClosestPortsFromCapital().toString());
                    break;
                case 10:
                    System.out.println(FunctionsGraph.getCapitalBordersMatrix().toString());
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
            String[] options = {"Go Back\n", "Current situation of a specific container",
                    "Containers to be offloaded in the next Port",
                    "Containers to be loaded in the next Port",
                    "C.Manifest transported during a given year and the average number of Containers per Manifest",
                    "Occupancy rate of a given Ship for a given Cargo Manifest.",
                    "Occupancy rate of a given Ship for a given Cargo Manifest.",
                    "Ships will be available on Monday next week\n\n"};
            printMenu("Show Ships", options, true);
            choice = getInput("Please make a selection: ", sc);
            Connection connection = MakeDBConnection.makeConnection();

            switch (choice) {
                case 1:
                    int containerNumber = getInput("Container Number: \n", sc);
                    String us204 = "{? = call func_client_container(" + containerNumber + ")}";

                    try (CallableStatement callableStatement = connection.prepareCall(us204)) {
                        callableStatement.registerOutParameter(1, Types.VARCHAR);
                        callableStatement.execute();
                        System.out.println(callableStatement.getString(1));
                    } catch (SQLException e) {
                        System.out.println("Failed to create a statement: " + e);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                    }
                    break;
                case 2:
                    int mmsiUnloading = getInput("Insert Ship's MMSI: ", sc);
                    if (mmsiAVL.find(new ShipMMSI(mmsiUnloading)) != null) {
                        currentShip = mmsiAVL.find(new ShipMMSI(mmsiUnloading));
                        FunctionsDB.getGetContainersNextPort(currentShip, "unloading");
                    }
                    break;
                case 3:
                    int mmsiLoading = getInput("Insert Ship's MMSI: ", sc);
                    if (mmsiAVL.find(new ShipMMSI(mmsiLoading)) != null) {
                        currentShip = mmsiAVL.find(new ShipMMSI(mmsiLoading));
                        FunctionsDB.getGetContainersNextPort(currentShip, "loading");
                    }
                    break;
                case 4:
                    int year = getInput("Select a Year: \n", sc);
                    String us207 = "{? = call func_avg_cm_container(" + year + ")}";


                    try (CallableStatement callableStatement = connection.prepareCall(us207)) {
                        callableStatement.registerOutParameter(1, Types.VARCHAR);
                        callableStatement.execute();
                        System.out.println(callableStatement.getString(1));
                    } catch (SQLException e) {
                        System.out.println("Failed to create a statement: " + e);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                    }
                    break;
                case 5:
                    int mmsi = getInput("Insert Ship's MMSI: \n", sc);
                    int container = getInput("Insert Cargo Manifest: \n", sc);
                    String us208 = "{? = call func_ratio(" + container + " , "+ mmsi +")}";


                    try (CallableStatement callableStatement = connection.prepareCall(us208)) {
                        callableStatement.registerOutParameter(1, Types.VARCHAR);
                        callableStatement.execute();
                        System.out.println(callableStatement.getString(1));
                    } catch (SQLException e) {
                        System.out.println("Failed to create a statement: " + e);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                    }
                    break;
                case 6:
                    int idShip = getInput("Insert Ship's MMSI: \n", sc);
                    int idContainer = getInput("Insert Cargo Manifest: \n", sc);
                    String us209 = "{? = call func_ratio_moment(" + idContainer + " , "+ idShip +")}";


                    try (CallableStatement callableStatement = connection.prepareCall(us209)) {
                        callableStatement.registerOutParameter(1, Types.VARCHAR);
                        callableStatement.execute();
                        System.out.println(callableStatement.getString(1));
                    } catch (SQLException e) {
                        System.out.println("Failed to create a statement: " + e);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                    }
                    break;
                case 7:
                    FunctionsDB.shipsAvailableMonday();
                    break;
                case 8:
                    String us305 = "{? = call func_check_container(" + 16 + " , "+ 1 +")}";


                    try (CallableStatement callableStatement = connection.prepareCall(us305)) {
                        callableStatement.registerOutParameter(1, Types.VARCHAR);
                        callableStatement.execute();
                        System.out.println(callableStatement.getString(1));
                    } catch (SQLException e) {
                        System.out.println("Failed to create a statement: " + e);
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to access database: " + e);
                        }
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Sorry, this option is invalid.");
                    break;
            }
        } while (choice != 0);
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
}