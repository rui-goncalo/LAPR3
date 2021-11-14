-- DROP Tables --
DROP TABLE Ship CASCADE CONSTRAINTS PURGE;
DROP TABLE Ship_Port CASCADE CONSTRAINTS PURGE;
DROP TABLE Container CASCADE CONSTRAINTS PURGE;
DROP TABLE Truck CASCADE CONSTRAINTS PURGE;
DROP TABLE Truck_Warehouse CASCADE CONSTRAINTS PURGE;
DROP TABLE Message CASCADE CONSTRAINTS PURGE;
DROP TABLE Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Container_Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Port CASCADE CONSTRAINTS PURGE;
DROP TABLE Warehouse CASCADE CONSTRAINTS PURGE;
DROP TABLE Pos_Container CASCADE CONSTRAINTS PURGE;
DROP TABLE Type_Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Location CASCADE CONSTRAINTS PURGE;
DROP TABLE Country CASCADE CONSTRAINTS PURGE;
DROP TABLE Continent CASCADE CONSTRAINTS PURGE;
DROP TABLE Employee CASCADE CONSTRAINTS PURGE;
DROP TABLE Type_Employee CASCADE CONSTRAINTS PURGE;
DROP TABLE Arrival CASCADE CONSTRAINTS PURGE;

-- CREATE Tables --
CREATE TABLE Ship (
    mmsi    INTEGER     CONSTRAINT ship_pk PRIMARY KEY,
    name  VARCHAR(30) CONSTRAINT ship_nn NOT NULL,
    imo     INTEGER UNIQUE,
    number_energy_gen   INTEGER,
    gen_power_output    DECIMAL(5,2),
    callsign    VARCHAR(10) UNIQUE,
    vessel_type INTEGER,
    length    DECIMAL(5,2),
    Width   DECIMAL(5,2),
    capacity  INTEGER,
    draft   DECIMAL(5,2),
    transceiver_class   VARCHAR(50),
    code    INTEGER,
    captain_id INTEGER,
    electrical_engineer INTEGER
);

CREATE TABLE Port (
    id  INTEGER     CONSTRAINT port_pk PRIMARY KEY,
    name    VARCHAR(30) CONSTRAINT port_nn NOT NULL,
    capacity    INTEGER,
    LocationId INTEGER
);

CREATE TABLE Ship_Port (
    Shipmmsi INTEGER,
    PortId_port INTEGER,
    CONSTRAINT ship_port_pk
    PRIMARY KEY(Shipmmsi, PortId_port)
);

CREATE TABLE Location (
    id  INTEGER CONSTRAINT location_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT location_nn NOT NULL,
    latitude    DECIMAL(5,2) DEFAULT 91.00,
    longitude   DECIMAL(5,2) DEFAULT 181.00,
    CountryId   INTEGER,
    CONSTRAINT location_ck CHECK (latitude >= -90.00 AND latitude <= 90.00 AND longitude >= -180.00 AND longitude <= 180.00)
);

CREATE TABLE Country(
    id  INTEGER CONSTRAINT country_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT country_nn NOT NULL,
    ContinentId INTEGER
);

CREATE TABLE Continent (
    id  INTEGER CONSTRAINT continent_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT continent_nn NOT NULL
);

CREATE TABLE Truck (
    registration_plate  VARCHAR(8) CONSTRAINT truck_pk PRIMARY KEY,
    driver_id   INTEGER,
    EmployeeId_employee INTEGER
);

CREATE TABLE Warehouse (
    id  INTEGER     CONSTRAINT warehouse_pk PRIMARY KEY,
    name    VARCHAR(30) CONSTRAINT warehouse_nn NOT NULL,
    capacity    INTEGER,
    LocationId INTEGER
);

CREATE TABLE Truck_Warehouse (
    TruckRegistration_plate VARCHAR(8),
    WarehouseId_warehouse INTEGER,
    CONSTRAINT truck_warehouse_pk
    PRIMARY KEY(TruckRegistration_plate, WarehouseId_warehouse)
);

CREATE TABLE Employee (
    id  INTEGER CONSTRAINT employee_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT employee_nn NOT NULL,
    Type_Employeetype_id INTEGER,
    PortId INTEGER,
    WarehouseId INTEGER,
    Shipmmsi INTEGER
);

CREATE TABLE Type_Employee (
    type_id INTEGER CONSTRAINT type_employee_pk PRIMARY KEY,
    role VARCHAR(30)
);

CREATE TABLE Message (
    id  INTEGER CONSTRAINT message_pk PRIMARY KEY,
    sog INTEGER,
    cog INTEGER,
    heading INTEGER DEFAULT 511,
    distance DECIMAL(5,2),
    date_t VARCHAR(10),
    LocationId INTEGER,
    Shipmmsi INTEGER,
    CONSTRAINT message_ck CHECK (heading >= 0 AND heading <= 359 AND cog >= 0 AND cog <= 359)
);

CREATE TABLE Container (
    id INTEGER CONSTRAINT container_pk PRIMARY KEY,
    payload DECIMAL(5,2),
    tare DECIMAL(5,2),
    gross DECIMAL(5,2),
    iso_code VARCHAR(30),
    refrigerated INTEGER,
    temperature INTEGER,
    Shipmmsi INTEGER,
    TruckRegistration_plate VARCHAR(8),
    Pos_ContainerId INTEGER
);

CREATE TABLE Cargo_Manifest (
    id INTEGER CONSTRAINT cargo_manifest_pk PRIMARY KEY,
    gross_weight DECIMAL(5,2),
    Type_Cargo_ManifestId INTEGER,
    Shipmmsi INTEGER,
    Truckregistration_plate VARCHAR(8)
);

CREATE TABLE Container_Cargo_Manifest (
    ContainerId_container INTEGER,
    Cargo_ManifestId_cargo_manifest INTEGER,
    CONSTRAINT container_cargo_manifest_pk
    PRIMARY KEY(ContainerId_container, Cargo_ManifestId_cargo_manifest)
);

CREATE TABLE Pos_Container (
    id INTEGER CONSTRAINT pos_container_pk PRIMARY KEY,
    container_x INTEGER,
    container_y INTEGER,
    container_z INTEGER
);

CREATE TABLE Type_Cargo_Manifest (
    id INTEGER CONSTRAINT type_cargo_manifest_pk PRIMARY KEY,
    designation VARCHAR(30) CONSTRAINT designation_nn NOT NULL
);

CREATE TABLE Arrival (
    Cargo_ManifestId INTEGER CONSTRAINT arrival_pk PRIMARY KEY,
    PortId INTEGER,
    WarehouseId INTEGER
);

-- ALTER Tables --

ALTER TABLE Port ADD CONSTRAINT
port_location_fk FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Location ADD CONSTRAINT
location_country_fk FOREIGN KEY (CountryId)
REFERENCES Country(id);

ALTER TABLE Country ADD CONSTRAINT
country_continet_fk FOREIGN KEY (ContinentId)
REFERENCES Continent(id);

ALTER TABLE Warehouse ADD CONSTRAINT
warehouse_location_fk FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Truck ADD CONSTRAINT
truck_employee_fk FOREIGN KEY (EmployeeId_employee)
REFERENCES Employee(id);

ALTER TABLE Employee ADD CONSTRAINT
employee_type_employee_fk FOREIGN KEY (Type_Employeetype_id)
REFERENCES Type_Employee(type_id);

ALTER TABLE Employee ADD CONSTRAINT
employee_port_fk FOREIGN KEY (PortId)
REFERENCES Port(id);

ALTER TABLE Employee ADD CONSTRAINT
employee_warehouse_fk FOREIGN KEY (WarehouseId)
REFERENCES Warehouse(id);

ALTER TABLE Employee ADD CONSTRAINT
employee_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Message ADD CONSTRAINT
message_location_fk FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Message ADD CONSTRAINT
message_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Container ADD CONSTRAINT
container_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Container ADD CONSTRAINT
container_truck_fk FOREIGN KEY (TruckRegistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
cargoManifest_typeCargoManifest_fk FOREIGN KEY (Type_Cargo_ManifestId)
REFERENCES Type_Cargo_Manifest(id);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
cargoManifest_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
cargoManifest_truck_fk FOREIGN KEY (Truckregistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Container ADD CONSTRAINT
container_posContainer_fk FOREIGN KEY (Pos_ContainerId)
REFERENCES Pos_Container(id);

ALTER TABLE Container_Cargo_Manifest ADD CONSTRAINT
containerCargoManifest_container_fk FOREIGN KEY (ContainerId_container)
REFERENCES Container(id);

ALTER TABLE Container_Cargo_Manifest ADD CONSTRAINT
containerCargoManifest_cargoManifest_fk FOREIGN KEY (Cargo_ManifestId_cargo_manifest)
REFERENCES Cargo_Manifest(id);

ALTER TABLE Ship_Port ADD CONSTRAINT
shipPort_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Ship_Port ADD CONSTRAINT
shipPort_port_fk FOREIGN KEY (PortId_port)
REFERENCES Port(id);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
truckWarehouse_truck_fk FOREIGN KEY (TruckRegistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
truckWarehouse_warehouse_fk FOREIGN KEY (WarehouseId_warehouse)
REFERENCES Warehouse(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_cargoManifest_fk FOREIGN KEY (Cargo_ManifestId)
REFERENCES Cargo_Manifest(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_port_fk FOREIGN KEY (PortId)
REFERENCES Port(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_warehouse_fk FOREIGN KEY (WarehouseId)
REFERENCES Warehouse(id);
