@startuml
title Relationships - Class Diagram

class Company

class Container {
int id;
float payload;
float tare;
float gross;
string iso_code;
int refrigerated;
float temperature;
}

class Cargo_Manifest {
int id;
float gross_weight;
int pos_container;
}

class Ship {
int MMSI;
string name;
int id_ship;
int number_energy_gen;
fload gen_power_output;
int callsign;
int ship_type;
float length;
float width;
float capacity;
float draft;
}

class Truck {
int registration_plate;
}

class Port {
int id;
string name;
string continent;
string country;
string location;
}

class Warehouse {
int id;
string name;
string continent;
string country;
string location;
}

class Employee {
int id;
string name;
}

Company "1" -- "*" Port: has >
Company "1" -- "*" Warehouse: has >
Port "1" -- "*" Cargo_Manifest: receives a >
Warehouse "1" -- "*" Cargo_Manifest: receives a >
Cargo_Manifest "*" -- "*" Container: has information about >
Ship "1" -- "*" Container: have >
Ship "1" -- "*" Cargo_Manifest: have >
Ship "*" -- "*" Port: docks <
Truck "*" -- "*" Warehouse: receives <
Truck "1" -- "*" Cargo_Manifest: have >
Port "1" -- "*" Employee: has >
Warehouse "1" -- "*" Employee: has >
@enduml