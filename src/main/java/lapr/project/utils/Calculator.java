package lapr.project.utils;

import lapr.project.model.ShipData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Calculator {
    //EARTH RADIUS   
    private static final double R= 6371e3; // metres
    
    private static double degToRad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private static double radToDeg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
     public static double distanceBetween(double lat1, double lon1, double lat2, double lon2){
        //if latitude/longitude aren't available
         if((lat1 < -90)  || (lat1 > 90)  || (lat2 < -90)  || (lat2 > 90) || 
           (lon1 < -180) || (lon1 > 180) || (lon2 < -180) || (lon2 > 180)  ){
            return 0;
        }
        
        double lat1Rad = degToRad(lat1);
        double lat2Rad = degToRad(lat2);
        double Δlat = degToRad(lat2-lat1);
        double Δlon = degToRad(lon2-lon1);
        double a = Math.sin(Δlat/2) * Math.sin(Δlat/2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(Δlon/2) * Math.sin(Δlon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return (R * c); // in metres      
    }

    
    //handle shipData before
    //also works for deltaDistance
    public static double totalDistance(ArrayList<ShipData> shipData){
        if(shipData.isEmpty()) { return 0;}
        
        double totalDistance = -1;
        double lat1;
        double lat2;
        double lon1;
        double lon2;
        ShipData pos1;
        ShipData pos2;
        
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
