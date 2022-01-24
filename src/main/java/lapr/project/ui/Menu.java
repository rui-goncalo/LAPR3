package lapr.project.ui;

import lapr.project.data.Circuit;
import lapr.project.data.LoadDBFiles;
import lapr.project.data.MakeDBConnection;
import lapr.project.model.*;
import lapr.project.structures.AVL;
import lapr.project.structures.KDTree;
import lapr.project.utils.*;
import lapr.project.data.FreightAdjacencyMatrixGraph;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;

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
    private static final String SEADISTS_FILE = "data/seadists.csv";
    private static final String COUNTRIES_FILE = "data/countries.csv";
    private static final String BORDERS_FILE = "data/borders.csv";

    private static ArrayList<Ship> shipArray = new ArrayList<>();
    private static ArrayList<Port> portsArray = new ArrayList<>();
    private static ArrayList<Seadist> seadistsArray = new ArrayList<>();
    private static ArrayList<Country> countriesArray = new ArrayList<>();
    private static ArrayList<Border> bordersArray = new ArrayList<>();

    private static final AVL<ShipMMSI> mmsiAVL = new AVL<>();
    private static final AVL<ShipIMO> imoAVL = new AVL<>();
    private static final AVL<ShipCallSign> csAVL = new AVL<>();

    private static final KDTree<Port> portTree = new KDTree<>();
    private static Ship currentShip = null;

    private static FreightAdjacencyMatrixGraph<Port, Integer> freightNetwork= new FreightAdjacencyMatrixGraph(false);
    private static FreightAdjacencyMatrixGraph<Country, Double> freightNetworkLand = new FreightAdjacencyMatrixGraph(false);

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

        //FunctionsGraph.populateGraph();

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
            case 7:
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
                    "Get Nearest Port\n", "Print Freight Network Matrix\n", "Get N ports with greater centrality\n", "Vessel Type", "Calculation Center of Mass","Position Containers","Energy Needed to Containers\n",
                    "Shortest Maritime Path", "Shortest Land Path\n", "Calculate how much did the vessel sink\n"};
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
                    System.out.println(FunctionsGraph.getFreightNetworkMatrix(number).toString());
                    break;
                case 9:
                    int n = getInput("Insert N Ports: \n", sc);
                    FunctionsGraph.populateGraph(n);
                    break;
                case 10:
                    vesselTypesMenu(sc);
                case 11:
                    menuCenterOfMass(sc);
                case 12:
                    menuPosContainers(sc);
                case 13:
                    menuEnergyNeeded(sc);
                case 14:
                    freightCalcMaritimePaths(sc);
                    break;
                case 15:
                    freightCalcLandPaths(sc);
                    break;
                case 16:
                    calcVesselSink(sc);
                    break;
            }

        } while (choice != 0);
    }

    private static void menuCenterOfMass(Scanner scan) {
        int type;
        double c_height = 0, c_width = 0, r_length = 0, r_height = 0, t_height = 0, m1 = 0, m2 = 0, m3 = 0, xCM = 0, yCM = 0;

        int choice;
        do {
            String[] options = {"Go Back\n", "Bow", "Mid", "Stern"};
            //String[] options = {"Choose one type of vessel.\n", "1. Bow", "2. Mid", "3. Stern\n"};
            printMenu("Calculation Center of Mass", options, true);
            choice = getInput("Please make a selection: ", scan);
            Scanner input = new Scanner(System.in);

            switch (choice) {
                case 1:
                    System.out.println("Enter the height of the cabin crew (m).");
                    c_height = input.nextDouble();
                    System.out.println("Enter the width of the cabin crew (m). ");
                    c_width = input.nextDouble();
                    System.out.println("Enter the length of the rectangle (m).");
                    r_length = input.nextDouble();
                    System.out.println("Enter the height of the rectangle (m).");
                    r_height = input.nextDouble();
                    System.out.println("Enter the height of the triangle (m).");
                    t_height = input.nextDouble();
                    System.out.println("Enter the mass of the cabin crew (kg).");
                    m1 = input.nextDouble();
                    System.out.println("Enter the mass of the rectangle (kg).");
                    m2 = input.nextDouble();
                    System.out.println("Enter the mass of the triangle (kg).");
                    m3 = input.nextDouble();
                    if (r_height == t_height) {
                        xCM = ((r_length + (t_height - (c_width / 2))) * m1 + (r_length / 2) * m2 + ((r_length + r_length + (r_length + t_height)) / 3) * m3) / (m1 + m2 + m3);
                        yCM = ((r_height + (c_height / 2)) * m1 + (r_height / 2) * m2 + ((t_height + t_height) / 3) * m3) / (m1 + m2 + m3);

                        System.out.printf("The center of mass is: (" + xCM + ", " + yCM + ").");
                    } else {
                        System.out.println("The height of the triangle and rectangle doesn't match.");
                        break;
                    }
                    break;
                case 2:
                    System.out.println("Enter the height of the cabin crew (m).");
                    c_height = input.nextDouble();
                    System.out.println("Enter the width of the cabin crew (m). ");
                    c_width = input.nextDouble();
                    System.out.println("Enter the length of the rectangle (m).");
                    r_length = input.nextDouble();
                    System.out.println("Enter the height of the rectangle (m).");
                    r_height = input.nextDouble();
                    System.out.println("Enter the height of the triangle (m).");
                    t_height = input.nextDouble();
                    System.out.println("Enter the mass of the cabin crew (kg).");
                    m1 = input.nextDouble();
                    System.out.println("Enter the mass of the rectangle (kg).");
                    m2 = input.nextDouble();
                    System.out.println("Enter the mass of the triangle (kg).");
                    m3 = input.nextDouble();
                    if (r_height == t_height) {
                        xCM = (((r_length + t_height) / 2) * m1 + (r_length / 2) * m2 + ((r_length + r_length + (r_length + t_height)) / 3) * m3) / (m1 + m2 + m3);
                        yCM = ((r_height + (c_height / 2)) * m1 + (r_height / 2) * m2 + ((t_height + t_height)/3) * m3) / (m1 + m2 + m3);
                        System.out.printf("The center of mass is: (" + xCM + ", " + yCM + ").");
                    } else {
                        System.out.println("The height of the triangle and rectangle doesn't match.");
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Enter the height of the cabin crew (m).");
                    c_height = input.nextDouble();
                    System.out.println("Enter the width of the cabin crew (m). ");
                    c_width = input.nextDouble();
                    System.out.println("Enter the length of the rectangle (m).");
                    r_length = input.nextDouble();
                    System.out.println("Enter the height of the rectangle (m).");
                    r_height = input.nextDouble();
                    System.out.println("Enter the height of the triangle (m).");
                    t_height = input.nextDouble();
                    System.out.println("Enter the mass of the cabin crew (kg).");
                    m1 = input.nextDouble();
                    System.out.println("Enter the mass of the rectangle (kg).");
                    m2 = input.nextDouble();
                    System.out.println("Enter the mass of the triangle (kg).");
                    m3 = input.nextDouble();
                    if (r_height == t_height) {
                        xCM = ((c_width / 2) * m1 + (r_length / 2) * m2 + ((r_length + r_length + (r_length + t_height)) / 3) * m3) / (m1 + m2 + m3);
                        yCM = ((r_height + (c_height / 2)) * m1 + (r_height / 2) * m2 + ((t_height + t_height) / 3) * m3) / (m1 + m2 + m3);
                        System.out.printf("The center of mass is: (" + xCM + ", " + yCM + ").");
                    } else {
                        System.out.println("The height of the triangle and rectangle doesn't match.");
                        break;
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, choose again.");
                    break;
            }
        } while (choice != 0);
    }

    private static void menuPosContainers(Scanner scan)
    {
        double contHeight=2.385;
        double contLength=5.896;
        double contWidth = 2.350;
        double s_length,s_width, contCmXX = 0,contCmYY = 0,sCmXX,sCmYY;
        int choice,nContainers;
        ArrayList<ContainerInfo> containerInfos = new ArrayList<ContainerInfo>();
        do {
            String[] options = {"Go Back\n", "User Default Container Measurements\n Container Height:" + contHeight + "\n Container Length: " +contLength+ "\n Container Width: " + contWidth, "Enter New Container Measurements"};
            printMenu("Calculation position of containers on the vessel", options, true);
            choice = getInput("Please make a selection: ", scan);
            Scanner input = new Scanner(System.in);
            switch (choice) {
                case 1:
                    System.out.println("Enter the number of containers on the vessel.");
                    nContainers = input.nextInt();
                    System.out.println("Enter the length of the ships rectangle (m).");
                    s_length = input.nextDouble();
                    System.out.println("Enter the width of the ships rectangle (m). ");
                    s_width = input.nextDouble();

                    containerInfos = calculateContainersPosition(nContainers,contHeight,contLength,contWidth,s_length,s_width);
                    if(containerInfos == null)
                    {
                        System.out.println(ANSI_RED_BACKGROUND
                                + "Impossible to add that many containers to the specified ship."
                                + ANSI_RESET);

                    } else {
                        int n =0;
                        for(ContainerInfo containerInfo : containerInfos)
                        {
                            n++;
                            contCmXX +=containerInfo.getXxCm();
                            contCmYY += containerInfo.getYyCm();
                            System.out.println(containerInfo);

                        }
                        contCmXX = contCmXX /nContainers;
                        contCmYY = contCmYY / nContainers;
                        sCmXX = s_length /2;
                        sCmYY = s_width/2;
                        System.out.println("Containers total Center of Mass XX : " + contCmXX);
                        System.out.println("Containers total Center of Mass YY : " +contCmYY);
                        System.out.println("Ship Center of Mass XX: " + sCmXX);
                        System.out.println("Ship Center of Mass YY: " + sCmYY);


                    }


                    break;
                case 2:
                    System.out.println("Enter the height of the Container (m).");
                   double  contHeight1 = input.nextInt();
                    System.out.println("Enter the length of the Container (m).");
                   double contLength1 = input.nextInt();
                    System.out.println("Enter the width of the Container (m).");
                   double contWidth1 = input.nextInt();
                    System.out.println("Enter the number of containers on the vessel.");
                    nContainers = input.nextInt();
                    System.out.println("Enter the length of the ships rectangle (m).");
                    s_length = input.nextDouble();
                    System.out.println("Enter the width of the ships rectangle (m). ");
                    s_width = input.nextDouble();
                    containerInfos = calculateContainersPosition(nContainers,contHeight1,contLength1,contWidth1,s_length,s_width);
                    if(containerInfos == null)
                    {

                        System.out.println(ANSI_RED_BACKGROUND
                                + "Impossible to add that many containers to the specified ship."
                                + ANSI_RESET);
                    } else {
                        int n = 0;
                        for(ContainerInfo containerInfo : containerInfos)
                        {
                            n++;
                            contCmXX +=containerInfo.getXxCm();
                            contCmYY += containerInfo.getYyCm();
                            System.out.println(containerInfo);

                        }
                        contCmXX = contCmXX /nContainers;
                        contCmYY = contCmYY / nContainers;
                        sCmXX = s_length /2;
                        sCmYY = s_width/2;
                        System.out.println("Containers total Center of Mass XX : " + contCmXX);
                        System.out.println("Containers total Center of Mass YY : " +contCmYY);
                        System.out.println("Ship Center of Mass XX: " + sCmXX);
                        System.out.println("Ship Center of Mass YY: " + sCmYY);

                    }



                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, choose again.");
                    break;
            }
        } while (choice != 0);

    }

    private static void menuEnergyNeeded(Scanner scan)
    {

        int choice;
        do {
            String[] options = {"Go Back\n", "Energy needed to a container in a trip of 2h30m with external temp of 20ºC", "Energy needed to a vessel with X containers in an established trip", "Energy needed to a vessel in role of containers position and how many auxiliar equipments of x KW are needed"};
            printMenu("Energy Needed to transport of goods", options, true);
            choice = getInput("Please make a selection: ", scan);
            Scanner input = new Scanner(System.in);

            switch (choice) {
                case 1:
                    calculateEnergyNeeded(9000,20,1);
                    break;
                case 2:
                    System.out.println("Enter the number of containers on the vessel.");
                    int nContainers = input.nextInt();
                    System.out.println("Enter the average Temperature of the trip");
                    double temperature = input.nextInt();
                    Ship currentShip = null;
                    choice = getInput("Ship's MMSI: ", scan);
                    for (Ship ship : shipArray) {
                        if (ship.getMmsi() == choice) {
                            currentShip = ship;
                        }
                    }

                    if (currentShip != null) {
                        if (currentShip.getSummary() != null) {
                            long seconds = currentShip.getSummary().getMinutes()*60;
                            seconds += currentShip.getSummary().getDays()*24*60*60;
                            seconds += currentShip.getSummary().getHours()*60*60;
                            long secondsPos = -seconds;
                            calculateEnergyNeeded(secondsPos,temperature,nContainers);
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
                case 3:
                    System.out.println("Enter the height of the Container (m).");
                    double  contHeight2 = input.nextInt();
                    System.out.println("Enter the length of the Container (m).");
                    double contLength2 = input.nextInt();
                    System.out.println("Enter the width of the Container (m).");
                    double contWidth2 = input.nextInt();
                    System.out.println("Enter the number of containers with 0 sides exposed to the sun on the vessel.");
                    int nContainers0 = input.nextInt();
                    System.out.println("Enter the number of containers with 1 side exposed to the sun on the vessel.");
                    int nContainers1 = input.nextInt();
                    System.out.println("Enter the number of containers with 2 sides exposed to the sun on the vessel.");
                    int nContainers2 = input.nextInt();
                    System.out.println("Enter the number of containers with 3 sides exposed to the sun on the vessel.");
                    int nContainers3 = input.nextInt();
                    System.out.println("Enter the number of containers with 4 sides exposed to the sun on the vessel.");
                    int nContainers4 = input.nextInt();
                    System.out.println("Enter the number of containers with 5 sides exposed to the sun on the vessel.");
                    int nContainers5 = input.nextInt();
                    System.out.println("Enter the number of containers which will be at 7ºC.");
                    int nContainers7 = input.nextInt();
                    if(nContainers7 > (nContainers5+nContainers0+nContainers1+nContainers2+nContainers3+nContainers4))
                    {
                        System.out.println(ANSI_RED_BACKGROUND
                                + "Number of containers with temperature of 7ºC must be lower than the total."
                                + ANSI_RESET);
                    }
                    int cont5 = (nContainers5+nContainers0+nContainers1+nContainers2+nContainers3+nContainers4) - nContainers7;

                    System.out.println("Enter the average temperature of the trip.");
                    double temp = input.nextDouble();
                    System.out.println("Power of the auxiliar Equipmente (KW).");
                    double powerAux = input.nextDouble();
                    Ship currentShip2 = null;
                    choice = getInput("Ship's MMSI: ", scan);
                    for (Ship ship : shipArray) {
                        if (ship.getMmsi() == choice) {
                            currentShip2 = ship;
                        }
                    }

                    if (currentShip2 != null) {
                        if (currentShip2.getSummary() != null) {
                            long seconds = currentShip2.getSummary().getMinutes()*60;
                            seconds += currentShip2.getSummary().getDays()*24*60*60;
                            seconds += currentShip2.getSummary().getHours()*60*60;
                            long secondsPos = -seconds;
                            calculateEnergy(nContainers0,nContainers1,nContainers2,nContainers3,nContainers4,nContainers5,contLength2,contHeight2,contWidth2,temp,secondsPos,powerAux,nContainers7,cont5);
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
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, choose again.");
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
                    "Ships will be available on Monday next week.",
                    "Access to audit trails for a given container of a given cargo manifest.",
                    "Route of a specific container I am leasing.",
                    "Occupancy rate of each warehouse and an estimate for the next 30 days.",
                    "Get a warning whenever I issue a cargo manifest destined for a warehouse whose available capacity is insufficient.",
                    "Have a system that ensures that the number of containers in a manifest does not exceed the ship's available capacity.",
                    "Do not allow a cargo manifest for a particular ship to be registered in the system on a date when the ship is already occupied.",
                    "Map of the occupation of the existing resources in the port during a given month.",
                    "Know the current situation of a specific container being used to transport my goods.",
                    "Know the number of days each ship has been idle since the beginning of the current year",
                    "Know the average occupancy rate per manifest of a given ship during a given period",
                    "Know which ship voyages – place and date of origin and destination – had an occupancy rate below a certain threshold.",
                    "Generate, a week in advance, the loading and unloading map based on ships and trucks load manifests and corresponding travel plans.\n\n"};

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
                case 7: //US210
                    FunctionsDB.shipsAvailableMonday();
                    break;
                case 8:
                    int containerid = getInput("Insert Container ID: \n", sc);
                    int cargomanifestid = getInput("Insert Cargo Manifest ID: \n", sc);
                    String us304 = "{? = call func_audit_trail(" + containerid + " , "+ cargomanifestid +")}";

                    try (CallableStatement callableStatement = connection.prepareCall(us304)) {
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
                case 9:
                    int container_id = getInput("Insert Container ID: \n", sc);
                    int clientid = getInput("Insert Client ID: \n", sc);
                    String us305 = "{? = call func_check_container(" + container_id + " , "+ clientid +")}";

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
                case 10:
                    //FunctionsDB.occupancyRateWarehouse();
                    /*
                    --Nº containers em cada warehouse
                    SELECT COUNT(*) AS "Containers in Warehouse"
                    FROM Container c, Container_Cargo_Manifest ccm, Cargo_Manifest cm, Arrival a, Location l
                    WHERE c.id = ccm.containerid
                    AND ccm.cargo_manifestid = cm.id
                    AND cm.arrivalid = a.id
                    AND a.initiallocationid = l.id
                    AND l.type_locationid = 2
                    GROUP BY l.id;

                    --Nº containers por warehouse que partem nos proximos 30 dias
                    SELECT COUNT(*) AS "Containers in Warehouse", l.name, t.final_date
                    FROM Container c, Container_Cargo_Manifest ccm, Cargo_Manifest cm, Arrival a, Location l, Type_cargo_manifest tcm, Trip t
                    WHERE c.id = ccm.containerid
                    AND ccm.cargo_manifestid = cm.id
                    AND cm.type_cargo_manifestid = tcm.id
                    AND cm.arrivalid = a.id
                    AND a.initiallocationid = l.id
                    AND l.type_locationid = 2
                    AND a.tripid = t.id
                    AND t.final_date BETWEEN SYSDATE AND TRUNC(sysdate, 'DAY')+31
                    GROUP BY l.id, l.name, t.final_date;
                    * */
                    break;
                case 11:
                    //US307 - Trigger
                    break;
                case 12:
                    //US308 - Trigger
                    break;
                case 13:
                    //US309 - Trigger
                    break;
                case 14:
                    int arrival_date = getInput("Insert Date of Arrival: \n", sc);
                    String us310 = "{? = call func_map_occupation(" + arrival_date + ")}";

                    try (CallableStatement callableStatement = connection.prepareCall(us310)) {
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
                case 15:
                    //US312 - Procedure
                    break;
                case 16:
                    int p_year = getInput("Insert Year: \n", sc);
                    String us404 = "{? = call fnc_iddle_ship(" + p_year + ")}";

                    try (CallableStatement callableStatement = connection.prepareCall(us404)) {
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
                case 17:
                    int v_ship = getInput("Insert Ship mmsi: \n", sc);
                    Scanner scan2 = new Scanner(System.in);
                    LocalDate v_initial_date = DateMenu.readDateSQL(scan, "Insert Initial Date:\n");
                    LocalDate v_final_date = DateMenu.readDateSQL(scan2, "Insert Final Date:");

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String us405 = "{? = call func_occupancy_rate_period(" + v_ship + ", TO_DATE('" + v_initial_date.format(format) + "', 'DD/MM/YYYY'), TO_DATE('" + v_final_date.format(format) + "', 'DD/MM/YYYY'))}";

                    try (CallableStatement callableStatement = connection.prepareCall(us405)) {
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
                case 18:
                    int mmsi406 = getInput("Insert MMSI: ", sc);
                    String us406 = "{? = call fnc_voyages_occupation_rate("+ mmsi406 +")}";

                    try (CallableStatement callableStatement = connection.prepareCall(us406)) {
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
                case 19:
                    String us407 = "{? = call fnc_week_in_advance()}";

                    try (CallableStatement callableStatement = connection.prepareCall(us407)) {
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

    public static void vesselTypesMenu(Scanner sc) {
        Ship containerVessel = new Ship(
                636091400,
                new ArrayList<>(),
                "RHL AGILITAS",
                9373486,
                "A8ND5",
                70,
                176f,
                27f,
                11.89,
                10.0);

        Ship fishingVessel = new Ship(
                303221000,
                new ArrayList<>(),
                "ARTIC SEA",
                7819216,
                "WDG5171",
                30,
                37f,
                9f,
                3f,
                10.0);

        Ship tugVessel = new Ship(
                499929694,
                new ArrayList<>(),
                "TANERLIQ",
                9178445,
                "WDF2025",
                52,
                45f,
                14f,
                0f,
                10.0);

        int choice;
        do {
            String[] options = {"Go Back\n", "Container Vessel", "Tug Vessel", "Ro-Ro Vessel \n"};
            printMenu("Manage Ships", options, true);
            choice = getInput("Please make a selection: ", sc);

            switch (choice) {
                case 1:
                    printType(containerVessel, fishingDescription);
                    break;
                case 2:
                    printType(fishingVessel, tugDescription);
                    break;
                case 3:
                    printType(tugVessel, RoRoDescription);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, choose again.");
                    break;
            }
        } while (choice != 0);
    }

    private static void printType(Ship ship, String desc) {
        System.out.println("Ship Name: " + ship.getName() +
                "\nMMSI Code: " + ship.getMmsi() +
                "\nIMO Code: " + ship.getImo() +
                "\nCallSign: " + ship.getCallSign() +
                "\nLength: " + ship.getLength() +
                "\nWidth: " + ship.getWidth() +
                "\nDraft: " + ship.getDraft() +
                "\nVessel Type: " + ship.getVessel() +
                "\n\nDescription: " + desc);
    }

    private static final String fishingDescription = "Containers can accommodate anything from foodstuffs to electrical equipment to automobiles. They are also used to transport\n" +
            "bagged and palatalised goods, as well as liquids and refrigerated cargo.\n\n" +
            "Standard containers are measured as TEUs (Twenty-foot Equivalent Units) and are generally 20 feet (1 TEU) or 40 feet (2 TEUs) long.\n" +
            "All standard shipping containers are 8 feet wide and 8 feet 6 inches tall. There are also longer, taller and even shorter standard\n" +
            "sizes, but these are less common.\n\n" +
            "Container ships are made up of several holds, each equipped with “cell guides” which allow the containers to slot into place. Once\n" +
            "the first layers of containers have been loaded and the hatches closed, extra layers are loaded on top of the hatches. Each container\n" +
            "is then lashed to the vessel but also to each other to provide integrity. Containers are usually loaded by specialized cranes or even\n" +
            "general purpose cranes with container lifting attachments. Some small container vessels are geared to allow self-loading and discharging.\n";

    private static final String tugDescription = "Even with the advent of highly maneuverable vessels, the tug is still vitally important to the maritime industry. Modern tugs are highly\n" +
            "maneuverable with pulling power that can exceed 100 tonnes! Harbor tugs are very common at ports around the world, and generally less powerful.\n" +
            "These vessels assist in docking, undocking and moving large vessels within port limits. Tugs are also used to assist vessels during bad weather\n" +
            "or when carrying dangerous or polluting cargo. Harbor tugs are also employed to move barges, floating cranes and personnel around ports. Larger\n" +
            "units are kept on standby in strategic locations to act as deep-sea rescue and salvage tugs.\n\n" +
            "Tugs are also used to tow barges from port to port and move large structures such as offshore platforms and floating storage units. Some tugs\n" +
            "can push barges; this is particularly common on rivers where the tug is able to exert more turning force on the tow. There are also tugs that\n" +
            "are designed to ‘slot’ into a barge or hull. Once secured, this composite unit behaves and is treated like a standard powered vessel. These\n" +
            "composite units are common on North American river and coastal trade.\n";

    private static final String RoRoDescription = "Roll on-Roll off or Ro-Ro vessels come in many forms. They include vehicle ferries and cargo ships carrying truck trailers. The car\n" +
            "carrier is the most commonly-used ro-ro vessel. These slab-sided vessels feature multiple vehicle decks comprising parking lanes, linked by internal\n" +
            "ramps with access to shore provided by one or more loading ramps. Cargo capacity of such vessels is measured in Car Equivalent Units (CEU) and the\n" +
            "largest car carriers afloat today have a capacity of over 6,000 CEU.\n";



    private static void calculateEnergyNeeded(long duration, double temperature, int nContainers)
    {
        System.out.println("Energy needed to a container so it maintains a determined difference of temperature from the outside");
        System.out.println();
        System.out.println("E = Q*T");
        System.out.println(" E -> Energy (J) ; Q -> Quant. Heat Flow (W or J/s) ; t -> time (s)");
        System.out.println();
        System.out.println("Heat Flow is directionally proportional to the reason between temp gradient and thermal resistance");
        System.out.println("I = ∆T- RT");
        System.out.println("I -> Heat Flow (W or J/s) ; ∆T ->Temp difference (K) ; Rt ->Total resistance (K/W)");
        System.out.println();
        System.out.println("Energy Needed to 7ºC");
        System.out.println("Materials used and its thermal resistances in the Container:");
        System.out.println("Exterior:");
        System.out.println("Steel ; Thermal Resistance : 0.00000259 K/W ; Thickness : 0.010 m");
        System.out.println("Intermediate:");
        System.out.println("Expanded polyester ; Thermal Resistance : 0.063 K/W ; Thickness : 0.14 m");
        System.out.println("Interior:");
        System.out.println("Polypropylene ; Thermal Resistance : 0.00613 K/W ; Thickness : 0.05 m");
        System.out.println();
        System.out.println("E=∆T/RT * t");
        System.out.println("∆T = " + temperature + " - 7 = " + (temperature -7) + "K");
        System.out.println("Rt = Rexterior + Rintermediate + Rinterior = 0.06913259 K/W");
        double energy = ((temperature-7)/0.06913259 * duration);
        System.out.println("E = " +(temperature - 7) + " / 0.06913259 * " + duration + " = " + energy + "J");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Energy Needed to -5ºC");
        System.out.println("Materials used and its thermal resistances in the Container:");
        System.out.println("Exterior:");
        System.out.println("Steel ; Thermal Resistance : 0.00000259 K/W ; Thickness : 0.010 m");
        System.out.println("Intermediate:");
        System.out.println("Polyurethane foam ; Thermal Resistance : 0.0756 K/W ; Thickness : 0.14 m");
        System.out.println("Interior:");
        System.out.println("Polypropylene ; Thermal Resistance : 0.00613 K/W ; Thickness : 0.05 m");
        System.out.println();
        System.out.println("E=∆T/RT * t");
        System.out.println("∆T = " + temperature + " - (-5) = " + (temperature -(-5)) + "K");
        System.out.println("Rt = Rexterior + Rintermediate + Rinterior = 0.08173259 K/W");
        energy = ((temperature-(-5))/0.08173259 * duration);
        System.out.println("E = " +(temperature -(-5)) + " / 0.06913259 * " + duration + " = " + energy + " J");

        if(nContainers >1)
        {
            System.out.println();
            System.out.println("Etotal = nContainers * Econtainer = " + energy*nContainers + " J");
        }


    }
    private static class ContainerInfo {
        private final String container;
        private final double xxCm;
        private final double yyCm;
        private final double xxIn;
        private final double xxFin;
        private final double yyIn;
        private final double yyFin;
        private final double height;

        @Override
        public String toString() {
            return  container +"\n" +
                    "Center of Mass XX: " + xxCm +
                    ", Center of Mass YY: " + yyCm + "\n  Measurements \n" +
                    " Initial Position XX:" + xxIn +
                    ", Initial Position YY:" + yyIn + "\n" +
                    " Final Position XX:" + xxFin +
                    ", Final Position YY: " + yyFin + "\n" +
                    " Height=" + height + "\n" ;
        }

        public ContainerInfo(String container, double xxCm, double yyCm, double xxIn, double xxFin, double yyIn, double yyFin, double height)
        {
            this.container = container;
            this.xxCm = xxCm;
            this.yyCm = yyCm;
            this.xxIn = xxIn;
            this.xxFin = xxFin;
            this.yyIn = yyIn;
            this.yyFin = yyFin;
            this.height = height;

        }

        public double getXxCm() {
            return xxCm;
        }

        public double getYyCm() {
            return yyCm;
        }

        public double getXxIn() {
            return xxIn;
        }

        public double getXxFin() {
            return xxFin;
        }

        public double getYyIn() {
            return yyIn;
        }

        public double getYyFin() {
            return yyFin;
        }

        public double getHeight()
        {
            return height;
        }
        public String getContainer() {
            return container;
        }


    }

    private static ArrayList<ContainerInfo> calculateContainersPosition(int nContainers, double contHeight, double contLength,double contWidth, double s_length, double s_width)
    {
        int MaxContStacked = 7;
        double height = 0;
        double xxSCont = s_length /2;
        double yySCont = s_width /2;
        double cont1CmXX = contLength/2;
        double cont1CmYY = contWidth/2;
        double cont1InXX = 0;
        double cont1InYY = 0;
        double cont1FinXX = contLength;
        double cont1FinYY = contWidth;

        double cont2CmXX = s_length - contLength/2;
        double cont2CmYY = s_width - contWidth/2;
        double cont2InXX = s_length - contLength;
        double cont2InYY = s_width;
        double cont2FinXX = s_length;
        double cont2FinYY = s_width - contWidth;
        int n = 1;

        double midWidth = s_width/2;

        ArrayList<ContainerInfo> containerInfos = new ArrayList<ContainerInfo>();

        //If nContainers is odd, one container should be in the middle of the ship to be in balance
        if(nContainers % 2 ==1)
        {
            containerInfos.add(new ContainerInfo("container"+n,xxSCont,yySCont,xxSCont-contLength/2,xxSCont+contLength/2,yySCont-contWidth/2,yySCont+contWidth/2,height));
            nContainers--;
            n++;

            midWidth-=contWidth/2;
        }

        while(nContainers>0)
        {
            // Adds containers in pais, opposite sides so that the center of mass remains in the middle of the rectangle

            containerInfos.add(new ContainerInfo("container"+n,cont1CmXX,cont1CmYY,cont1InXX,cont1FinXX,cont1InYY,cont1FinYY,height));
            cont1CmXX += contLength;
            cont1InXX +=contLength;
            cont1FinXX +=contLength;
            nContainers--;
            n++;

            containerInfos.add(new ContainerInfo("container"+n,cont2CmXX,cont2CmYY,cont2InXX,cont2FinXX,cont2InYY,cont2FinYY,height));
            cont2CmXX -= contLength;
            cont2InXX -=contLength;
            cont2FinXX -=contLength;
            nContainers--;
            n++;
            //Verify if is possible to add another container in the row , if not verifies it its within the possible width
            if(cont1FinXX + contLength > s_length)
            {
                //If it is within the possible width, increments width and changes pos of XX to initial position
                if(cont1FinYY + contWidth < midWidth){
                    cont1CmYY += contWidth;
                    cont1InYY += contWidth;
                    cont1FinYY += contWidth;
                    cont1CmXX = contLength/2;
                    cont1InXX = 0;
                    cont1FinXX = contLength;

                    cont2CmYY -= contWidth;
                    cont2InYY -= contWidth;
                    cont2FinYY -= contWidth;
                    cont2CmXX = s_length - contLength/2;
                    cont2InXX = s_length - contLength;
                    cont2FinXX = s_length;
                } else {
                    //If its not possible, starts to stack containers on top of each other ( MAX 8)
                    if(MaxContStacked >0)
                    {
                        height += contHeight;
                        cont1CmXX = contLength/2;
                        cont1CmYY = contWidth/2;
                        cont1InXX = 0;
                        cont1InYY = 0;
                        cont1FinXX = contLength;
                        cont1FinYY = contWidth;

                        cont2CmXX = s_length - contLength/2;
                        cont2CmYY = s_width - contWidth/2;
                        cont2InXX = s_length - contLength;
                        cont2InYY = s_width;
                        cont2FinXX = s_length;
                        cont2FinYY = s_width - contWidth;
                        MaxContStacked--;
                        //If there is still containers to add, its impossible to have that many containers in that ship, so it returns null
                    } else {
                        return null;
                    }
                }
            }
        }


        return containerInfos;
    }


    public static void calculateEnergy(int cont0,int cont1,int cont2,int cont3,int cont4,int cont5,double contLength,double contHeight,double contWidth,double temperature,long duration,double powerAux,int nCont7,int nCont5)
    {
        double cont1AC = contLength * contWidth;
        double cont2AC = (contLength * contWidth) + (contWidth*contHeight);
        double cont3AC = 2*(contLength * contWidth) + (contWidth*contHeight);
        double cont4AC = 2*(contLength * contWidth) + 2*(contWidth*contHeight);
        double cont5AC = 3*(contLength * contWidth) + 2*(contWidth*contHeight);
        double totalContAC = cont1*cont1AC + cont2*cont2AC + cont3*cont3AC+cont4*cont4AC+cont5*cont5AC;
        double Lsteel = 0.01;
        double Ksteel = 52;
        double LPoliuretano = 0.14;
        double KPoliuretano = 0.03;
        double LPolipropileno = 0.05;
        double KPoplipropelino = 0.11;
        double RTotal7 = ((Lsteel/(Ksteel*totalContAC))+(LPoliuretano/(KPoliuretano*totalContAC))+(LPolipropileno/(KPoplipropelino*totalContAC)));
        double eConduction7 = (((temperature-7)/RTotal7)*duration);
        double eRadiation7 = (5.67*0.000000001*172.37*0.9*((temperature +273.15 )*(temperature +273.15 )*(temperature +273.15 )*(temperature +273.15 )-(7+273.15)*(7+273.15)*(7+273.15)*(7+273.15) )*duration);
        double LPoliestireno = 0.14;
        double KPoliestireno = 0.025;
        double RTotal5 = ((Lsteel/(Ksteel*totalContAC))+(LPoliestireno/(KPoliestireno*totalContAC))+(LPolipropileno/(KPoplipropelino*totalContAC)));
        double eConduction5 = (((temperature+5)/RTotal5)*duration);
        double eRadiation5 = (5.67*0.000000001*172.37*0.9*((temperature +273.15 )*(temperature +273.15 )*(temperature +273.15 )*(temperature +273.15 )+(5+273.15)*(5+273.15)*(5+273.15)*(5+273.15) )*duration);
        double totalE7 = eConduction7+eRadiation7;
        double totalE5 = eConduction5+eRadiation5;
        double pTotal = ((nCont5*totalE5)+(nCont7*totalE7))/duration;



        System.out.println("To determine the necessary energy to maintain the temperature of 7ºC and -5ºC with the resulting temperature variations of exposure to the sun, we need to consider the Heat Flow from conduction and radiation");
        System.out.println();
        System.out.println("Etotal = Econduction + Eradiation");
        System.out.println();
        System.out.println("The transfered energy per conduction will be the product of Heat Flow (W or J/s) with the time (s).");
        System.out.println();
        System.out.println("Econduction = Q*t");
        System.out.println();
        System.out.println("Using the analogy of thermal resistance, we know that the Heat Flow is directly proportional to the difference of temperature and inversely proportional to the thermal resistance");
        System.out.println();
        System.out.println("Q= ∆T/Rt");
        System.out.println("In terms of Heat Flow by radiation, we can determine the power from Stefan Boltzmann law:");
        System.out.println();
        System.out.println("Pradiation = σAεT^4");
        System.out.println("Eradiation = (σAradation εT^4) *t");
        System.out.println("T(K) = T(ºC) + 273.15");
        System.out.println();
        System.out.println();
        System.out.println("Containers used: ");
        System.out.println("Containers with 0 sides exposed to sun: " + cont0);
        System.out.println("Containers with 1 side exposed to sun: "+ cont1);
        System.out.println("Containers with 2 sides exposed to sun: "+ cont2);
        System.out.println("Containers with 3 sides exposed to sun: "+ cont3);
        System.out.println("Containers with 4 sides exposed to sun: "+ cont4);
        System.out.println("Containers with 5 sides exposed to sun: "+ cont5);
        System.out.println();
        System.out.println("Total Area of Conduction:");
        System.out.println("For each container with 0 sides exposed: 0 m^2");
        System.out.println("For each container with 1 side exposed: ");
        System.out.println("Aconduction1 = 1* (container length * container width) = " + cont1AC);
        System.out.println("Aconduction2 = 1* (container length * container width) + 1*(container width * container height) = " + cont2AC);
        System.out.println("Aconduction3 = 2* (container length * container width) + 1*(container width * container height) = " + cont3AC);
        System.out.println("Aconduction4 = 2* (container length * container width) + 2*(container width * container height) = " + cont4AC);
        System.out.println("Aconduction5 = 3* (container length * container width) + 2*(container width * container height) = " + cont5AC);
        System.out.println("ATotalconduction = Cont 1 side * Aconduction1 + Cont 2 sides * Aconduction2 +Cont 3 sides * Aconduction3 +Cont 4 sides * Aconduction4 +Cont 5 sides * Aconduction5 = " + totalContAC);
        System.out.println();
        System.out.println("For 7ºC");
        System.out.println("Rtotal = Lsteel / (Ksteel*ATotalConduction) + Lpolyurethane / (Kpolyurethane*ATotalConduction) + Lpolypropylene / (Kpolypropylene*ATotalConduction)");
        System.out.println("Rtotal = " + RTotal7);
        System.out.println();
        System.out.println("Econduction = " + eConduction7);
        System.out.println();
        System.out.println("Eradiation =" + eRadiation7);
        System.out.println();
        System.out.println("For -5ºC");
        System.out.println("Rtotal = Lsteel / (Ksteel*ATotalConduction) + LexpandedPolyester / (KexpandedPolyester*ATotalConduction) + Lpolypropylene / (Kpolypropylene*ATotalConduction)");
        System.out.println("Rtotal = " + RTotal5);
        System.out.println();
        System.out.println("Econduction = " + eConduction5);
        System.out.println();
        System.out.println("Eradiation =" + eRadiation5);
        System.out.println();
        System.out.println("Total Energy for 7ºC: "+ totalE7);
        System.out.println();
        System.out.println("Total Energy for -5ºC: "+ totalE5);
        System.out.println();
        System.out.println();
        System.out.println("Auxiliar Equipments needed:");
        System.out.println("Ptotal = Etotal / time");
        System.out.println("Ptotal =" + pTotal + " W ");
        System.out.println("Ptotal = " + pTotal/1000 + " KW ");
        System.out.println("NEquipments = Ptotal / Pequipments");
        System.out.println("Nequipments = " + (int)((pTotal/1000) / powerAux)+1);
    }
    
    //
    //US402
    //
    
    //MARITIME
    public static void freightCalcMaritimePaths(Scanner sc){
        freightLoadMaritimePaths();
        Port vOrig = null;
        Port vDest = null;
        do {
            int portOrig = getInput("Input First Port", sc);

            for (Port port : freightNetwork.vertices()) {
                if (port.getId() == portOrig) {
                    vOrig = port;
                    break;
                }
            }
        } while (vOrig == null);

        do {
            int portDest = getInput("Input Second Port", sc);

            for (Port port : freightNetwork.vertices()) {
                if (port.getId() == portDest) {
                    vDest = port;
                    break;
                }
            }
        } while (vDest == null);

        BinaryOperator<Integer> sum = (a, b) -> { return a + b;};
        Comparator<Integer> ce = (Integer d1, Integer d2) -> d1 - d2;
        int numVerts = freightNetwork.numVertices();
        boolean[] visited = new boolean[numVerts];
        Port[] pathKeys = new Port[numVerts];
        Integer[] dist = new Integer[numVerts];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        freightNetwork.shortestPathDijkstraFreight(vOrig, ce, sum, 0, visited, pathKeys, dist);
        
//        int origKey = freightNetwork.key(vOrig);
        int destKey = freightNetwork.key(vDest);
        
        
        System.out.printf("The shortest path from Port %s to Port %s\nIt's distance is %d\nThe Path that was used:\n", vOrig.getName(), vDest.getName(), dist[destKey]);
        
        int i = destKey;
        ArrayList<Port> reversedPath = new ArrayList<Port>();
        reversedPath.add(vDest);
        while(pathKeys[i]!=vOrig){
            reversedPath.add(pathKeys[i]);
            i = freightNetwork.key(pathKeys[i]);
        }
        reversedPath.add(vOrig);
        Collections.reverse(reversedPath);
        for(Port port: reversedPath){
            System.out.println(port.toString());
        }
        
    }
    public static void freightLoadMaritimePaths(){
        //make sure ports are loaded into the array.
        if(portsArray==null){
            System.out.println("Ports array is empty.");
            return;
        }
        for (Port port : portsArray) {
            if(port == null){ System.out.println("Port is null.");
                return;
            }
            freightNetwork.addVertex(port);
        }
        
        int fromPortId;
        int toPortId;
        Port fromPort;
        Port toPort;
        
        seadistsArray = CSVReaderUtils.readSeadistsCSV(SEADISTS_FILE);      
        //check if seadists empty
        if(seadistsArray.isEmpty()){
            System.out.println("Seadists array is empty!");
            return;
        }
        
        for(Seadist seadist: seadistsArray){
            fromPortId = seadist.getFromPortId();
            toPortId = seadist.getToPortId();
            
            fromPort = findPortById(fromPortId);
            toPort = findPortById(toPortId);
            
            //if from Port is NULL
            if(fromPort==null){
                System.out.printf("The from Port does not exist:\nfromPortId: %d\n", fromPortId);
                fromPort = new Port("None", seadist.getFromCountry(), seadist.getFromPortId(), seadist.getFromPort(), 0, 0);
            }
            
            //if to Port is NULL
            if(toPort==null){
                System.out.printf("The to Port does not exist:\ntoPortId: %d\n", toPortId);
                toPort = new Port("None", seadist.getToCountry(), seadist.getToPortId(), seadist.getToPort(), 0, 0);
            }
            
//            System.out.println(freightNetwork.addEdge(fromPort, toPort, seadist.getSeaDistance()));
            freightNetwork.addEdge(fromPort, toPort, seadist.getSeaDistance());
        }
        //printing matrix
//        System.out.println(freightNetwork.toString());
    }
    
    public static Port findPortById(int portId){
        Port portFound = null;
        for (Port port : freightNetwork.vertices()){
            if(port.getId()==portId){
                portFound = port;
                break;
            }
        }
        return portFound;
    }
    
    //LAND
    
    public static void freightCalcLandPaths(Scanner sc){
        freightLoadLandPaths(sc);
        
        Country vOrig = null;
        Country vDest = null;
        do {
            System.out.println("Input First City\n");
            String cityOrig = sc.next();

            for (Country country : freightNetworkLand.vertices()) {
                if (country.getCapital().equals(cityOrig)) {
                    vOrig = country;
                    break;
                }
            }
        } while (vOrig == null);

        do {
            System.out.println("Input Second City\n");
            String cityDest = sc.next();

            for (Country country : freightNetworkLand.vertices()) {
                if (country.getCapital().equals(cityDest)) {
                    vDest = country;
                    break;
                }
            }
        } while (vDest == null);

        BinaryOperator<Double> sum = (a, b) -> { return a + b;};
        Comparator<Double> ce = (Double d1, Double d2) -> d1.compareTo(d2);
        int numVerts = freightNetworkLand.numVertices();
        boolean[] visited = new boolean[numVerts];
        Country[] pathKeys = new Country[numVerts];
        Double[] dist = new Double[numVerts];
        Arrays.fill(dist, Double.MAX_VALUE);
        
        freightNetworkLand.shortestPathDijkstraFreight(vOrig, ce, sum, 0.0, visited, pathKeys, dist);
        
        int origKey = freightNetworkLand.key(vOrig);
        int destKey = freightNetworkLand.key(vDest);
        
        if(dist[destKey]==Double.MAX_VALUE){
            System.out.printf("There is no possible Land Path between %s and %s.", vOrig.getCapital(), vDest.getCapital());
            return;
        }
        
        System.out.printf("The shortest path from city %s to city %s\nIt's distance is %.2f Km\nThe Path that was used:\n", vOrig.getCapital(), vDest.getCapital(), dist[destKey]);
        
        int i = destKey;
        ArrayList<Country> reversedPath = new ArrayList<>();
        reversedPath.add(vDest);
        while(pathKeys[i]!=vOrig){
            reversedPath.add(pathKeys[i]);
            i = freightNetworkLand.key(pathKeys[i]);
        }
        reversedPath.add(vOrig);
        Collections.reverse(reversedPath);
        for(Country country: reversedPath){
            System.out.println(country.toString());
        }
        
    }
    
    public static void freightLoadLandPaths(Scanner sc){
        countriesArray = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
        bordersArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
        
        for(Border border : bordersArray){
            Country country1 = border.getCountry1();
            Country country2 = border.getCountry2();
            double distance = Calculator.getDistance(country1.getLatitude(), country1.getLongitude(), country2.getLatitude(), country2.getLongitude());
            freightNetworkLand.addEdge(country1, country2, distance);
        }

    }
    //
    //US420
    //
    public static void calcVesselSink(Scanner sc){
        int numContainers = getInput("How many Containers", sc);
        int totalMassPlaced = Calculator.totalMassPlaced(numContainers);
        double shipLength = getInputDouble("Input the Ship Length:", sc);
        double shipBeam = getInputDouble("Input the Ship Beam:", sc);
        double shipDraft = getInputDouble("Input the Ship Draft:", sc);
        double shipArea = shipBeam * shipLength;
        double pressureExerted = Calculator.pressureExerted(shipArea, totalMassPlaced);
        
        double heightDifference = Calculator.heightDifference(shipDraft, shipBeam, shipLength, totalMassPlaced);
        
        System.out.printf("|----SHIP MEASUREMENTS----|\n\nShip Length: %.2f m\nShip Beam: %.2f m\n"
                           + "Ship Draft: %.2f m\nShip Area: %.2f m^2\nTotal Mass Placed: %d Kg\n"
                           + "Pressure Exerted: %.2f N/m^2\nDifference In Height: %.2f m", shipLength,
                           shipBeam, shipDraft, shipArea, totalMassPlaced, pressureExerted, heightDifference);
    }
    
     /**
     * Prompts for and verifies the user input.
     *
     * @param prompt Prompt to be shown to the user
     * @param sc     user input
     * @return user input
     */
    public static double getInputDouble(String prompt, Scanner sc) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input.");
            sc.next();
            System.out.print(prompt);
        }

        return sc.nextDouble();
    }

    public static void mostEficientCircuit(){
        Circuit.mostEfficientCircuit(freightNetwork);
    }

}

