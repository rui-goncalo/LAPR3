package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.Calculator;

import java.util.*;
import java.util.function.BinaryOperator;

public class FunctionsGraph {

    private static final String BORDERS_FILE = "data/borders.csv";
    private static final String SMALL_PORTS_FILE = "data/sports.csv";
    private static final String COUNTRIES_FILE = "data/countries.csv";
    private static final String SEADIST_FILE = "data/seadists.csv";
    private static final ArrayList<Port> portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
    private static final ArrayList<Country> countriesArray  = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
    private static final ArrayList<Border> borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
    private static final ArrayList<Seadist> seaDistArray = CSVReaderUtils.readSeadistsCSV(SEADIST_FILE);
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();

    private static final GraphDijkstra<PortInfo, Integer> dijkstraGraph = new GraphDijkstra();


    public static GraphDijkstra populateGraph() {
        int counter = 0;
        PortInfo origin = null;
        PortInfo dest = null;
        for (Seadist portInfo : seaDistArray) {
            if (counter == 0) {
                origin = new PortInfo(portInfo.getFromCountry(), portInfo.getFromPortId(), portInfo.getFromPort());
            }
            PortInfo fromPort = new PortInfo(portInfo.getFromCountry(), portInfo.getFromPortId(), portInfo.getFromPort());
            PortInfo toPort = new PortInfo(portInfo.getToCountry(), portInfo.getToPortId(), portInfo.getToPort());

            dest = toPort;

            ArrayList<PortInfo> vertices = dijkstraGraph.vertices();

            boolean isValidFromPort = true;
            boolean isValidToPort = true;
            for (PortInfo port : vertices) {
                if (Integer.compare(port.getId(), fromPort.getId()) == 0) {
                    isValidFromPort = false;
                }
                if (Integer.compare(port.getId(), toPort.getId()) == 0) {
                    isValidToPort = false;
                }
            }

            if (isValidFromPort) {
                dijkstraGraph.addVertex(fromPort);
            }

            if (isValidToPort) {
                dijkstraGraph.addVertex(toPort);
            }

            dijkstraGraph.addEdge(fromPort, toPort, portInfo.getSeaDistance());
        }

        BinaryOperator<Integer> operator = (x, y) -> x + y;

        Comparator<Integer> portComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? -1 : (o1 == o2 ? 0 : 1);
            }
        };

        LinkedList<PortInfo> portLinkedList = new LinkedList<>();
        Integer zero = 0;

        Integer distance = dijkstraGraph.shortestPath(dijkstraGraph, origin, dest, portComparator, operator, zero, portLinkedList);


        return dijkstraGraph;
    }


    public static AdjacencyMatrixGraph<Port, Integer> getNClosestPortMatrix(int n) {
        portMatrix = new AdjacencyMatrixGraph<>();
        loadPorts();
        ArrayList<PortDistance> distanceArray = null;

        for (Port firstPort : portsArray) {
            distanceArray = new ArrayList<>();
            for (Port secondPort : portsArray) {
                if (!firstPort.getCountry().equals(secondPort.getCountry())) {
                    double distanceToPort = Calculator.getDistance(firstPort.getLatitude(), firstPort.getLongitude(),
                            secondPort.getLatitude(), secondPort.getLongitude());
                    distanceArray.add(new PortDistance(secondPort, distanceToPort));
                }
            }
            Collections.sort(distanceArray);
            int i = 0;
            for (PortDistance portDistance : distanceArray) {
                if (i < n) {
                    portMatrix.insertEdge(firstPort, portDistance.getPort(), 1);
                    i++;
                }
            }
        }
        return portMatrix;
    }

    public static AdjacencyMatrixGraph<Port, Integer> getClosestPortsFromCapital() {
        portMatrix = new AdjacencyMatrixGraph<>();
        loadPorts();
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
                portMatrix.insertEdge(nearestPort, nearestPort, 1);
            }
        }
        for (int i = 0; i < portsArray.size() - 1; i++) {
            for (int j = i + 1; j < portsArray.size(); j++) {
                Port firstPort = portsArray.get(i);
                Port secondPort = portsArray.get(j);
                if (firstPort.getCountry().equals(secondPort.getCountry())) {
                    portMatrix.insertEdge(firstPort, secondPort, 1);
                }
            }
        }
        return portMatrix;
    }

    /**
     * Method used to get Capital and Borders Matrix.
     *
     * @return Matrix of Capitals and Borders
     *
     */
    public static AdjacencyMatrixGraph<String, Integer> getCapitalBordersMatrix() {
        AdjacencyMatrixGraph<String, Integer> capitalMatrix = new AdjacencyMatrixGraph<>();
        for (Country country : countriesArray) {
            capitalMatrix.insertVertex(country.getCapital());
        }

        for(Border border : borderArray) {
            String capital1 = border.getCountry1().getCapital();
            String capital2 = border.getCountry2().getCapital();

            capitalMatrix.insertEdge(capital1, capital2, 1);
        }
        return capitalMatrix;
    }

    public static void loadPorts() {
        for (Port port : portsArray) {
            portMatrix.insertVertex(port);
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


