package lapr.project.model;

import java.util.Date;

public class Message {

    private final int id;
    private final Ship ship;
    private final double latitude;
    private final double longitude;
    private final double distance;
    private final Date dateTime;

    public Message(int id, Ship ship, double latitude, double longitude, double distance, Date dateTime) {
        this.id = id;
        this.ship = ship;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public Ship getShip() {
        return ship;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public Date getDateTime() {
        return dateTime;
    }
}
