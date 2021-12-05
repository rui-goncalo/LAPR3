package lapr.project.model;

import java.time.LocalDateTime;

/**
 * @author Rui Gonçalves - 1191831
 */
public class ShipData {

    /**
     * Instance variables of a ShipData.
     */
    private final LocalDateTime dateTime;
    private final double latitude;
    private final double longitude;
    private final double sog;
    private final double cog;
    private final double heading;
    private final char transceiverClass;


    /**
     * Creates ShipData with the attributes below.
     *
     * @param dateTime
     * @param latitude
     * @param longitude
     * @param sog
     * @param cog
     * @param heading
     * @param transceiverClass
     */
    public ShipData(LocalDateTime dateTime, double latitude, double longitude, double sog, double cog, double heading, char transceiverClass) {
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.transceiverClass = transceiverClass;
    }

    /**
     *
     * @return Date Time of a ShipData
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     *
     * @return Latitude of a ShipData
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return Longitude of a Longitude
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @return Sog of a ShipData
     */
    public double getSog() {
        return this.sog;
    }

    /**
     *
     * @return Cog of a ShipData
     */
    public double getCog() {
        return this.cog;
    }

    /**
     *
     * @return Heading of a ShipData
     */
    public double getHeading() {
        return this.heading;
    }

    /**
     *
     * @return Transceiver Class of a ShipData
     */
    public char getTransceiver() {
        return this.transceiverClass;
    }

    /**
     * Override method of Object toString
     * 
     * @return a string representation of ShipData
     */
    @Override
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