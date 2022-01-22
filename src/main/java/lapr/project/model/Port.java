package lapr.project.model;

public class Port extends Location {
    /**
     * Instance variables of a Port.
     */
    private final String continent;
    private final String country;
    private final int id;
    private final String name;
    private final double latitude;
    private final double longitude;

    /**
     * Creates a Port with the attributes below.
     * @param continent
     * @param country
     * @param id
     * @param name
     * @param latitude
     * @param longitude
     */
    public Port(String continent, String country, int id, String name, double latitude, double longitude) {
        super(name, continent);
        this.continent = continent;
        this.country = country;
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
    public double getLatitude() {
        return latitude;
    }

    /**
     * 
     * @return Port longitude
     */
    public double getLongitude() {
        return longitude;
    }
        
    @Override
    public String toString(){
        return name;
    }
}
