package lapr.project.model;

public class Port {
    private final String continent;
    private final String country;
    private final int id;
    private final String name;
    private final double lat;
    private final double lon;

    public Port(String continent, String country, int id, String name, double lat, double lon) {
        this.continent = continent;
        this.country = country;
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
