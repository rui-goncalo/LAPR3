package lapr.project.model;

public class Seadist {
    private final String fromCountry;
    private final int fromPortId;
    private final String fromPort;
    private final String toCountry;
    private final int toPortId;
    private final String toPort;
    private final int seaDistance;

    public Seadist(String fromCountry, int fromPortId, String fromPort, String toCountry, int toPortId, String toPort, int seaDistance) {
        this.fromCountry = fromCountry;
        this.fromPortId = fromPortId;
        this.fromPort = fromPort;
        this.toCountry = toCountry;
        this.toPortId = toPortId;
        this.toPort = toPort;
        this.seaDistance = seaDistance;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public int getFromPortId() {
        return fromPortId;
    }

    public String getFromPort() {
        return fromPort;
    }

    public String getToCountry() {
        return toCountry;
    }

    public int getToPortId() {
        return toPortId;
    }

    public String getToPort() {
        return toPort;
    }

    public int getSeaDistance() {
        return seaDistance;
    }
}
