@startuml
left to right direction
actor "Traffic Manager" as tm
actor "Project Manager" as pm
actor "Port Manager" as pman
actor "Client" as c
actor "Ship Captain" as sc
actor "Port Staff" as ps
actor "Ship Chief Electrical Engineer" as scee
actor "Fleet Manager" as fm
actor "Ship Master" as sm

  usecase "US101: Import ships from a text file into a BST" as US101
  usecase "US102: Search the details of a ship using any of its codes: MMSI, IMO or Call Sign" as US102
  usecase "US103: Have the positional messages temporally organized and associated with each of the ships" as US103
  usecase "US104: Make a summary of a ship's movements" as US104
  usecase "US105: List for all ships the MMSI, the total number of movements, Travelled Distance and Delta Distance" as US105
  usecase "US108: Develop the data model required to support all the functionality and to fulfill the purpose of the system to develop. This data model is to be designed following a systematic data modeling methodology" as US108
  usecase "US109: Draft an SQL script to test whether the database verifies all the data integrity restrictions that are required to fulfil the purpose of the system and the business constraints of the UoD" as US109
  usecase "US110: Define the naming conventions to apply when defining identifiers or writing SQL or PL/SQL code. The naming conventions may evolve as new database and programming objects are known. The naming conventions guide should be organized in a way to facilitate its maintenance" as US110
  usecase "US111: Create a SQL script to load the database with a minimum set of data sufficient to carry out data integrity verification and functional testing. This script shall produce a bootstrap report providing the number of tuples/rows in each relation/table" as US111
  usecase "US201: Import ports from a text file and create a 2D-tree with port locations" as US201
  usecase "US202: Find the closest port of a ship given its CallSign, on a certain DateTime" as US202
  usecase "US203: Review the relational data model in view of the new user stories so it can support all the requirements to fulfil the purpose of the system being developed" as US203
  usecase "US204: Know the current situation of a specific container being used to transport my goods" as US204
  usecase "US205: List of containers to be offloaded in the next port, including container identifier, type, position, and load" as US205
  usecase "US206: List of containers to be loaded in the next port, including container identifier, type, and load" as US206
  usecase "US207: Know how many cargo manifests I have transported during a given year and the average number of containers per manifest" as US207
  usecase "US208: Know the occupancy rate (percentage) of a given ship for a given cargo manifest. Occupancy rate is the ratio between total number of containers in the ship coming from a given manifest and the total capacity of the ship, i.e., the maximum number of containers the ship can load" as US208
  usecase "US209: Know the occupancy rate of a given ship at a given moment" as US209
  usecase "US210: Know which ships will be available on Monday next week and their location" as US210
  usecase "US301: Import data from countries, ports, borders and seadists files to build a freight network." as US301
  usecase "US302: Colour the map using as few colours as possible." as US302
  usecase "US303: Know which places (cities or ports) are closest to all other places (closeness places)." as US303
  usecase "US304: Have access to audit trails for a given container of a given cargo manifest, that is, I want to have access to a list of all operations performed on a given container of a given manifest, in chronological order." as US304
  usecase "US305: Know the route of a specific container I am leasing." as US305
  usecase "US306: Know the occupancy rate of each warehouse and an estimate of the containers leaving the warehouse during the next 30 days." as US306
  usecase "US307: Get a warning whenever I issue a cargo manifest destined for a warehouse whose available capacity is insufficient to accommodate the new manifest." as US307
  usecase "US308: Have a system that ensures that the number of containers in a manifest does not exceed the ship's available capacity." as US308
  usecase "US309: not allow a cargo manifest for a particular ship to be registered in the system on a date when the ship is already occupied." as US309
  usecase "US310: Have a map of the occupation of the existing resources in the port during a given month." as US310
  usecase "US311: Provide a database access account, with login “crew” and password “bd7wd5aF”, which gives access exclusively to the information of the containers that are loaded on my ship." as US311
  usecase "US312: Know the current situation of a specific container being used to transport my goods – US204." as US312
  usecase "US313: Given a Cargo Manifest, I wish to fill a statically reserved matrix in memory with each container's ID in its respective place." as US313
  usecase "US314: Know the total number of free/occupied slots in the transport vehicle." as US314
  usecase "US315: Given a position in the transport vehicle, I wish to know if a container is there or not." as US315
  usecase "US316: Given a set of positions, I wish to know the total number of occupied slots." as US316
  usecase "US317: Know what set of materials to use in a container, to operate at temperatures of 7°C." as US317
  usecase "US318: Know what set of materials to use in a container, to operate at temperatures of -5 °C." as US318
  usecase "US319: Know the thermal resistance, for each operating temperature, of each container that must contain at least three different materials in its walls. One for the outer wall, one for the intermediate material, and one for the inner wall." as US319
  usecase "US320: Present in a summary document, the choice of materials considered for the two types of containers considered, and their thermal resistances." as US320
  usecase "US401: Know which ports are more critical (have greater centrality) in this freight network." as US401
  usecase "US402: Know the shortest path between two locals (city and/or port)." as US402
  usecase "US403: Know the most efficient circuit that starts from a source location and visits the greatest number of other locations once, returning to the starting location and with the shortest total distance." as US403
  usecase "US404: Know the number of days each ship has been idle since the beginning of the current year." as US404
  usecase "US405: Know the average occupancy rate per manifest of a given ship during a given period." as US405
  usecase "US406: Know which ship voyages – place and date of origin and destination – had an occupancy rate below a certain threshold; by default, consider an occupancy rate threshold of 66%. Only the trips already concluded are to be considered." as US406
  usecase "US407: generate, a week in advance, the loading and unloading map based on ships and trucks load manifests and corresponding travel plans, to anticipate the level of sufficient and necessary resources (loading and unloading staff, warehouse staff, ...)." as US407
  usecase "US408: Develop a data model to build a Data Warehouse to analyse the volume of maritime traffic between any two ports. The fact to be analysed is the traffic volume measured by the indicators “number of containers”, “accumulated number of containers” and “target number of containers”. These indicators refer to the number of containers that are in transit between two ports/locations on the first day of each month. The dimensions to consider are Time, Port of origin and Port of destination. The Time dimension has a hierarchy with the following levels: Year, Month. The location/Port is subject to the following hierarchy: Continent, Country, Port. An estimate of the upper cardinality of the dimension and fact tables must be indicated." as US408
  usecase "US409: wish to fill a dynamically reserved array in memory with all the container's information in its respective place." as US409
  usecase "US410: Given the position of a container, I want to know the amount of needed energy to keep the container at its required temperature." as US410
  usecase "US411: Receive an alert when the current energy generation units are not enough to provide energy to all refrigerated containers at once." as US411
  usecase "US412: Know how much energy to supply, for each container, in a determined trip, with an exterior temperature of 20 oC, and a travel time of 2h30." as US412  
  usecase "US413: Know the total energy to be
supplied to the set of containers in a certain established trip, assuming that all the containers have the same behaviour." as US413
  usecase "US414: know how much energy to supply to the container cargo, in a voyage (or route), depending on the position of the containers on the ship. Admitting that the interior containers, or the sides not exposed directly to the "sun", maintain the initial temperature, or of departure. However, the exposed sides may present temperature variations during the trip." as US414
  usecase "US415: Know how many auxiliary power equipment are needed for the voyage, knowing that each one supplies a maximum of 75 KW." as US415
  usecase "US416: Submit a summary document, with the following items." as US416
  usecase "US417: Search for at least three types of ship/vessels that are better suited to the task (e.g., depending on the type of cargo), in which the “control” bridge can assume three positions, one in the bow, one in the stern, and finally in the midship." as US417
  usecase "US418: Determine the unladen center of mass for each vessel (if different) according to its characteristics. For calculation purposes, consider known geometric figures." as US418
  usecase "US419: Know where to position, for example, one hundred (100) containers on the vessel, such that the center of mass remains at xx and yy, determined in the previous point." as US419
  usecase "US420: Know for a specific vessel, how much did the vessel sink, assuming that each container has half a ton of mass." as US420
  
  

tm --> US101
tm --> US102
tm --> US103
tm --> US104
tm --> US105
tm --> US202
tm --> US210
tm --> US301
tm --> US302
tm --> US303
tm --> US308
tm --> US309
tm --> US401
tm --> US402
tm --> US403

pm --> US108
pm --> US109
pm --> US110
pm --> US111
pm --> US203

pman --> US201
pman --> US306
pman --> US307
pman --> US310
pman --> US407
pman --> US408

c --> US204
c --> US305
c --> US312

sc --> US205
sc --> US206
sc --> US207
sc --> US208
sc --> US209
sc --> US304
sc --> US311
sc --> US415
sc --> US417
sc --> US418
sc --> US419
sc --> US420

ps --> US313
ps --> US314
ps --> US315
ps --> US316
ps --> US409

scee --> US317
scee --> US318
scee --> US319
scee --> US320
scee --> US410
scee --> US411
scee --> US412
scee --> US413
scee --> US414

fm --> US404
fm --> US405
fm --> US406

sm --> US416
@enduml