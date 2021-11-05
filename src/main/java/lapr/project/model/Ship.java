package lapr.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Ship {

    private final int mmsi;
    private final String name;
    private final int imo;
    private final int generatorCount;
    private final double generatorOutput;
    private final int callSign;
    private final int vessel;
    private final double width;
    private final double length;
    private final double capacity;
    private final double draft;
    private double latitude;
    private double longitude;
    private double sog;
    private double cog;
    private double heading;
    private int code;
    private String transceiverClass;
    //private ArrayList<Ship> arrayShip;

    public Ship(int mmsi, String name, int imo, int generatorCount, double generatorOutput, int callSign,
                int vessel, double width, double length, double capacity, double draft, double latitude,
                double longitude, double sog, double cog, double heading, int code, String transceiverClass) {
        this.mmsi = mmsi;
        this.name = name;
        this.imo = imo;
        this.generatorCount = generatorCount;
        this.generatorOutput = generatorOutput;
        this.callSign = callSign;
        this.vessel = vessel;
        this.width = width;
        this.length = length;
        this.capacity = capacity;
        this.draft = draft;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.code = code;
        this.transceiverClass = transceiverClass;
    }

    public int getMmsi() {
        return this.mmsi;
    }

    public String getName() {
        return this.name;
    }

    public int getImo() {
        return this.imo;
    }

    public int getGeneratorCount() {
        return this.generatorCount;
    }

    public double getGeneratorOutput() {
        return this.generatorOutput;
    }

    public int getCallSign() {
        return this.callSign;
    }

    public int getVessel() {
        return this.vessel;
    }

    public double getWidth() {
        return this.width;
    }

    public double getLength() {
        return this.length;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public double getDraft() {
        return this.draft;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getSog() {
        return sog;
    }

    public double getCog() {
        return cog;
    }

    public double getHeading() {
        return heading;
    }

    public int getCode() {
        return code;
    }

    public String getTransceiverClass() {
        return transceiverClass;
    }

    public void setLatitude(double latitude) {
        if (latitude <= 90 && latitude >= -90 ) {
            this.latitude = latitude;
        }
    }

    public void setLongitude(double longitude) {
        if (longitude <= 180 && longitude >= -180 ) {
            this.longitude = longitude;
        }
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public void setCog(double cog) {
        if( cog >= 0 && cog <= 359) {
            this.cog = cog;
        }
    }

    public void setHeading(double heading) {
        if( heading >= 0 && heading <= 359) {
            this.heading = heading;
        }
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTransceiverClass(String transceiverClass) {
        this.transceiverClass = transceiverClass;
    }

    /*
    void addShipDynamic(ShipDynamic shipDynamic) {
        this.arrayDynamic.add(shipDynamic);
    }
*/

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return mmsi == ship.mmsi && imo == ship.imo && call_sign == ship.call_sign;
    }
*/

    /*@Override
    public int compareTo(Ship o) {

        *//* TODO: Pensar no ponto de chegada. A BST irá estar
                 ligada através de nodes que contêm ships,
                 portanto é importante pensar com
                 Este método, dentro de si, terá uma determinada
                 regra de comparação (suspeito que será a localização)
                 em que, deverá devolver três números possíveis:
                        0 -> a mesma loc..
                        1 -> o objeto que chamou o CompareTo ter uma loc. mais próxima.
                        -1 -> o objeto que chamou o CompareTo ter uma loc. menos próxima do ponto final.
         *//*
        return 0;
    }*/

}
