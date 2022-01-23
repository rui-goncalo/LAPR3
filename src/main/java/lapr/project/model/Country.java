package lapr.project.model;

public class Country extends Location {

    private final int id;
    private final String name;
    private final String alpha2;
    private final String alpha3;
    private final String continent;
    private final String capital;
    private final double population;
    private final double latitude;
    private final double longitude;

    public Country(int id, String name, String alpha2, String alpha3, String continent, String capital, double population, double latitude, double longitude) {
        super(name, continent);
        this.id = id;
        this.name = name;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.continent = continent;
        this.capital = capital;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public String getContinent() {
        return continent;
    }

    public String getCapital() {
        return capital;
    }

    public double getPopulation() {
        return population;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    @Override
    public String toString(){
        return capital;
    }
}
