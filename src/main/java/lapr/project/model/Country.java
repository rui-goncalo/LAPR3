package lapr.project.model;

public class Country {

    private final int id;
    private final String name;
    private final Continent continent;
    private final double number;
    private final double longitude;
    private final double latitude;

    public Country(int id, String name, Continent continent, double number, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Continent getContinent() {
        return this.continent;
    }

    public double getNumber() {
        return this.number;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }
}
