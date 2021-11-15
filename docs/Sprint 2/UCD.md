@startuml
left to right direction
actor "Traffic Manager" as tm
actor "Project Manager" as pm
actor "Port Manager" as pman
actor "Client" as c
actor "Ship Captain" as sc

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

tm --> US101
tm --> US102
tm --> US103
tm --> US104
tm --> US105
tm --> US202
tm --> US210

pm --> US108
pm --> US109
pm --> US110
pm --> US111
pm --> US203

pman --> US201

c --> US204

sc --> US205
sc --> US206
sc --> US207
sc --> US208
sc --> US209
@enduml