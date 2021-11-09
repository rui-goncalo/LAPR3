package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sumary {

    public static ArrayList createSumary(Ship ship, String code){
        int nMoves = 0;
        ArrayList<Object> sumary = new ArrayList<>();
        LocalDateTime inicialTime = null, finalTime = null, totalTime = null;
        Double maxSog = 0.0, meanSog = 0.0, maxCog = 0.0, meanCog = 0.0;
        Double departLat=0.0, departLong = 0.0, arrLat = 0.0, arrLong = 0.0;
                sumary.add(ship.getName()); // Name
                sumary.add(ship.getVessel()); // VasselType
                for (ShipData sd : ship.getDynamicShip()) {
                    if(nMoves == 0){
                        inicialTime = sd.getDateTime();
                        departLat = sd.getLatitude();
                        departLong = sd.getLongitude();
                    } else if(nMoves+1 == ship.getDynamicShip().size()){
                        finalTime = sd.getDateTime();
                        arrLat = sd.getLatitude();
                        arrLong = sd.getLongitude();
                    }
                    if(maxSog < sd.getSog()){
                        maxSog = sd.getSog();
                    }
                    if(maxCog < sd.getCog()){
                        maxCog = sd.getCog();
                    }
                    meanSog += sd.getSog();
                    meanCog += sd.getCog();
                    nMoves++;
                }

                inicialTime
                totalTime = Calculator.convertToLocalDateViaInstant();

                sumary.add(inicialTime); // BDT Inicial
                sumary.add(finalTime); // BDT Final
                sumary.add(totalTime); // Tempo total dos movimentos
                sumary.add(nMoves); // Numero total de movimentos
                meanSog = meanSog/nMoves;
                meanCog = meanCog/nMoves;
                sumary.add(maxSog); // MaxSog
                sumary.add(meanSog); // MeanSog
                sumary.add(maxCog); // MaxCog
                sumary.add(meanCog); // MeanCog
                sumary.add(departLat); // DepartureLatitude
                sumary.add(departLong); // DepartureLongitude
                sumary.add(arrLat); // ArrivalLatitude
                sumary.add(arrLong); // ArrivalLongitude
        return sumary;
    }


}
