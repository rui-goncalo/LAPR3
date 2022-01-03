package lapr.project.model;

import java.util.Objects;
public class Location {

    private Double latitude = null;
    private Double longitude = null;
    private final String name;
    private final String countryName;

    public Location(String name, String countryName){
        this.name=name;
        this.countryName=countryName;
    }

    public Location(String name, String countryName, Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
        this.countryName=countryName;
    }

    public double getLongitude() {
            return longitude;
        }

    public double getLatitude() {
            return latitude;
        }

    public String getName() {
            return name;
        }

    public String getCountryName() {
            return countryName;
        }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location position = (Location) o;
        return Objects.equals(latitude, position.latitude) && Objects.equals(longitude, position.longitude) && Objects.equals(name, position.name) && Objects.equals(countryName, position.countryName);
    }

    @Override
    public int hashCode() {
            return Objects.hash(latitude, longitude, name, countryName);
        }

}
