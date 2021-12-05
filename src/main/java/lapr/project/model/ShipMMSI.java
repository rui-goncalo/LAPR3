package lapr.project.model;

/**
 * @author Rui Gonçalves - 1191831
 */
public class ShipMMSI extends Ship implements Comparable<ShipMMSI> {

    /**
     * Constructor of ShipMMSI
     *
     * @param ship
     */
    public ShipMMSI(Ship ship) {
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
     * Constructor of Ship MMSI, initialized all data to null/0,
     * except MMSI value. It's useful because the find method in BST.
     *
     * @param mmsi
     */
    public ShipMMSI(int mmsi) {
        super(mmsi, null, null, 0, null, 0, 0, 0, 0, 0);
    }

    /**
     * Override for Object compareTo method
     * 
     * @param o ShipMMSI to compare to
     * @return  1 if the first ShipMMSI is greater than the other
     *         -1 if the first ShipMMSI is less than the other
     *          0 if they're equal
     */
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
