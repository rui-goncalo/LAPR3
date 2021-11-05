package lapr.project.model;

import java.util.ArrayList;
import java.util.Date;

public class ShipData {

    private final int mmsi;
    private final Date dateTime;
    private double latitude;
    private double longitude;
    private double sog;
    private double cog;
    private double heading;
    private String vesselName;
    private String imo;
    private String callSign;
    private String vesselType;
    private double length;
    private double width;
    private double draft;
    private String cargo;
    private char transceiverClass;

    public ShipData(int mmsi, Date dateTime, double latitude, double longitude, double sog, double cog,
                    double heading, String vesselName, String imo, String callSign, String vesselType,
                    double length, double width, double draft, String cargo, char transceiverClass) {
        this.mmsi = mmsi;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.vesselName = vesselName;
        this.imo = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
        this.transceiverClass = transceiverClass;
    }

    public int getMmsi() {
        return this.mmsi;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getSog() {
        return this.sog;
    }

    public double getCog() {
        return this.cog;
    }

    public double getHeading() {
        return this.heading;
    }

    public String getVesselName() {
        return this.vesselName;
    }

    public String getImo() {
        return this.imo;
    }

    public String getCallSign() {
        return this.callSign;
    }

    public String getVesselType() {
        return this.vesselType;
    }

    public double getLength() {
        return this.length;
    }

    public double getWidth() {
        return this.width;
    }

    public double getDraft() {
        return this.draft;
    }

    public String getCargo() {
        return this.cargo;
    }

    public char getTransceiverClass() {
        return this.transceiverClass;
    }
}
