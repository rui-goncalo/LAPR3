package lapr.project.model;

import java.util.Date;

public class Ship {

    private final int mmsi;
    private final String name;
    private final int imo;
    private final int energy_generators;
    private final double power_output;
    private final int call_sign;
    private final int vessel;
    private final double width;
    private final double length;
    private final double capacity;
    private final double draft;

    private Date date_time;
    private double latitude;
    private double longitude;
    private double sog;
    private double cog;
    private double heading;
    private int code;
    private String transceiver_class;


    public Ship(int mmsi, String name, int imo, int energy_generators, double power_output, int call_sign, int vessel, double width, double length, double capacity, double draft) {
        this.mmsi = mmsi;
        this.name = name;
        this.imo = imo;
        this.energy_generators = energy_generators;
        this.power_output = power_output;
        this.call_sign = call_sign;
        this.vessel = vessel;
        this.width = width;
        this.length = length;
        this.capacity = capacity;
        this.draft = draft;
    }


    public int getMmsi() {
        return mmsi;
    }

    public String getName() {
        return name;
    }

    public int getImo() {
        return imo;
    }

    public int getEnergy_generators() {
        return energy_generators;
    }

    public double getPower_output() {
        return power_output;
    }

    public int getCall_sign() {
        return call_sign;
    }

    public int getVessel() {
        return vessel;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getDraft() {
        return draft;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude <= 90 && latitude >= -90 ) {
            this.latitude = latitude;
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude <= 180 && longitude >= -180 ) {
            this.longitude = longitude;
        }
    }

    public double getSog() {
        return sog;
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public double getCog() {
        return cog;
    }

    public void setCog(double cog) {
        if( cog >= 0 && cog <= 359) {
            this.cog = cog;
        }
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        if( heading >= 0 && heading <= 359) {
            this.heading = heading;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTransceiver_class() {
        return transceiver_class;
    }

    public void setTransceiver_class(String transceiver_class) {
        this.transceiver_class = transceiver_class;
    }
}
