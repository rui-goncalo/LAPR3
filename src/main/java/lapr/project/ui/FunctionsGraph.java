package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.Calculator;

import java.util.*;
import java.util.function.BinaryOperator;

public class FunctionsGraph {

    private static final String BORDERS_FILE = "data/borders.csv";
    private static final String BIG_PORTS_FILE = "data/bports.csv";
    private static final String COUNTRIES_FILE = "data/countries.csv";
    private static final String SEADIST_FILE = "data/seadists.csv";
    private static final ArrayList<Port> portsArray = CSVReaderUtils.readPortCSV(BIG_PORTS_FILE);
    private static final ArrayList<Country> countriesArray  = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
    private static final ArrayList<Border> borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
    private static final ArrayList<Seadist> seaDistArray = CSVReaderUtils.readSeadistsCSV(SEADIST_FILE);
    private static AdjacencyMatrixGraph<Location, Double> freightNetworkMatrix = new AdjacencyMatrixGraph<>();

    private static GraphDijkstra<Location, Double> dijkstraGraph = new GraphDijkstra();


    public static GraphDijkstra populateGraph(int n) {
        dijkstraGraph = new GraphDijkstra<>();
        Iterable<Location> freightVertices = freightNetworkMatrix.vertices();

        for (Location location : freightVertices) {
            if (location.getClass() == Port.class) {
                if (!dijkstraGraph.validVertex(location)) {
                    dijkstraGraph.addVertex(location);
                }

                Map<Integer, Double> edgeMap = freightNetworkMatrix.getVertexEdges(location);
                for (Map.Entry<Integer, Double> edge : edgeMap.entrySet()) {
                    //System.out.println(edge); -> mostra os edges para cada location
                    Location toLocation = freightNetworkMatrix.getVertex(edge.getKey());
                    if (toLocation.getClass() == Port.class) {

                        if (!dijkstraGraph.validVertex(toLocation)) {
                            dijkstraGraph.addVertex(toLocation);
                        }
                        dijkstraGraph.addEdge(location, toLocation, edge.getValue());
                    }
                }
            }
        }

        BinaryOperator<Double> operator = (x, y) -> x + y; // somar distancias
        Comparator<Double> portComparator = (o1, o2) -> o1 < o2 ? -1 : (o1 == o2 ? 0 : 1); //comparar se são maiores ou + pequenas

        LinkedList<Location> portLinkedList = new LinkedList<>();
        Double zero = 0.0;

        ArrayList<Location> vertices = dijkstraGraph.vertices();
        ArrayList<String> paths = new ArrayList<>();
        Map<Integer, Integer> pathsMap = new HashMap<>();
        for (Location fromLocation : vertices) {
            for (Location toLocation : vertices) {
                String path = dijkstraGraph.shortestPath(dijkstraGraph, fromLocation, toLocation, portComparator, operator, zero, portLinkedList);
                if (path != null) {
                    String[] splitPath = path.split(",");
                    if (Double.parseDouble(splitPath[splitPath.length - 1]) != 0.0) {
                        //System.out.println("From: " + fromLocation.getName() + " To: " + toLocation.getName() + " -> " + path);
                        paths.add(path);
                    }
                }
            }
        }

        for (String path : paths) {
            String[] splitPath = path.split(",");
            for (int i = 0; i < splitPath.length - 1; i++) {
                int location = Integer.parseInt(splitPath[i]);
                //
                // System.out.println(location);
                if (pathsMap.containsKey(location)) {
                    pathsMap.put(location, pathsMap.get(location) + 1);
                } else {
                    pathsMap.put(location, 1);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(pathsMap.entrySet());

        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<Integer, Integer> sortedPaths = new LinkedHashMap<Integer, Integer>();
        int counter = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedPaths.put(entry.getKey(), entry.getValue());
            if (counter < n) { // o n de portos com mais shortest paths
                System.out.println(dijkstraGraph.getVertex(entry.getKey()).getName() + " " + entry.getValue());
            }
            counter++;
        }



        return dijkstraGraph;
    }

    // fazer o map com a contagem dos vertices nos caminhos. Deve retornar aquele que tiver maior numero de caminhos.


    public static AdjacencyMatrixGraph<Location, Double> getFreightNetworkMatrix(int n) {
        // Add capitals
        freightNetworkMatrix = new AdjacencyMatrixGraph<>();
        for (Country country : countriesArray) {
            freightNetworkMatrix.insertVertex(country);
        }

        for(Border border : borderArray) {
            Country country1 = border.getCountry1();
            Country country2 = border.getCountry2();

            freightNetworkMatrix.insertEdge(country1, country2, 1.0);
        }

        // Add N closest ports
        loadPorts();
        ArrayList<PortDistance> distanceArray = null;

        for (Port firstPort : portsArray) {
            distanceArray = new ArrayList<>();
            for (Port secondPort : portsArray) {
                if (!firstPort.getCountry().equals(secondPort.getCountry())) {
                    double dist = 0.0;
                    for (Seadist seadist : seaDistArray) {
                        if (firstPort.equals(seadist.getFromPort()) && secondPort.equals(seadist.getToPort())) {
                            dist = seadist.getSeaDistance();
                        } else if (secondPort.equals(seadist.getFromPort()) && firstPort.equals(seadist.getToPort())) {
                            dist = seadist.getSeaDistance();
                        }
                    }
                    double distanceToPort = Calculator.getDistance(firstPort.getLatitude(), firstPort.getLongitude(),
                            secondPort.getLatitude(), secondPort.getLongitude());
                    distanceArray.add(new PortDistance(secondPort, distanceToPort));
                }
            }
            Collections.sort(distanceArray);
            int i = 0;
            for (PortDistance portDistance : distanceArray) {
                if (i < n) {
                    freightNetworkMatrix.insertEdge(firstPort, portDistance.getPort(), portDistance.getDistance());
                    i++;
                }
            }
        }

        // Get closest ports from capital

        Port nearestPort = null;
        double distance = 0.0;
        int counter = 0;
        for(Country country : countriesArray) {
            distance = 0.0;
            counter++;
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
                freightNetworkMatrix.insertEdge(nearestPort, nearestPort, 1.0);
            }
        }
        for (int i = 0; i < portsArray.size() - 1; i++) {
            for (int j = i + 1; j < portsArray.size(); j++) {
                Port firstPort = portsArray.get(i);
                Port secondPort = portsArray.get(j);
                if (firstPort.getCountry().equals(secondPort.getCountry())) {
                    String fromPort = firstPort.getName();
                    String toPort = secondPort.getName();
                    double dist = 0.0;
                    for (Seadist seadist : seaDistArray) {
                        if (fromPort.equals(seadist.getFromPort()) && toPort.equals(seadist.getToPort())) {
                            dist = seadist.getSeaDistance();
                            freightNetworkMatrix.insertEdge(firstPort, secondPort, dist);
                            //System.out.println("First Port: " + firstPort.getName() + " Second Port: " + secondPort.getName() + " -> " + dist);
                        } else if (toPort.equals(seadist.getFromPort()) && fromPort.equals(seadist.getToPort())) {
                            dist = seadist.getSeaDistance();
                            freightNetworkMatrix.insertEdge(secondPort, firstPort, dist);
                            //System.out.println("First Port: " + firstPort.getName() + " Second Port: " + secondPort.getName() + " -> " + dist);
                        }
                    }

                }
            }
        }
        return freightNetworkMatrix;
    }

    public static void loadPorts() {
        for (Port port : portsArray) {
            freightNetworkMatrix.insertVertex(port);
        }
    }

    public static Map<String, String> getBorderMap() {
        Map<String, String> borderMap = new HashMap<>();
        String color = null;

        for(Country country :countriesArray) {
            String countryName = country.getName();
            color =getRandomColor();
            for (Border border :borderArray) {
                if(border.getCountry1().getName().equals(countryName)) {
                    String country2 = border.getCountry2().getName();
                    if (borderMap.containsKey(country2)) {
                        String country2Color = borderMap.get(country2);
                        while (color.equals(country2Color)) {
                            color =getRandomColor();
                        }
                        borderMap.put(countryName, color);
                    } else {
                        borderMap.put(countryName, color);
                    }
                } else if (border.getCountry2().getName().equals(countryName)) {
                    String country1 = border.getCountry1().getName();
                    if (borderMap.containsKey(country1)) {
                        String country1Color = borderMap.get(country1);
                        while (color.equals(country1Color)) {
                            color =getRandomColor();
                        }
                        borderMap.put(countryName, color);
                    } else {
                        borderMap.put(countryName, color);
                    }
                }
            }
        }

        for (String key : borderMap.keySet()) {
            System.out.println("Country: " + key + " Color: " + borderMap.get(key));
        }
        return borderMap;
    }

    public static String getRandomColor() {
        String[] colors = new String[]{"blue", "green", "yellow", "black", "pink"};
        return colors[(int)Math.floor(Math.random()*(4+1)+0)];
    }
}