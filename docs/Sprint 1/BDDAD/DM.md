@startuml
title Relationships - Class Diagram

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

class ShipCallSign {

}

class ShipIMO {}

class ShipMMSI {}

class ShipData {
LocalDateTime dateTime;
double latitude;
double longitude;
double sog;
double cog;
double heading;
char transceiverClass;
}

Ship <|-- ShipIMO
Ship <|-- ShipCallSign
Ship <|-- ShipMMSI
Ship "1" -- "*" ShipData: contains >

@enduml