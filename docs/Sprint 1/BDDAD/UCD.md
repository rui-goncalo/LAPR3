@startuml
left to right direction
actor "Traffic Manager" as tm
actor "Project Manager" as pm

  usecase "US101: Import ships from a text file into a BST" as US101
  usecase "US102: Search the details of a ship using any of its codes: MMSI, IMO or Call Sign" as US102
  usecase "US103: Have the positional messages temporally organized and associated with each of the ships" as US103
  usecase "US104: Make a summary of a ship's movements" as US104
  usecase "US105: List for all ships the MMSI, the total number of movements, Travelled Distance and Delta Distance" as US105
  usecase "US108: Develop the data model required to support all the functionality and to fulfill the purpose of the system to develop. This data model is to be designed following a systematic data modeling methodology" as US108
  usecase "US109: Draft an SQL script to test whether the database verifies all the data integrity restrictions that are required to fulfil the purpose of the system and the business constraints of the UoD" as US109
  usecase "US110: Define the naming conventions to apply when defining identifiers or writing SQL or PL/SQL code. The naming conventions may evolve as new database and programming objects are known. The naming conventions guide should be organized in a way to facilitate its maintenance" as US110
  usecase "US111: Create a SQL script to load the database with a minimum set of data sufficient to carry out data integrity verification and functional testing. This script shall produce a bootstrap report providing the number of tuples/rows in each relation/table" as US111

tm --> US101
tm --> US102
tm --> US103
tm --> US104
tm --> US105

pm --> US108
pm --> US109
pm --> US110
pm --> US111
@enduml