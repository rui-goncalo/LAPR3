SELECT s.mmsi AS "Available Ships", t.final_date AS "Finished Trip"
FROM Ship s
INNER JOIN Vehicle v ON v.id = s.vehicleid
INNER JOIN Cargo_Manifest cm ON v.id = cm.vehicleid
INNER JOIN Type_Cargo_Manifest tcm ON cm.type_cargo_manifestId = tcm.id
INNER JOIN Arrival a ON a.id = cm.arrivalid
INNER JOIN Trip t ON t.id = a.tripId
AND tcm.id = 2
AND t.final_date < TRUNC(sysdate, 'DAY')+8;