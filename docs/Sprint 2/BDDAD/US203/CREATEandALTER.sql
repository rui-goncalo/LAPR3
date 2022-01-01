-- DROP Tables --
DROP TABLE Ship CASCADE CONSTRAINTS PURGE;
DROP TABLE Ship_Port CASCADE CONSTRAINTS PURGE;
DROP TABLE Container CASCADE CONSTRAINTS PURGE;
DROP TABLE Truck CASCADE CONSTRAINTS PURGE;
DROP TABLE Truck_Warehouse CASCADE CONSTRAINTS PURGE;
DROP TABLE Message CASCADE CONSTRAINTS PURGE;
DROP TABLE Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Container_Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Pos_Container CASCADE CONSTRAINTS PURGE;
DROP TABLE Type_Cargo_Manifest CASCADE CONSTRAINTS PURGE;
DROP TABLE Location CASCADE CONSTRAINTS PURGE;
DROP TABLE Type_Location CASCADE CONSTRAINTS PURGE;
DROP TABLE Country CASCADE CONSTRAINTS PURGE;
DROP TABLE Continent CASCADE CONSTRAINTS PURGE;
DROP TABLE Employee CASCADE CONSTRAINTS PURGE;
DROP TABLE Type_Employee CASCADE CONSTRAINTS PURGE;
DROP TABLE Arrival CASCADE CONSTRAINTS PURGE;
DROP TABLE Employee_Location CASCADE CONSTRAINTS PURGE;
DROP TABLE Ship_Crew CASCADE CONSTRAINTS PURGE;
DROP TABLE Role CASCADE CONSTRAINTS PURGE;
DROP TABLE "User" CASCADE CONSTRAINTS PURGE;
DROP TABLE Container_Refrigerated CASCADE CONSTRAINTS PURGE;
DROP TABLE Trip CASCADE CONSTRAINTS PURGE;
DROP TABLE Client CASCADE CONSTRAINTS PURGE;
DROP TABLE Container_Client CASCADE CONSTRAINTS PURGE;

-- CREATE Tables --
CREATE TABLE Ship (
    mmsi    INTEGER     CONSTRAINT ship_pk PRIMARY KEY,
    name  VARCHAR(30) CONSTRAINT ship_nn NOT NULL,
    imo     INTEGER UNIQUE,
    number_energy_gen   INTEGER,
    gen_power_output    DECIMAL(5,2),
    callsign    VARCHAR(10) UNIQUE,
    vessel INTEGER,
    length    DECIMAL(5,2),
    Width   DECIMAL(5,2),
    capacity  INTEGER,
    draft   DECIMAL(5,2),
    transceiver_class   VARCHAR(50),
    code    INTEGER
);

CREATE TABLE Ship_Port (
    Shipmmsi INTEGER,
    Locationid INTEGER,
    CONSTRAINT ship_location_pk
    PRIMARY KEY(Shipmmsi, Locationid)
);

CREATE TABLE Location (
    id  INTEGER CONSTRAINT location_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT location_nn NOT NULL,
    latitude    DECIMAL(5,2) DEFAULT 91.00,
    longitude   DECIMAL(5,2) DEFAULT 181.00,
    Type_Locationid INTEGER,
    CountryId   INTEGER,
    CONSTRAINT location_ck CHECK (latitude >= -90.00 AND latitude <= 90.00 AND longitude >= -180.00 AND longitude <= 180.00)
);

CREATE TABLE Type_Location (
    id INTEGER CONSTRAINT type_location_pk PRIMARY KEY,
    description VARCHAR(10)
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
    EmployeeId_employee INTEGER
);

CREATE TABLE Truck_Warehouse (
    TruckRegistration_plate VARCHAR(8),
    Locationid INTEGER,
    CONSTRAINT truck_warehouse_pk
    PRIMARY KEY(TruckRegistration_plate, Locationid)
);

CREATE TABLE Employee (
    id  INTEGER CONSTRAINT employee_pk PRIMARY KEY,
    name VARCHAR(30) CONSTRAINT employee_nn NOT NULL,
    Type_Employeetype_id INTEGER
);


CREATE TABLE Employee_Location (
    Employeeid INTEGER,
    Locationid INTEGER,
    CONSTRAINT employee_location_pk
    PRIMARY KEY (Employeeid, Locationid)
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
    date_t DATE,
    LocationId INTEGER,
    Shipmmsi INTEGER,
    CONSTRAINT message_ck CHECK (heading >= 0 AND heading <= 359 AND cog >= 0 AND cog <= 359)
);

CREATE TABLE Container_Refrigerated (
    id INTEGER CONSTRAINT container_refrigerated_pk PRIMARY KEY,
    temperature INTEGER
);

CREATE TABLE Container (
    id INTEGER CONSTRAINT container_pk PRIMARY KEY,
    payload DECIMAL(5,2),
    tare DECIMAL(5,2),
    gross DECIMAL(5,2),
    iso_code VARCHAR(30),
    Container_Refrigeratedid INTEGER
);

CREATE TABLE Cargo_Manifest (
    id INTEGER CONSTRAINT cargo_manifest_pk PRIMARY KEY,
    gross_weight DECIMAL(5,2),
    year INTEGER,
    Type_Cargo_ManifestId INTEGER,
    Shipmmsi INTEGER
);

CREATE TABLE Container_Cargo_Manifest (
    ContainerId_container INTEGER,
    Cargo_ManifestId_cargo_manifest INTEGER,
    CONSTRAINT container_cargo_manifest_pk
    PRIMARY KEY(ContainerId_container, Cargo_ManifestId_cargo_manifest),
    Pos_ContainerId INTEGER
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
    id INTEGER,
    TripId INTEGER,
    CONSTRAINT arrival_pk PRIMARY KEY (id, TripId),
    arrival_date DATE CONSTRAINT arrival_nn NOT NULL,
    Cargo_ManifestId INTEGER,
    InitialLocationId INTEGER,
    DestinationLocationId INTEGER
);

CREATE TABLE Ship_Crew (
    Employeeid INTEGER,
    Shipmmsi INTEGER,
    CONSTRAINT ship_crew_pk
    PRIMARY KEY(Employeeid, Shipmmsi)
);

CREATE TABLE Role (
    id INTEGER CONSTRAINT role_pk PRIMARY KEY,
    description VARCHAR(30)
);

CREATE TABLE "User"(
    username VARCHAR(20) CONSTRAINT user_pk PRIMARY KEY,
    password VARCHAR(20),
    Employeeid INTEGER,
    Roleid INTEGER
);

CREATE TABLE Trip (
    id INTEGER CONSTRAINT trip_pk PRIMARY KEY,
    initial_date DATE,
    final_date DATE,
    InitialLocationId INTEGER,
    DestinationLocationId INTEGER,
    Shipmmsi INTEGER
);

CREATE TABLE Client (
    id INTEGER CONSTRAINT Client_pk PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Container_Client(
    ContainerId INTEGER,
    ClientId INTEGER,
    CONSTRAINT Container_Client_pk
    PRIMARY KEY (ContainerId, ClientId)
);

-- ALTER Tables --
ALTER TABLE Location ADD CONSTRAINT
location_type_location_fk FOREIGN KEY (Type_Locationid)
REFERENCES Type_Location(id);

ALTER TABLE Location ADD CONSTRAINT
location_country_fk FOREIGN KEY (CountryId)
REFERENCES Country(id);

ALTER TABLE Country ADD CONSTRAINT
country_continet_fk FOREIGN KEY (ContinentId)
REFERENCES Continent(id);

ALTER TABLE Truck ADD CONSTRAINT
truck_employee_fk FOREIGN KEY (EmployeeId_employee)
REFERENCES Employee(id);

ALTER TABLE Employee ADD CONSTRAINT
employee_type_employee_fk FOREIGN KEY (Type_Employeetype_id)
REFERENCES Type_Employee(type_id);

ALTER TABLE Employee_Location ADD CONSTRAINT
employee_employee_location_fk FOREIGN KEY (Employeeid)
REFERENCES Employee(id);

ALTER TABLE Employee_Location ADD CONSTRAINT
location_employee_location_fk FOREIGN KEY (Locationid)
REFERENCES Location(id);

ALTER TABLE Message ADD CONSTRAINT
message_location_fk FOREIGN KEY (LocationId)
REFERENCES Location(id);

ALTER TABLE Message ADD CONSTRAINT
message_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
cargoManifest_typeCargoManifest_fk FOREIGN KEY (Type_Cargo_ManifestId)
REFERENCES Type_Cargo_Manifest(id);

ALTER TABLE Cargo_Manifest ADD CONSTRAINT
cargoManifest_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Container_Cargo_Manifest ADD CONSTRAINT
containercargomanifest_posContainer_fk FOREIGN KEY (Pos_ContainerId)
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
shipPort_location_fk FOREIGN KEY (Locationid)
REFERENCES Location(id);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
truckWarehouse_truck_fk FOREIGN KEY (TruckRegistration_plate)
REFERENCES Truck(registration_plate);

ALTER TABLE Truck_Warehouse ADD CONSTRAINT
truckWarehouse_location_fk FOREIGN KEY (Locationid)
REFERENCES Location(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_cargoManifest_fk FOREIGN KEY (Cargo_ManifestId)
REFERENCES Cargo_Manifest(id);

ALTER TABLE Ship_Crew ADD CONSTRAINT
employee_ship_crew_fk FOREIGN KEY (Employeeid)
REFERENCES Employee(id);

ALTER TABLE Ship_Crew ADD CONSTRAINT
ship_ship_crew_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE "User" ADD CONSTRAINT
role_user_fk FOREIGN KEY (Roleid)
REFERENCES Role(id);

ALTER TABLE "User" ADD CONSTRAINT
employee_user_fk FOREIGN KEY (Employeeid)
REFERENCES Employee(id);

ALTER TABLE Container ADD CONSTRAINT
container_refrigerated_container_fk FOREIGN KEY (Container_Refrigeratedid)
REFERENCES Container_Refrigerated(id);

ALTER TABLE Trip ADD CONSTRAINT
trip_initial_location_fk FOREIGN KEY (InitialLocationId)
REFERENCES Location(id);

ALTER TABLE Trip ADD CONSTRAINT
trip_destination_location_fk FOREIGN KEY (DestinationLocationId)
REFERENCES Location(id);

ALTER TABLE Trip ADD CONSTRAINT
trip_ship_fk FOREIGN KEY (Shipmmsi)
REFERENCES Ship(mmsi);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_initial_location_fk FOREIGN KEY (InitialLocationId)
REFERENCES Location(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_destination_location_fk FOREIGN KEY (DestinationLocationId)
REFERENCES Location(id);

ALTER TABLE Arrival ADD CONSTRAINT
arrival_tripId_fk FOREIGN KEY (TripId)
REFERENCES Trip(id);

ALTER TABLE Container_Client ADD CONSTRAINT
Container_Client_ContainerId_fk FOREIGN KEY (ContainerId)
REFERENCES Container(id);

ALTER TABLE Container_Client ADD CONSTRAINT
Container_Client_ClientId_fk FOREIGN KEY (ClientId)
REFERENCES Client(id);