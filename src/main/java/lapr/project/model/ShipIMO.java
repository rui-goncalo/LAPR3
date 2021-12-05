package lapr.project.model;

/**
 * @author Rui Gonçalves - 1191831
 */
public class ShipIMO extends Ship implements Comparable<ShipIMO> {

    /**
     * Constructor of ShipIMO
     *
     * @param ship
     */
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

    /**
     * Constructor of Ship IMO, initialized all data to null/0,
     * except IMO value. It's useful because the find method in BST.
     *
     * @param imo
     */
    public ShipIMO(int imo) {
        super(0, null, null, imo, null, 0, 0, 0, 0, 0);
    }

    /**
     * Override of Object compareTo method
     * @param o ShipImo to compare to
     * @return a value greater than 0 if the first IMO is greater than the other;
     *         a value less than 0 if the the first IMO is less than the other
     *         0 if both values are equal
     */
    @Override
    public int compareTo(ShipIMO o) {
        return Integer.compare(this.getImo(), o.getImo());
    }
}
