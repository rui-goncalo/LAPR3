package lapr.project.model;

import java.util.ArrayList;

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

    // private final double capacity;
    // private int code;
    // private final int generatorCount;
    // private final double generatorOutput;


    public Ship(int mmsi, String name, int imo, String callSign, int vessel, double length, double width, double draft) {
        this.mmsi = mmsi;
        this.dynamicShip = new ArrayList<>();
        this.name = name;
        this.imo = imo;
        this.callSign = callSign;
        this.vessel = vessel;
        this.length = length;
        this.width = width;
        this.draft = draft;
    }

    public int getMmsi() {
        return mmsi;
    }

    public ArrayList<ShipData> getDynamicShip() {
        return dynamicShip;
    }

    public void addDynamicShip(ShipData data) {
        this.dynamicShip.add(data);
    }
}
