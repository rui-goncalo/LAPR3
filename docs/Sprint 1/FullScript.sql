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
    id  INTEGER     CONSTRAINT port_pk PRIMARY KEY,
    name    VARCHAR(30),
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
    name VARCHAR(30),
    latitude    DECIMAL(5,2) DEFAULT 91.00,
    longitude   DECIMAL(5,2) DEFAULT 181.00,
    CountryId   INTEGER,
    CONSTRAINT location_ck CHECK (latitude >= -90.00 AND latitude <= 90.00 AND longitude >= -180.00 AND longitude <= 180.00)
);

CREATE TABLE Country(
    id  INTEGER CONSTRAINT country_pk PRIMARY KEY,
    name VARCHAR(30),
    ContinentId INTEGER
);

CREATE TABLE Continent (
    id  INTEGER CONSTRAINT continent_pk PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE Truck (
    registration_plate  VARCHAR(8) CONSTRAINT truck_pk PRIMARY KEY,
    driver_id   INTEGER,
    EmployeeId_employee INTEGER
);

CREATE TABLE Warehouse (
    id  INTEGER     CONSTRAINT warehouse_pk PRIMARY KEY,
    name    VARCHAR(30),
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
    name VARCHAR(30),
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
    designation VARCHAR(30)
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

--INSERT Tables
--INSERT SHIP--
INSERT INTO ship VALUES (211331620, 'SEOUlEXPRE', 9193305, 21, 12.3, 'DHBN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331624, 'SEOUlEXPRE', 9193321, 21, 12.3, 'DHKL', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331635, 'SEOUlEXPRE', 9193343, 21, 12.3, 'DHQW', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331643, 'SEOUlEXPRE', 9193334, 21, 12.3, 'DHER', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331678, 'SEOUlEXPRE', 9193323, 21, 12.3, 'DHTY', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331696, 'SEOUlEXPRE', 9193312, 21, 12.3, 'DHUI', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331683, 'SEOUlEXPRE', 9193332, 21, 12.3, 'DHOP', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331619, 'SEOUlEXPRE', 9193303, 21, 12.3, 'DHAS', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331610, 'SEOUlEXPRE', 9193365, 21, 12.3, 'DHDF', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331612, 'SEOUlEXPRE', 9193356, 21, 12.3, 'DHGH', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211331, 'SEOUlEXPRE', 91933, 21, 12.3, 'DH', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);

SELECT * FROM ship;

--INSERT CONTINENT--
INSERT INTO continent VALUES (1, 'Europe');
INSERT INTO continent VALUES (2, 'America');
INSERT INTO continent VALUES (3, 'Africa');

SELECT * FROM continent;

--INSERT COUNTRY--
INSERT INTO country VALUES (1, 'UK', 1);
INSERT INTO country VALUES (2, 'US', 2);
INSERT INTO country VALUES (3, 'Mexico', 2);
INSERT INTO country VALUES (4, 'Brasil', 2);
INSERT INTO country VALUES (5, 'Peru', 2);
INSERT INTO country VALUES (6, 'Morocco', 3);
INSERT INTO country VALUES (7, 'Angola', 3);
INSERT INTO country VALUES (8, 'Turkey', 1);
INSERT INTO country VALUES (9, 'Italy', 1);
INSERT INTO country VALUES (10, 'Canada', 2);

SELECT * FROM country;

--INSERT LOCATION--
INSERT INTO location VALUES (1, 'Liverpool', 34.91666667, 33.65, 1);
INSERT INTO location VALUES (2, 'Los Angeles', 34.91666667, 33.65, 2);
INSERT INTO location VALUES (3, 'New Jersey', 34.91666667, 33.65, 2);
INSERT INTO location VALUES (4, 'Rio Grande', 34.91666667, 33.65, 4);
INSERT INTO location VALUES (5, 'Salvador', 34.91666667, 33.65, 4);
INSERT INTO location VALUES (6, 'São Paulo', 34.91666667, 33.65, 4);
INSERT INTO location VALUES (7, 'Halifax', 34.91666667, 33.65, 10);
INSERT INTO location VALUES (8, 'Vancouver', 34.91666667, 33.65, 10);
INSERT INTO location VALUES (9, 'San Vicente', 34.91666667, 33.65, 4);
INSERT INTO location VALUES (10, 'Pisa', 34.91666667, 33.65, 9);

SELECT * FROM location;

--INSERT PORT--
INSERT INTO Port VALUES (29002, 'LIV', 350, 1);
INSERT INTO Port VALUES (14635, 'LA', 400, 2);
INSERT INTO Port VALUES (25007, 'NJ', 500, 2);
INSERT INTO Port VALUES (20301, 'RG', 1250, 4);
INSERT INTO Port VALUES (20351, 'SAL', 210, 4);
INSERT INTO Port VALUES (27248, 'SAN', 789, 4);
INSERT INTO Port VALUES (22226, 'HAL', 567, 10);
INSERT INTO Port VALUES (25350, 'VNC', 345, 10);
INSERT INTO Port VALUES (27792, 'SVC', 908, 4);
INSERT INTO Port VALUES (28082, 'VAL', 890, 9);

SELECT * FROM port;

--INSERT WAREHOUSE--
INSERT INTO warehouse VALUES (345, 'JGT', 200, 1);
INSERT INTO warehouse VALUES (349, 'JLD', 201, 2);
INSERT INTO warehouse VALUES (341, 'JBC', 202, 3);
INSERT INTO warehouse VALUES (340, 'JQP', 203, 4);
INSERT INTO warehouse VALUES (342, 'JÇA', 204, 5);
INSERT INTO warehouse VALUES (343, 'JXQ', 205, 6);
INSERT INTO warehouse VALUES (332, 'JRE', 206, 7);
INSERT INTO warehouse VALUES (320, 'JIU', 207, 8);
INSERT INTO warehouse VALUES (310, 'JLN', 208, 9);
INSERT INTO warehouse VALUES (301, 'JMA', 209, 10);

SELECT * FROM warehouse;

--INSERT TYPE_EMPLOYEE--
INSERT INTO type_employee VALUES (34, 'Driver');
INSERT INTO type_employee VALUES (31, 'Driver');
INSERT INTO type_employee VALUES (32, 'Driver');
INSERT INTO type_employee VALUES (33, 'Driver');
INSERT INTO type_employee VALUES (35, 'Driver');
INSERT INTO type_employee VALUES (36, 'Driver');
INSERT INTO type_employee VALUES (37, 'Driver');
INSERT INTO type_employee VALUES (38, 'Driver');
INSERT INTO type_employee VALUES (39, 'Driver');
INSERT INTO type_employee VALUES (40, 'Driver');

SELECT * FROM type_employee;

--INSERT EMPLOYEE--
INSERT INTO employee VALUES (145, 'John', 34, 29002, 345, 211331);
INSERT INTO employee VALUES (141, 'John', 31, 14635, 349, 211331620);
INSERT INTO employee VALUES (142, 'John', 32, 25007, 341, 211331624);
INSERT INTO employee VALUES (143, 'John', 33, 20301, 340, 211331635);
INSERT INTO employee VALUES (144, 'John', 35, 20351, 342, 211331643);
INSERT INTO employee VALUES (146, 'John', 36, 27248, 343, 211331678);
INSERT INTO employee VALUES (147, 'John', 37, 22226, 332, 211331696);
INSERT INTO employee VALUES (148, 'John', 38, 25350, 320, 211331683);
INSERT INTO employee VALUES (149, 'John', 39, 27792, 310, 211331610);
INSERT INTO employee VALUES (150, 'John', 40, 28082, 301, 211331612);

SELECT * FROM employee;

--INSERT TRUCK--
INSERT INTO truck VALUES ('Kotka', 567, 145);
INSERT INTO truck VALUES ('Kotk', 561, 141);
INSERT INTO truck VALUES ('Kot', 562, 142);
INSERT INTO truck VALUES ('Ko', 563, 143);
INSERT INTO truck VALUES ('K', 564, 144);
INSERT INTO truck VALUES ('O', 565, 146);
INSERT INTO truck VALUES ('OL', 566, 147);
INSERT INTO truck VALUES ('OLA', 568, 148);
INSERT INTO truck VALUES ('A', 569, 149);
INSERT INTO truck VALUES ('AL', 570, 150);

SELECT * FROM truck;

--INSERT TRUCK_WAREHOUSE--
INSERT INTO truck_warehouse VALUES ('Kotka', 345);
INSERT INTO truck_warehouse VALUES ('Kotk', 349);
INSERT INTO truck_warehouse VALUES ('Kot', 341);
INSERT INTO truck_warehouse VALUES ('Ko', 340);
INSERT INTO truck_warehouse VALUES ('K', 342);
INSERT INTO truck_warehouse VALUES ('O', 343);
INSERT INTO truck_warehouse VALUES ('OL', 332);
INSERT INTO truck_warehouse VALUES ('OLA', 320);
INSERT INTO truck_warehouse VALUES ('A', 310);
INSERT INTO truck_warehouse VALUES ('AL', 301);

SELECT * FROM truck_warehouse;

--INSERT TYPE_CARGO_MANIFEST--
INSERT INTO type_cargo_manifest VALUES (12, 'driver');
INSERT INTO type_cargo_manifest VALUES (1, 'driver');
INSERT INTO type_cargo_manifest VALUES (2, 'driver');
INSERT INTO type_cargo_manifest VALUES (3, 'driver');
INSERT INTO type_cargo_manifest VALUES (4, 'driver');
INSERT INTO type_cargo_manifest VALUES (5, 'driver');
INSERT INTO type_cargo_manifest VALUES (6, 'driver');
INSERT INTO type_cargo_manifest VALUES (7, 'driver');
INSERT INTO type_cargo_manifest VALUES (8, 'driver');
INSERT INTO type_cargo_manifest VALUES (9, 'driver');

SELECT * FROM type_cargo_manifest;

--INSERT CARGO_MANIFEST--
INSERT INTO cargo_manifest VALUES (326, 5.3, 12, 211331620, 'Kotka');
INSERT INTO cargo_manifest VALUES (321, 5.3, 1, 211331624, 'Kotk');
INSERT INTO cargo_manifest VALUES (322, 5.3, 2, 211331635, 'Kot');
INSERT INTO cargo_manifest VALUES (323, 5.3, 3, 211331643, 'Ko');
INSERT INTO cargo_manifest VALUES (324, 5.3, 4, 211331678, 'K');
INSERT INTO cargo_manifest VALUES (325, 5.3, 5, 211331696, 'O');
INSERT INTO cargo_manifest VALUES (327, 5.3, 6, 211331683, 'OL');
INSERT INTO cargo_manifest VALUES (328, 5.3, 7, 211331610, 'OLA');
INSERT INTO cargo_manifest VALUES (329, 5.3, 8, 211331612, 'A');
INSERT INTO cargo_manifest VALUES (330, 5.3, 9, 211331, 'AL');

SELECT * FROM cargo_manifest;

--INSERT ARRIVAL--
INSERT INTO arrival VALUES (326, 29002, 345);
INSERT INTO arrival VALUES (321, 14635, 349);
INSERT INTO arrival VALUES (322, 25007, 341);
INSERT INTO arrival VALUES (323, 20301, 340);
INSERT INTO arrival VALUES (324, 20351, 342);
INSERT INTO arrival VALUES (325, 27248, 343);
INSERT INTO arrival VALUES (327, 22226, 332);
INSERT INTO arrival VALUES (328, 25350, 320);
INSERT INTO arrival VALUES (329, 27792, 310);
INSERT INTO arrival VALUES (330, 28082, 301);

SELECT * FROM arrival;

--INSERT SHIP_PORT--
INSERT INTO ship_port VALUES (211331620, 29002);
INSERT INTO ship_port VALUES (211331624, 14635);
INSERT INTO ship_port VALUES (211331635, 25007);
INSERT INTO ship_port VALUES (211331643, 27792);
INSERT INTO ship_port VALUES (211331678, 20301);
INSERT INTO ship_port VALUES (211331696, 27248);
INSERT INTO ship_port VALUES (211331683, 20351);
INSERT INTO ship_port VALUES (211331610, 22226);
INSERT INTO ship_port VALUES (211331612, 25350);
INSERT INTO ship_port VALUES (211331, 28082);

SELECT * FROM ship_port;

--INSERT MESSAGE--
INSERT INTO message VALUES (456, 12, 13, 355, 321.5, 'MONDAY', 1, 211331620);
INSERT INTO message VALUES (451, 12, 13, 355, 321.5, 'MONDAY', 2, 211331624);
INSERT INTO message VALUES (452, 12, 13, 355, 321.5, 'MONDAY', 3, 211331635);
INSERT INTO message VALUES (453, 12, 13, 355, 321.5, 'MONDAY', 4, 211331643);
INSERT INTO message VALUES (454, 12, 13, 355, 321.5, 'MONDAY', 5, 211331678);
INSERT INTO message VALUES (455, 12, 13, 355, 321.5, 'MONDAY', 6, 211331696);
INSERT INTO message VALUES (457, 12, 13, 355, 321.5, 'MONDAY', 7, 211331683);
INSERT INTO message VALUES (458, 12, 13, 355, 321.5, 'MONDAY', 8, 211331610);
INSERT INTO message VALUES (459, 12, 13, 355, 321.5, 'MONDAY', 9, 211331612);
INSERT INTO message VALUES (460, 12, 13, 355, 321.5, 'MONDAY', 10, 211331);

SELECT * FROM message;

--INSERT POS_CONTAINER--
INSERT INTO pos_container VALUES (912, 650, 230, 198);
INSERT INTO pos_container VALUES (911, 650, 230, 198);
INSERT INTO pos_container VALUES (913, 650, 230, 198);
INSERT INTO pos_container VALUES (914, 650, 230, 198);
INSERT INTO pos_container VALUES (915, 650, 230, 198);
INSERT INTO pos_container VALUES (916, 650, 230, 198);
INSERT INTO pos_container VALUES (917, 650, 230, 198);
INSERT INTO pos_container VALUES (918, 650, 230, 198);
INSERT INTO pos_container VALUES (919, 650, 230, 198);
INSERT INTO pos_container VALUES (920, 650, 230, 198);

SELECT * FROM pos_container;

--INSERT CONTAINER--
INSERT INTO container VALUES (19, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331620, 'Kotka', 912);
INSERT INTO container VALUES (11, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331624, 'Kotk', 911);
INSERT INTO container VALUES (12, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331635, 'Kot', 913);
INSERT INTO container VALUES (13, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331643, 'Ko', 914);
INSERT INTO container VALUES (14, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331678, 'K', 915);
INSERT INTO container VALUES (15, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331696, 'O', 916);
INSERT INTO container VALUES (16, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331683, 'OL', 917);
INSERT INTO container VALUES (17, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331610, 'OLA', 918);
INSERT INTO container VALUES (18, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331612, 'A', 919);
INSERT INTO container VALUES (20, 124.2, 198.2, 876.4, 'ABGT', 9, 43, 211331, 'AL', 920);

SELECT * FROM container;

--INSERT CONTAINER_CARGO_MANIFEST--
INSERT INTO container_cargo_manifest VALUES (19, 326);
INSERT INTO container_cargo_manifest VALUES (11, 321);
INSERT INTO container_cargo_manifest VALUES (12, 322);
INSERT INTO container_cargo_manifest VALUES (13, 323);
INSERT INTO container_cargo_manifest VALUES (14, 324);
INSERT INTO container_cargo_manifest VALUES (15, 325);
INSERT INTO container_cargo_manifest VALUES (16, 327);
INSERT INTO container_cargo_manifest VALUES (17, 328);
INSERT INTO container_cargo_manifest VALUES (18, 329);
INSERT INTO container_cargo_manifest VALUES (20, 330);

SELECT * FROM container_cargo_manifest;



--TESTING INTEGRITY RESTRICTIONS

-- Na execução destas linhas, a 2ª deverá dar erro uma vez que
-- o IMO é um atributo UNIQUE e está a ser repetidos.
INSERT INTO ship VALUES (111111111, 'SEOUlEXPRE', 1010100, 21, 12.3, 'DABN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (111111112, 'SEOUlEXPRE', 1010100, 21, 12.3, 'DBKL', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);

-- Na execução destas linhas, a 2ª deverá dar erro uma vez que
-- o CALLSIGN é um atributo UNIQUE e está a ser repetidos.
INSERT INTO ship VALUES (211334620, 'SEOUlEXPRE', 1010101, 21, 12.3, 'DHCN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211335624, 'SEOUlEXPRE', 1010110, 21, 12.3, 'DHCN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);

-- Na execução da linha abaixo deverá dar erro no atributo name
-- da tabela Port por exceder o máximo de 30 caracteres previamente definidos
INSERT INTO Port VALUES (29102, 'MaisDeTrintaCaracteresNumVarcharParaDarErro', 350, 1);

-- Na execução destas linhas:
-- A linha abaixo deverá dar erro pela já existência de um registo com a mesma PK.
INSERT INTO location VALUES (1, 'Liverpool', 34.91666667, 33.65, 1);
-- A linha abaixo deverá dar erro por exceder a verificação com o check do intervalo
-- de valores permitidos para o atributo latitude
INSERT INTO location VALUES (11, 'Los Angeles', 94, 33.65, 2);
-- A linha abaixo deverá dar erro por exceder a verificação com o check do intervalo
-- de valores permitidos para o atributo longitude
INSERT INTO location VALUES (12, 'New Jersey', 34.91666667, 190, 2);

-- Na execução da linha abaixo, deverá dar erro devido à inexistência da fk com o id=4.
INSERT INTO country VALUES (11, 'UK', 4);
