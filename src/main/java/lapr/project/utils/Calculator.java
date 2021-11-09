package lapr.project.utils;

import lapr.project.model.*;
import java.util.ArrayList;

public class Calculator {
    //1Degree of latitude to Km   
    private static final double DEGREE_TO_KM= 111;
    // REVER: assumindo distances como ints
    
    private static double degToRad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private static double radToDeg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
    public static double distanceBetween(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(degToRad(lat1)) * Math.sin(degToRad(lat2)) + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) * Math.cos(degToRad(theta));
        dist = Math.acos(dist);
        dist = radToDeg(dist);
        dist = dist * DEGREE_TO_KM;
        return dist;
    }
    
    //TODO
    public static int totalDistance(ArrayList<ShipData> shipData){
        return 0;
    }
    
}
