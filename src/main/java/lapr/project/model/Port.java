package lapr.project.model;

public class Port {
    /**
     * Instance variables of a Port.
     */
    private final String continent;
    private final String country;
    private final int id;
    private final String name;
    private final double lat;
    private final double lon;

    /**
     * Creates a Port with the attributes below.
     * @param continent
     * @param country
     * @param id
     * @param name
     * @param lat
     * @param lon 
     */
    public Port(String continent, String country, int id, String name, double lat, double lon) {
        this.continent = continent;
        this.country = country;
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * 
     * @return Port continent
     */
    public String getContinent() {
        return continent;
    }

    /**
     * 
     * @return Port country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @return Port id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return Port name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * 
     * @return Port latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * 
     * @return Port longitude
     */
    public double getLon() {
        return lon;
    }
}
