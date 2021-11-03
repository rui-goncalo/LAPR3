package lapr.project.model;

import java.util.Date;
import java.util.Objects;

public class Ship implements Comparable<Ship>{

    private final int mmsi;
    private final String name;
    private final int imo;
    private final int energy_generators;
    private final double power_output;
    private final int call_sign;
    private final int vessel;
    private final double width;
    private final double length;
    private final double capacity;
    private final double draft;

    private Date date_time;
    private double latitude;
    private double longitude;
    private double sog;
    private double cog;
    private double heading;
    private int code;
    private String transceiver_class;


    public Ship(int mmsi, String name, int imo, int energy_generators, double power_output, int call_sign, int vessel, double width, double length, double capacity, double draft) {
        this.mmsi = mmsi;
        this.name = name;
        this.imo = imo;
        this.energy_generators = energy_generators;
        this.power_output = power_output;
        this.call_sign = call_sign;
        this.vessel = vessel;
        this.width = width;
        this.length = length;
        this.capacity = capacity;
        this.draft = draft;
    }


    public int getMmsi() {
        return mmsi;
    }

    public String getName() {
        return name;
    }

    public int getImo() {
        return imo;
    }

    public int getEnergy_generators() {
        return energy_generators;
    }

    public double getPower_output() {
        return power_output;
    }

    public int getCall_sign() {
        return call_sign;
    }

    public int getVessel() {
        return vessel;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getDraft() {
        return draft;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude <= 90 && latitude >= -90 ) {
            this.latitude = latitude;
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude <= 180 && longitude >= -180 ) {
            this.longitude = longitude;
        }
    }

    public double getSog() {
        return sog;
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public double getCog() {
        return cog;
    }

    public void setCog(double cog) {
        if( cog >= 0 && cog <= 359) {
            this.cog = cog;
        }
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        if( heading >= 0 && heading <= 359) {
            this.heading = heading;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTransceiver_class() {
        return transceiver_class;
    }

    public void setTransceiver_class(String transceiver_class) {
        this.transceiver_class = transceiver_class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return mmsi == ship.mmsi && imo == ship.imo && call_sign == ship.call_sign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mmsi, imo, call_sign);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "mmsi=" + mmsi +
                ", name='" + name + '\'' +
                ", imo=" + imo +
                ", energy_generators=" + energy_generators +
                ", power_output=" + power_output +
                ", call_sign=" + call_sign +
                ", vessel=" + vessel +
                ", width=" + width +
                ", length=" + length +
                ", capacity=" + capacity +
                ", draft=" + draft +
                ", date_time=" + date_time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", sog=" + sog +
                ", cog=" + cog +
                ", heading=" + heading +
                ", code=" + code +
                ", transceiver_class='" + transceiver_class + '\'' +
                '}';
    }

    @Override
    public int compareTo(Ship o) {

        /* TODO: Pensar no ponto de chegada. A BST irá estar
                 ligada através de nodes que contêm ships,
                 portanto é importante pensar com
                 Este método, dentro de si, terá uma determinada
                 regra de comparação (suspeito que será a localização)
                 em que, deverá devolver três números possíveis:
                        0 -> a mesma loc..
                        1 -> o objeto que chamou o CompareTo ter uma loc. mais próxima.
                        -1 -> o objeto que chamou o CompareTo ter uma loc. menos próxima do ponto final.
         */
        return 0;
    }

}
