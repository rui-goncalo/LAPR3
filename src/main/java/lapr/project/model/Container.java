package lapr.project.model;

public class Container {

    private final int id;
    private final int payload;
    private final double width;
    private final double height;
    private final double length;
    private final int ISO;


    private int tare;
    private double gross;
    private boolean isRefrigerated;
    private double temperature;


    public Container(int id, int payload, double width, double height, double length, int ISO, int tare, double gross, boolean isRefrigerated, double temperature) {
        this.id = id;
        this.payload = payload;
        this.width = width;
        this.height = height;
        this.length = length;
        this.ISO = ISO;
        this.tare = tare;
        this.gross = gross;
        this.isRefrigerated = isRefrigerated;
        this.temperature = temperature;
    }

    public int getId() {
        return this.id;
    }

    public int getPayload() {
        return this.payload;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getLength() {
        return this.length;
    }

    public int getISO() {
        return this.ISO;
    }

    public int getTare() {
        return tare;
    }

    public void setTare(int tare) {
        this.tare = tare;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public boolean isRefrigerated() {
        return isRefrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        isRefrigerated = refrigerated;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
