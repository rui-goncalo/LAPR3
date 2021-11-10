package lapr.project.model;

public class ShipCallSign extends Ship implements Comparable<ShipCallSign> {


    public ShipCallSign(Ship ship) {
        super(ship.getMmsi(),
                ship.getDynamicShip(),
                ship.getName(),
                ship.getImo(),
                ship.getCallSign(),
                ship.getVessel(),
                ship.getLength(),
                ship.getWidth(),
                ship.getDraft(),
                ship.getCargo());
    }

    public ShipCallSign(String callSign) {
        super(0, null, null, 0, callSign, 0, 0, 0, 0, 0);
    }

    @Override
    public int compareTo(ShipCallSign o) {
        return this.getCallSign().compareTo(o.getCallSign());
    }
}
