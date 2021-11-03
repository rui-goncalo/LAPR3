package lapr.project.model;

public class Port {

    private final int id;
    private final String name;
    private final String continent;
    private final String Country;
    private final double latitude;
    private final double longitude;

    public Port(int id, String name, String continent, String country, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        Country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return Country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // TODO: Implementar o Hashcode(?) ; método Equals() ; toString().
}
