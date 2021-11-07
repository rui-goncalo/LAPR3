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
        return dateTime;
    }
}