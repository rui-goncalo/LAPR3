package lapr.project.utils;

import lapr.project.model.ShipData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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
    
    //handle shipData before
    //also works for deltaDistance
    public static double totalDistance(ArrayList<ShipData> shipData){
        double totalDistance = -1;
        double lat1 = 91;
        double lat2 = 91;
        double lon1 = 181;
        double lon2 = 181;
        ShipData pos1 = null;
        ShipData pos2 = null;
        
        for(int i = 0; i < shipData.size() - 1; i++) {
            pos1= shipData.get(i);
            pos2= shipData.get(i+1);
            lat1= pos1.getLatitude();
            lat2= pos2.getLatitude();
            lon1= pos1.getLongitude();
            lon2= pos2.getLongitude();
            
            totalDistance+= distanceBetween(lat1, lon1, lat2, lon2);
        }
        return totalDistance;
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

}
