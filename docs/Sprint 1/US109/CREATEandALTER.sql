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
    mmsi    INTEGER     CONSTRAINT pkMMSIShip PRIMARY KEY,
    name  VARCHAR(30),
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
    id  INTEGER     CONSTRAINT pkIdPort PRIMARY KEY,
    name    VARCHAR(30),
    capacity    INTEGER,
    LocationId INTEGER
);

CREATE TABLE Ship_Port (
    Shipmmsi INTEGER,
    PortId_port INTEGER,
    CONSTRAINT pkShipmmsi_PortId_Ship_Port
    PRIMARY KEY(Shipmmsi, PortId_port)
);

CREATE TABLE Location (
    id  INTEGER CONSTRAINT pkIdLocation PRIMARY KEY,
    name VARCHAR(30),
    latitude    DECIMAL(5,2) DEFAULT 91.00,
    longitude   DECIMAL(5,2) DEFAULT 181.00,
    CountryId   INTEGER,
    CONSTRAINT ck_Location CHECK (latitude >= -90.00 AND latitude <= 90.00 AND longitude >= -180.00 AND longitude <= 180.00)
);

CREATE TABLE Country(
    id  INTEGER CONSTRAINT pkIdCountry PRIMARY KEY,
    name VARCHAR(30),
    ContinentId INTEGER
);

CREATE TABLE Continent (
    id  INTEGER CONSTRAINT pkIdContinent PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Truck (
    registration_plate  VARCHAR(8) CONSTRAINT pkRegTruck PRIMARY KEY,
    driver_id   INTEGER,
    EmployeeId_employee INTEGER
);

CREATE TABLE Warehouse (
    id  INTEGER     CONSTRAINT pkIdWarehouse PRIMARY KEY,
    name    VARCHAR(30),
    capacity    INTEGER,
    LocationId INTEGER
);

CREATE TABLE Truck_Warehouse (
    TruckRegistration_plate VARCHAR(8),
    WarehouseId_warehouse INTEGER,
    CONSTRAINT pkTruckReg_WarehouseId_Truck_Warehouse
    PRIMARY KEY(TruckRegistration_plate, WarehouseId_warehouse)
);

CREATE TABLE Employee (
    id  INTEGER CONSTRAINT pkIdEmployee PRIMARY KEY,
    name VARCHAR(30),
    Type_Employeetype_id INTEGER,
    PortId INTEGER,
    WarehouseId INTEGER,
    Shipmmsi INTEGER
);

CREATE TABLE Type_Employee (
    type_id INTEGER CONSTRAINT pkType_Id_Type_Employee PRIMARY KEY,
    role VARCHAR(30)
);

CREATE TABLE Message (
    id  INTEGER CONSTRAINT pkIdMessage PRIMARY KEY,
    sog INTEGER,
    cog INTEGER,
    heading INTEGER DEFAULT 511,
    distance DECIMAL(5,2),
    date_t VARCHAR(10),
    LocationId INTEGER,
    Shipmmsi INTEGER,
    CONSTRAINT ck_Message CHECK (heading >= 0 AND heading <= 359 AND cog >= 0 AND cog <= 359)
);

CREATE TABLE Container (
    id INTEGER CONSTRAINT pkIdContainer PRIMARY KEY,
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
    id INTEGER CONSTRAINT pkIdCargo_Manifest PRIMARY KEY,
    gross_weight DECIMAL(5,2),
    source INTEGER,
    destination INTEGER,
    Type_Cargo_ManifestId INTEGER,
    Shipmmsi INTEGER,
    Truckregistration_plate VARCHAR(8),
    PortId INTEGER,
    WarehouseId INTEGER
);

CREATE TABLE Container_Cargo_Manifest (
    ContainerId_container INTEGER,
    Cargo_ManifestId_cargo_manifest INTEGER,
    CONSTRAINT pkid_container_id_cargo_manifest
    PRIMARY KEY(ContainerId_container, Cargo_ManifestId_cargo_manifest)
);

CREATE TABLE Pos_Container (
    id INTEGER CONSTRAINT pkIdPos_Container PRIMARY KEY,
    container_x INTEGER,
    container_y INTEGER,
    container_z INTEGER
);

CREATE TABLE Type_Cargo_Manifest (
    id INTEGER CONSTRAINT pkIdType_Cargo_Manifest PRIMARY KEY,
    designation VARCHAR(30)
);

CREATE TABLE Arrival (
    Cargo_ManifestId INTEGER CONSTRAINT pkArrival PRIMARY KEY,
    PortId INTEGER,
    WarehouseId INTEGER
);

-- ALTER Tables --
--ALTER TABLE Ship ADD CONSTRAINT
--fkPortId_Ship FOREIGN KEY (PortId)
--REFERENCES Port(id);

ALTER TABLE Port ADD CONSTRAINT
fkLocationId_Port FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Location ADD CONSTRAINT
fkCountryId_Location FOREIGN KEY (CountryId)
REFERENCES Country(id);

ALTER TABLE Country ADD CONSTRAINT
fkContinentId_Country FOREIGN KEY (ContinentId)
REFERENCES Continent(id);

ALTER TABLE Warehouse ADD CONSTRAINT
fkLocationId_Warehouse FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Truck ADD CONSTRAINT
fkEmployeeId_Truck FOREIGN KEY (EmployeeId_employee)
REFERENCES Employee(id);

ALTER TABLE Employee ADD CONSTRAINT
fkType_Employee_Employee FOREIGN KEY (Type_Employeetype_id)
REFERENCES Type_Employee(type_id);

ALTER TABLE Employee ADD CONSTRAINT
fkPortId_Employee FOREIGN KEY (PortId)
REFERENCES Port(id);

ALTER TABLE Employee ADD CONSTRAINT
fkWarehouseId_Employee FOREIGN KEY (WarehouseId)
REFERENCES Warehouse(id);

ALTER TABLE Employee ADD CONSTRAINT
fkShipmmsi_Employee FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Message ADD CONSTRAINT
fkLocationId_Message FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Message ADD CONSTRAINT
fkShipmmsi_Message FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Container ADD CONSTRAINT
fkShipmmsi_Container FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Container ADD CONSTRAINT
fkTruckReg_Container FOREIGN KEY (TruckRegistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
fkType_Cargo_ManifestId_Cargo_Manifest FOREIGN KEY (Type_Cargo_ManifestId)
REFERENCES Type_Cargo_Manifest(id);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
fkShipmmsi_Cargo_Manifest FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
fkTruckReg_Cargo_Manifest FOREIGN KEY (Truckregistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
fkPortId_Cargo_Manifest FOREIGN KEY (PortId)
REFERENCES Port(id);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
fkWarehouseId_Cargo_Manifest FOREIGN KEY (WarehouseId)
REFERENCES Warehouse(id);

ALTER TABLE Container ADD CONSTRAINT
fkPos_ContainerId_Container FOREIGN KEY (Pos_ContainerId)
REFERENCES Pos_Container(id);

ALTER TABLE Container_Cargo_Manifest ADD CONSTRAINT
fkContainerId_container_cargo_manifest FOREIGN KEY (ContainerId_container)
REFERENCES Container(id);

ALTER TABLE Container_Cargo_Manifest ADD CONSTRAINT
fkCargo_ManifestId_cargo_manifest_cargo_manifest FOREIGN KEY (Cargo_ManifestId_cargo_manifest)
REFERENCES Cargo_Manifest(id);

ALTER TABLE Ship_Port ADD CONSTRAINT
fkShipmmsiShip_Port FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Ship_Port ADD CONSTRAINT
fkPortId_Ship_Port FOREIGN KEY (PortId_port)
REFERENCES Port(id);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
fkTruckReg_Truck_Warehouse FOREIGN KEY (TruckRegistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
fkWarehouseId_Truck_Warehouse FOREIGN KEY (WarehouseId_warehouse)
REFERENCES Warehouse(id);

ALTER TABLE Arrival ADD CONSTRAINT
fkCargo_ManifestId_Arrival FOREIGN KEY (Cargo_ManifestId)
REFERENCES Cargo_Manifest(id);

ALTER TABLE Arrival ADD CONSTRAINT
fkPortId_Arrival FOREIGN KEY (PortId)
REFERENCES Port(id);

ALTER TABLE Arrival ADD CONSTRAINT
fkWarehouseId_Arrival FOREIGN KEY (WarehouseId)
REFERENCES Warehouse(id);