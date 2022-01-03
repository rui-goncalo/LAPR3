package lapr.project.ui;

import lapr.project.model.Border;
import lapr.project.model.Country;
import lapr.project.model.Port;
import lapr.project.model.PortDistance;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.Calculator;

import java.util.*;

public class FunctionsGraph {

    private static final String BORDERS_FILE = "data/borders.csv";
    private static final String SMALL_PORTS_FILE = "data/sports.csv";
    private static final String COUNTRIES_FILE = "data/countries.csv";
    private static ArrayList<Port> portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
    private static ArrayList<Country> countriesArray  = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
    private static ArrayList<Border> borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();
    private static AdjacencyMatrixGraph<String, Integer> capitalMatrix = new AdjacencyMatrixGraph<>();

    /**
     * Method used to insert Capitals and Borders into a Matrix
     * Through Capitals we add a Vertex and for Borders
     * we have the Edges.
     *
     */
    private static void insertCapitalsAndBorders() {


    }

    /**
     * Method used to insert Countries as Vertex. Through ArrayList
     * of Ports, we check if Ports have same Country and if so,
     * we add a new edge with value 1. Otherwise, the field is null.
     *
     */
    private static void insertPortsSameCountry() {
        AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();

        for (Port port : portsArray) {
            portMatrix.insertVertex(port);
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
    }

    /**
     * Method used to calculate distances from one port to another.
     * In the ArrayList of Ports we have a list of Ports and for each
     * one we add a new Port Distance list with the Port that contains
     * the n Ports with the smallest distance to the largest.
     *
     * @param n number of Ports (input from user)
     */
    private static void calculateDistancesFromPorts(int n) {
        AdjacencyMatrixGraph<Port, Integer> portNMatrix = new AdjacencyMatrixGraph<>();
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
                    portNMatrix.insertEdge(firstPort, portDistance.getPort(), 1);
                    i++;
                }
            }
        }
    }

    /**
     * Method used to get the closest Port to the Capital of a
     * Country. For the closest one, we make a buckle.
     *
     */
    private static void closestPortToCapital() {
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
    }

    /**
     * Method used to get all methods that involving Ports.
     *
     * @return Matrix of Ports
     *
     */
    public static AdjacencyMatrixGraph<Port, Integer> getCountryPortMatrix() {
        portMatrix = new AdjacencyMatrixGraph<>();

        for (Port port : portsArray) {
            portMatrix.insertVertex(port);
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

    public static AdjacencyMatrixGraph<Port, Integer> getNClosestPortMatrix(int n) {
        portMatrix = new AdjacencyMatrixGraph<>();
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
        return portMatrix;
    }

    /**
     * Method used to get Capital and Borders Matrix.
     *
     * @return Matrix of Capitals and Borders
     *
     */
    public static AdjacencyMatrixGraph<String, Integer> getCapitalBordersMatrix() {
        capitalMatrix = new AdjacencyMatrixGraph<>();
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


}


