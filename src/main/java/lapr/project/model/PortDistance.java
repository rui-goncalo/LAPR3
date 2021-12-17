package lapr.project.model;

public class PortDistance implements Comparable<PortDistance> {
    private Port port;
    private double distance;

    public PortDistance(Port port, double distance) {
        this.port = port;
        this.distance = distance;
    }

    public Port getPort() {
        return port;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(PortDistance o) {
        return Double.compare(this.getDistance(), o.getDistance());
    }
}
