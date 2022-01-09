package lapr.project.ui;

import lapr.project.model.*;
import lapr.project.structures.AdjacencyMatrixGraph;
import lapr.project.utils.CSVReaderUtils;
import lapr.project.utils.Calculator;

import java.util.*;

public class FunctionsGraph {

    private static final String BORDERS_FILE = "data/borders.csv";
    private static final String SMALL_PORTS_FILE = "data/sports.csv";
    private static final String COUNTRIES_FILE = "data/countries.csv";
    private static final String SEADIST_FILE = "data/seadists.csv";
    private static ArrayList<Port> portsArray = CSVReaderUtils.readPortCSV(SMALL_PORTS_FILE);
    private static ArrayList<Country> countriesArray  = CSVReaderUtils.readCountryCSV(COUNTRIES_FILE);
    private static ArrayList<Border> borderArray = CSVReaderUtils.readBordersCSV(BORDERS_FILE);
    private static ArrayList<Seadist> seaDistArray = CSVReaderUtils.readSeadistsCSV(SEADIST_FILE);
    private static AdjacencyMatrixGraph<Port, Integer> portMatrix = new AdjacencyMatrixGraph<>();
    private static AdjacencyMatrixGraph<String, Integer> capitalMatrix = new AdjacencyMatrixGraph<>();

    private static DijkstraGraph dijkstraGraph = new DijkstraGraph();


    // Criamos uma classe Dijkstra
    // Criamos uma classe Port Info
    // Criamos um metodo populateGraph para a US 401 que guarda em todos os nodes
    // os ports de seadist array e verifica se o from Port já existe. Senão cria um novo
    // Por último, verifica se o toNode já existe, para não repetir e criarmos um novo node.


    // O nr de Shortest Paths deve ser 1 ou 0 por Porto?
    // Perguntar se eles (profs.) disponibilizam alguma base/class para implementar o algoritmo de dijkstra

    public static DijkstraGraph populateGraph() {
        PortInfo latestPort = null;
        DijkstraGraph.Node firstNode = null;
        DijkstraGraph.Node latestNode = null;
        for (Seadist portInfo : seaDistArray) {
            if (latestPort == null) {
                latestPort = new PortInfo(portInfo.getFromCountry(), portInfo.getFromPortId(), portInfo.getFromPort());
                latestNode = new DijkstraGraph.Node(latestPort);
                firstNode = latestNode;
            }

            PortInfo fromPort = new PortInfo(portInfo.getFromCountry(), portInfo.getFromPortId(), portInfo.getFromPort());

            if (Integer.compare(latestPort.getId(), fromPort.getId()) != 0) {
                dijkstraGraph.addNode(latestNode);
                latestPort = fromPort;
                latestNode = new DijkstraGraph.Node(latestPort);
            }
            PortInfo toPort = new PortInfo(portInfo.getToCountry(), portInfo.getToPortId(), portInfo.getToPort());

            DijkstraGraph.Node toNode = null;
            DijkstraGraph.Node checkNode = dijkstraGraph.checkIfNodeExists(toPort);
            if (checkNode == null) {
                 toNode = new DijkstraGraph.Node(toPort);
            } else {
                toNode = checkNode;
            }

            latestNode.addDestination(toNode, portInfo.getSeaDistance());

            dijkstraGraph.addNode(toNode);
        }
        DijkstraGraph newGraph = DijkstraGraph.calculateShortestPathFromSource(dijkstraGraph, firstNode);
        for(DijkstraGraph.Node node : newGraph.getNodes()){
            for (DijkstraGraph.Node newNode : node.getShortestPath()) {
                System.out.println(newNode.getPort().getName());
            }
        }
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

    public static void loadPorts() {
        for (Port port : portsArray) {
            portMatrix.insertVertex(port);
        }
    }

}


