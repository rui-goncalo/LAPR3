package lapr.project.model;

import java.time.LocalDateTime;

public class ShipData {

    private final LocalDateTime dateTime;
    private final double latitude;
    private final double longitude;
    private final double sog;
    private final double cog;
    private final double heading;
    private final char transceiverClass;


    public ShipData(LocalDateTime dateTime, double latitude, double longitude, double sog, double cog, double heading, char transceiverClass) {
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.transceiverClass = transceiverClass;
    }

    public LocalDateTime getDateTime() {
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

    public char getTransceiver() {
        return this.transceiverClass;
    }

    public String toString() {
        String[] date = this.getDateTime().toString().split("T");
        return "Date: " + date[0] + ", Time: " + date[1] +
                ", Latitude: " + this.getLatitude() +
                ", Longitude: " + this.getLongitude() +
                ", SOG: " + this.getSog() +
                ", COG: " + this.getCog() +
                ", Heading: " + this.getHeading() +
                ", Transceiver: " + this.getTransceiver();
    }
}