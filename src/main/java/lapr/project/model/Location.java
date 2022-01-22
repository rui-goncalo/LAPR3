package lapr.project.model;

public class Location implements Comparable<Location> {
    private final String name;
    private final String continent;

    public Location(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    @Override
    public int compareTo(Location o) {
        if (o.getName().equals(this.getName())) {
            return 0;
        } else {
            return 1;
        }
    }
}
