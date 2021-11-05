package lapr.project.model;

public class Warehouse {

    private final int id;
    private final String name;
    private final Country country;
    private final double latitude;
    private final double longitude;
    private final int capacity;

    public Warehouse(int id, String name, Country country, double latitude, double longitude, int capacity) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Country getCountry() {
        return this.country;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getCapacity() {
        return this.capacity;
    }
}