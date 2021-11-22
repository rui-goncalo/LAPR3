package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.time.Duration;
import java.time.LocalDateTime;

public class Summary {
    private final ShipData departureData;
    private final ShipData arrivalData;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final double departLat;
    private final double departLon;
    private final double arrLat;
    private final double arrLon;
    private final long days;
    private final long hours;
    private final long minutes;
    private double maxSog;
    private double meanSog;
    private double maxCog;
    private double meanCog;
    private double travelledDistance;
    private final double deltaDistance;

    public Summary(Ship ship) {
        this.departureData = ship.getFirstDynamicData();
        this.arrivalData = ship.getLastDynamicData();

        this.departureTime = departureData.getDateTime();
        this.arrivalTime = arrivalData.getDateTime();

        this.departLat = departureData.getLatitude();
        this.departLon = departureData.getLongitude();
        this.arrLat = arrivalData.getLatitude();
        this.arrLon = arrivalData.getLongitude();

        int moveCount = ship.getDynamicShip().size();
        long totalMinutes = Duration.between(departureTime, arrivalTime).toMinutes();

        this.days = totalMinutes / 1440;
        this.hours = totalMinutes / 60 - days * 24;
        this.minutes = totalMinutes % 60;

        this.maxSog = departureData.getSog();
        this.meanSog = 0;
        this.maxCog = departureData.getCog();
        this.meanCog = 0;

        this.travelledDistance = 0;
        this.deltaDistance = Calculator.getDistance(departLat, departLon, arrLat, arrLon);

        for (int i = 0; i < moveCount; i++) {
            ShipData sd = ship.getDynamicShip().get(i);
            this.meanSog += sd.getSog();
            this.meanCog += sd.getCog();

            if (sd.getSog() > maxSog)
                this.maxSog = sd.getSog();
            if (sd.getCog() > maxCog)
                this.maxCog = sd.getCog();
            if (i < ship.getDynamicShip().size() - 1) {
                ShipData secondData = ship.getDynamicShip().get(i + 1);
                travelledDistance += Calculator.getDistance(sd.getLatitude(), sd.getLongitude(), secondData.getLatitude(), secondData.getLongitude());
            }
        }

        meanSog = meanSog / moveCount;
        meanCog = meanCog / moveCount;
    }

    public ShipData getDepartureData() {
        return departureData;
    }

    public ShipData getArrivalData() {
        return arrivalData;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public double getDepartLat() {
        return departLat;
    }

    public double getDepartLon() {
        return departLon;
    }

    public double getArrLat() {
        return arrLat;
    }

    public double getArrLon() {
        return arrLon;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public double getMaxSog() {
        return maxSog;
    }

    public double getMeanSog() {
        return meanSog;
    }

    public double getMaxCog() {
        return maxCog;
    }

    public double getMeanCog() {
        return meanCog;
    }

    public double getTravelledDistance() {
        return travelledDistance;
    }

    public double getDeltaDistance() {
        return deltaDistance;
    }

}
