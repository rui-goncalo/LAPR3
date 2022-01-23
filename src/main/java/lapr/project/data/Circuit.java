package lapr.project.data;

import lapr.project.model.Port;
import lapr.project.utils.Calculator;

import java.util.*;


public class Circuit {

    public static String mostEfficientCircuit(FreightAdjacencyMatrixGraph<Port, Integer> g) {

        List<LinkedList<Port>> paths = Calculator.allCycles(g);
        int max=0;
        double dist=Double.MAX_VALUE;
        LinkedList<Port> tempPos= null;
        for(LinkedList<Port> p: paths) {
            if (p.size() > max) {
                max = p.size();
                tempPos = p;
                dist = pathDistance(p, g);
            } else if (p.size() == max && dist < pathDistance(p, g)) {
                tempPos = p;
                dist = pathDistance(p, g);
            }
        }
        return print(tempPos);
    }
    public static double pathDistance (LinkedList<Port> p, FreightAdjacencyMatrixGraph<Port, Integer> g){
        double temp=0;
        Port tempP= p.pop();

        for (Port pos: p) {
            temp=temp+g.edge(tempP,pos).getWeight();
            tempP=pos;
        }

        return temp;
    }

    public static String print(LinkedList<Port> p) {
        String output = "";
        for (Port port : p) {
            System.out.println(port.getName());
            output += port.getCountry() + "\n";
        }
        return output;
    }

}