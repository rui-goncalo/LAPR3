package lapr.project.model;

public class ShipIMO extends Ship implements Comparable<ShipIMO> {

    public ShipIMO(Ship ship) {
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

    public ShipIMO(int imo) {
        super(0, null, null, imo, null, 0, 0, 0, 0, 0);
    }

    @Override
    public int compareTo(ShipIMO o) {
        return Integer.compare(this.getImo(), o.getImo());
    }
}
