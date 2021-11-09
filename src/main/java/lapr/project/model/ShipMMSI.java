package lapr.project.model;


public class ShipMMSI extends Ship implements Comparable<ShipMMSI> {

    public ShipMMSI(Ship ship) {
        super(ship.getMmsi(),
                ship.getName(),
                ship.getImo(),
                ship.getCallSign(),
                ship.getVessel(),
                ship.getLength(),
                ship.getWidth(),
                ship.getDraft(),
                ship.getCargo());
    }

    public ShipMMSI(int mmsi) {
        super(mmsi, null, 0, null, 0, 0, 0, 0, 0);
    }

    @Override
    public int compareTo(ShipMMSI o) {
        if (this.getMmsi() > o.getMmsi()) {
            return 1;
        } else if (this.getMmsi() < o.getMmsi()) {
            return -1;
        } else {
            return 0;
        }
    }
}
