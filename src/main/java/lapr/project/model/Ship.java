package lapr.project.model;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Ship {

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


    public Ship(int mmsi, ArrayList<ShipData> dynamicShip, String name, int imo, String callSign, int vessel, double length, double width, double draft, double cargo) {
        this.mmsi = mmsi;
        this.dynamicShip = dynamicShip;
        this.name = name;
        this.imo = imo;
        this.callSign = callSign;
        this.vessel = vessel;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
//        this.dynamicShip = new ArrayList<>();
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDraft() {
        return draft;
    }

    public double getCargo() {
        return cargo;
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
        return this.name;
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

    public void printShip() {
        System.out.println("MMSI: " + this.getMmsi());
        for (ShipData data : this.dynamicShip) {
            data.toString();
        }
    }

    public void initializeDynamicData() {
        this.dynamicShip = new ArrayList<>();
    }

    public String toString() {
        return "Ship{" +
                "mmsi=" + mmsi +
                ", dynamicShip=" + dynamicShip +
                ", name='" + name + '\'' +
                ", imo=" + imo +
                ", callSign='" + callSign + '\'' +
                ", vessel=" + vessel +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +
                ", cargo=" + cargo +
                '}';
    }
}