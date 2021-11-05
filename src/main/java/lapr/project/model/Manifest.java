package lapr.project.model;

import java.util.Date;

public class Manifest {

    private final int id;
    private final Container container;
    private final Date departureDate;
    private final Date expectedArrival;
    private Date arrivalDate;
    private final Ship ship;
    private final Truck truck;
    private final Warehouse initialWarehouse;
    private final Warehouse finalWarehouse;
    private final Port initialPort;
    private final Port destinationPort;
    private final Customer customer;
    private final int containerX;
    private final int containerY;
    private final int containerZ;
    private final String type;

    public Manifest(int id, Container container, Date departureDate, Date expectedArrival, Ship ship,
                    Truck truck, Warehouse initialWarehouse, Warehouse finalWarehouse, Port initialPort,
                    Port destinationPort, Customer customer, int containerX, int containerY,
                    int containerZ, String type) {
        this.id = id;
        this.container = container;
        this.departureDate = departureDate;
        this.expectedArrival = expectedArrival;
        this.arrivalDate = null;
        this.ship = ship;
        this.truck = truck;
        this.initialWarehouse = initialWarehouse;
        this.finalWarehouse = finalWarehouse;
        this.initialPort = initialPort;
        this.destinationPort = destinationPort;
        this.customer = customer;
        this.containerX = containerX;
        this.containerY = containerY;
        this.containerZ = containerZ;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public Container getContainer() {
        return this.container;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public Date getExpectedArrival() {
        return this.expectedArrival;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }

    public Ship getShip() {
        return this.ship;
    }

    public Truck getTruck() {
        return this.truck;
    }

    public Warehouse getInitialWarehouse() {
        return this.initialWarehouse;
    }

    public Warehouse getFinalWarehouse() {
        return this.finalWarehouse;
    }

    public Port getInitialPort() {
        return this.initialPort;
    }

    public Port getDestinationPort() {
        return this.destinationPort;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getContainerX() {
        return this.containerX;
    }

    public int getContainerY() {
        return this.containerY;
    }

    public int getContainerZ() {
        return this.containerZ;
    }

    public String getType() {
        return this.type;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}