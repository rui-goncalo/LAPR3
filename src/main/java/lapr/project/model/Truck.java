package lapr.project.model;

public class Truck {

    private final int id;
    private final String name;
    private final String license;
    private Employee driver;

    public Truck(int id, String name, String license, Employee driver) {
        this.id = id;
        this.name = name;
        this.license = license;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicense() {
        return license;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }
}
