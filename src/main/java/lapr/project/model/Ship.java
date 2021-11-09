package lapr.project.model;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Ship implements Comparable<Ship> {

    private final int mmsi;
    private ArrayList<ShipData> dynamicShip;
    private final String name;
    private final int imo;
    private final String callSign;
    private final int vessel;
    private final double length;
    private final double width;
    private final double draft;
    private final double cargo;

    // private final double capacity;
    // private int code;
    // private final int generatorCount;
    // private final double generatorOutput;


    public Ship(int mmsi, String name, int imo, String callSign, int vessel, double length, double width, double draft, double cargo) {
        this.mmsi = mmsi;
        this.dynamicShip = new ArrayList<>();
        this.name = name;
        this.imo = imo;
        this.callSign = callSign;
        this.vessel = vessel;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
    }

    public int getMmsi() {
        return this.mmsi;
    }

    public int getImo() {
        return this.imo;
    }

    public String getCallSign() {
        return this.callSign;
    }

    public ArrayList<ShipData> getDynamicShip() {
        return dynamicShip;
    }

    public String getName() {
        return name;
    }

    public int getVessel() {
        return vessel;
    }
    
    public void setDynamicShip(ArrayList<ShipData> data) {
        this.dynamicShip = data;
    }

    public void addDynamicShip(ShipData data) {
        this.dynamicShip.add(data);
    }

    public ArrayList<ShipData> filterShipData(LocalDateTime start, LocalDateTime end){
        ArrayList<ShipData> filteredShipData = new ArrayList<>();
        
        for(ShipData shipData : dynamicShip){
            if(shipData.getDateTime().isAfter(end)){
                break;
            }
            if(shipData.getDateTime().isAfter(start)){
                filteredShipData.add(shipData);
            }
        }

        return filteredShipData;
    }
    
    @Override
    public int compareTo(Ship o) {
        if (this.getMmsi() > o.getMmsi()) {
            return 1;
        } else if (this.getMmsi() < o.getMmsi()) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean compareTo(int imo) {
        return this.imo == imo;
    }

    public boolean compareTo(String callSign) {
        return this.callSign.equals(callSign);
    }
}