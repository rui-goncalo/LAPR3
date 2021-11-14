package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Summary {

    public static ArrayList createSumary(Ship ship, String code) {
        ArrayList<Object> summary = new ArrayList<>();
        LocalDateTime inicialTime = null;
        LocalDateTime finalTime = null;
        int nMoves = 0;
        int totalTime;
        double maxSog = 0.0;
        double meanSog = 0.0;
        double maxCog = 0.0;
        double meanCog = 0.0;
        double departLat = 0.0;
        double departLong = 0.0;
        double arrLat = 0.0;
        double arrLong = 0.0;



        for (ShipData sd : ship.getDynamicShip()) {
            if (nMoves == 0) {
                inicialTime = sd.getDateTime();
                departLat = sd.getLatitude();
                departLong = sd.getLongitude();
            } else if (nMoves + 1 == ship.getDynamicShip().size()) {
                finalTime = sd.getDateTime();
                arrLat = sd.getLatitude();
                arrLong = sd.getLongitude();
            }
            if (maxSog < sd.getSog()) {
                maxSog = sd.getSog();
            }
            if (maxCog < sd.getCog()) {
                maxCog = sd.getCog();
            }
            meanSog += sd.getSog();
            meanCog += sd.getCog();
            nMoves++;
        }

        Date initialDate = Calculator.convertToDateViaInstant(inicialTime);
        Date finalDate = Calculator.convertToDateViaInstant(finalTime);
        totalTime = finalDate.compareTo(initialDate);

        meanSog = meanSog / nMoves;
        meanCog = meanCog / nMoves;

        Double travelDistance = Calculator.totalDistance(ship.getDynamicShip());
        Double deltaDistance = Calculator.distanceBetween(departLat, departLong, arrLat, arrLong);


        // Code
        if (code.equalsIgnoreCase("MMSI")) {
            summary.add(ship.getMmsi());
        } else if (code.equalsIgnoreCase("IMO")) {
            summary.add(ship.getImo());
        } else if (code.equalsIgnoreCase("CallSign")) {
            summary.add(ship.getCallSign());
        }
        summary.add(ship.getName()); // Name
        summary.add(ship.getVessel()); // VasselType
        summary.add(inicialTime); // BDT Inicial
        summary.add(finalTime); // BDT Final
        summary.add(totalTime); // Tempo total dos movimentos
        summary.add(nMoves); // Numero total de movimentos
        summary.add(maxSog); // MaxSog
        summary.add(meanSog); // MeanSog
        summary.add(maxCog); // MaxCog
        summary.add(meanCog); // MeanCog
        summary.add(departLat); // DepartureLatitude
        summary.add(departLong); // DepartureLongitude
        summary.add(arrLat); // ArrivalLatitude
        summary.add(arrLong); // ArrivalLongitude
        summary.add(travelDistance); // TraveledDistance
        summary.add(deltaDistance); // DeltaDistance

        return summary;
    }


}
