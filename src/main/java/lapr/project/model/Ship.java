package lapr.project.model;

import lapr.project.utils.Summary;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * @author Rui Gonçalves - 1191831
 * @author João Teixeira - 1180590
 */
public class Ship {

    /**
     * Instance variables of a Ship.
     * <b>Note:</b> We don't use the variables that are
     *              commented out in this Sprint.
     */
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
    private Summary summary;

    // private final double capacity;
    // private int code;
    // private final int generatorCount;
    // private final double generatorOutput;


    /**
     * Creates Ship with the attributes below.
     *
     * @param mmsi
     * @param dynamicShip
     * @param name
     * @param imo
     * @param callSign
     * @param vessel
     * @param length
     * @param width
     * @param draft
     * @param cargo
     */
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
        this.summary = null;
    }

    /**
     *
     * @return Ship length
     */
    public double getLength() {
        return length;
    }

    /**
     *
     * @return Ship width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Draft means a vertical distance between the waterline
     * and the bottom of the ship's hull, in meters.
     *
     * @return Ship draft
     */
    public double getDraft() {
        return draft;
    }

    /**
     *
     * @return Ship Cargo
     */
    public double getCargo() {
        return cargo;
    }

    /**
     * MMSI is an unique 9-digit ship identification code.
     *
     * @return Ship MMSI
     */
    public int getMmsi() {
        return this.mmsi;
    }

    /**
     * IMO is an unique 7-digit international identification
     * number, which remains unchanged after transferring the
     * ship's registration to another country.
     *
     * @return Ship IMO
     */
    public int getImo() {
        return this.imo;
    }

    /**
     * Is a ship's unique callsign.
     *
     * @return Ship Call Sign
     */
    public String getCallSign() {
        return this.callSign;
    }

    /**
     * Dynamic data fields relating to a ship's positioning data.
     *
     * @return Ship dynamic data
     */
    public ArrayList<ShipData> getDynamicShip() {
        return dynamicShip;
    }

    /**
     *
     * @return Ship name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return Ship Vessel type
     */
    public int getVessel() {
        return vessel;
    }

    /**
     *
     * @return Summary of Ships
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     *
     * @param data ShipData to add the ArrayList
     */
    public void setDynamicShip(ArrayList<ShipData> data) {
        this.dynamicShip = data;
    }

    /**
     * Adds a new ShipData to the ShipData Arraylist.
     *
     * @param data ShipData to add to the ArrayList
     */
    public void addDynamicShip(ShipData data) {
        this.dynamicShip.add(data);
    }

    public ShipData getFirstDynamicData() {
        return this.dynamicShip.get(0);
    }

    public ShipData getLastDynamicData() {
        return this.dynamicShip.get(this.dynamicShip.size() - 1);
    }

    /**
     * Filters the shipData by a Local Date Time period
     * @param start null if no initial limit
     * @param end null if no end limit
     * @return
     */
    public ArrayList<ShipData> filterShipData(LocalDateTime start, LocalDateTime end){
        ArrayList<ShipData> filteredShipData = new ArrayList<>();
        
        //When there's no time period.
        if (start==null && end==null) {
            return dynamicShip;
        }
        
        //When there's no initial date.
        if (start == null) {
            for (ShipData shipData : dynamicShip) {
                if (shipData.getDateTime().isAfter(end)) {
                    break;
                }
                filteredShipData.add(shipData);
            }
            return filteredShipData;
        }
        
        //When there's final date.
        if (end == null) {
            for (ShipData shipData : dynamicShip) {
                if (shipData.getDateTime().isAfter(start)) {
                    filteredShipData.add(shipData);
                }
            }
            return filteredShipData;
        }
        
        //When there's a date period.
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

    /**
     * Prints the records of a ShipData array organized by mmsi.
     */
    public void printShip() {
        System.out.println("Ship MMSI: " + this.getMmsi());
        for (ShipData data : this.dynamicShip) {
            System.out.println(data.toString());
        }
    }

    /**
     * Creates an ArrayList of DynamicShip.
     */
    public void initializeDynamicData() {
        this.dynamicShip = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "mmsi=" + mmsi +
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

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public ShipData getDataByDate(LocalDateTime date) {
        for (ShipData data : this.dynamicShip) {
            LocalDateTime currentDate = data.getDateTime();
            if (currentDate.isEqual(date)) {
                return data;
            }
        }
        return null;
    }
}