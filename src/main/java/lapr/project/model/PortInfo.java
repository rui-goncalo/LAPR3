package lapr.project.model;

public class PortInfo implements Comparable<PortInfo> {
    private final String country;
    private final Integer id;
    private final String name;

    public PortInfo(String country, int id, String name) {
        this.country = country;
        this.id = id;
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(PortInfo o) {
        return this.getId().compareTo(o.getId());
    }
}
