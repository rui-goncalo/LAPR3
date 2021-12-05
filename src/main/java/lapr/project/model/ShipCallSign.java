package lapr.project.model;

/**
 * @author Rui Gonçalves - 1191831
 */
public class ShipCallSign extends Ship implements Comparable<ShipCallSign> {

    /**
     * Constructor of ShipCallSign
     *
     * @param ship
     */
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

    /**
     * Constructor of Ship CallSign, initialized all data to null/0,
     * except CallSign value. It's useful because the find method in BST.
     *
     * @param callSign
     */
    public ShipCallSign(String callSign) {
        super(0, null, null, 0, callSign, 0, 0, 0, 0, 0);
    }

    /**
     * Override of Object compareTo method
     * 
     * @param o ShipCallSign to compare to
     * @return returns a positive integer if the first CallSign is lexicographically greater than the other;
     *         returns a negative integer for the opposite
     *         returns 0 if they're lexicographically equal
     */
    @Override
    public int compareTo(ShipCallSign o) {
        return this.getCallSign().compareTo(o.getCallSign());
    }
}
