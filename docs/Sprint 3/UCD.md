@startuml
left to right direction
actor "Traffic Manager" as tm
actor "Project Manager" as pm
actor "Port Manager" as pman
actor "Client" as c
actor "Ship Captain" as sc
actor "Port Staff" as ps
actor "Ship Chief Electrical Engineer" as scee

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

pm --> US108
pm --> US109
pm --> US110
pm --> US111
pm --> US203

pman --> US201
pman --> US306
pman --> US307
pman --> US310

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

ps --> US313
ps --> US314
ps --> US315
ps --> US316

scee --> US317
scee --> US318
scee --> US319
scee --> US320
@enduml