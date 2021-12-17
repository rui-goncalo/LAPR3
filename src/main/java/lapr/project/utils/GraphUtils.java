package lapr.project.utils;

import lapr.project.model.Border;
import lapr.project.model.Country;
import lapr.project.model.Port;
import lapr.project.model.PortDistance;
import lapr.project.structures.AdjacencyMatrixGraph;

import java.util.ArrayList;
import java.util.Collections;

public class GraphUtils {

    private static final String BORDERS_FILE = "src/data/borders.csv";
    private static final String SMALL_PORTS_FILE = "src/data/sports.csv";
    private static final String COUNTRIES_FILE = "src/data/countries.csv";
    private static ArrayList<Port> portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
    private static ArrayList<Country> countriesArray  = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
    private static ArrayList<Border> borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();
    private static AdjacencyMatrixGraph<String, Integer> capitalMatrix = new AdjacencyMatrixGraph<>();

    private static void insertCapitals() {
        for (Country country : countriesArray) {
            capitalMatrix.insertVertex(country.getCapital());
        }

        for(Border border : borderArray) {
            String capital1 = border.getCountry1().getCapital();
            String capital2 = border.getCountry2().getCapital();

            capitalMatrix.insertEdge(capital1, capital2, 1);
        }
    }

    private static void insertPortsSameCountry() {
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

    private static void calculateDistancesFromPorts(int n) {
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
    }

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

    public static AdjacencyMatrixGraph<Port, Integer> getPortMatrix(int n) {
        insertPortsSameCountry();
        calculateDistancesFromPorts(n);
        closestPortToCapital();
        return portMatrix;
    }

    public static AdjacencyMatrixGraph<String, Integer> getCapitalMatrix() {
        insertCapitals();
        return capitalMatrix;
    }
}


