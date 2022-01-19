--Nº containers em cada warehouse
SELECT COUNT(*) AS "Containers in Warehouse"
FROM Container c, Container_Cargo_Manifest ccm, Cargo_Manifest cm, Arrival a, Location l
WHERE c.id = ccm.containerid
AND ccm.cargo_manifestid = cm.id
AND cm.arrivalid = a.id
AND a.initiallocationid = l.id
AND l.type_locationid = 2
GROUP BY l.id;

--Nº containers por warehouse que partem nos proximos 30 dias
SELECT COUNT(*) AS "Containers in Warehouse", l.name, t.final_date
FROM Container c, Container_Cargo_Manifest ccm, Cargo_Manifest cm, Arrival a, Location l, Type_cargo_manifest tcm, Trip t
WHERE c.id = ccm.containerid
AND ccm.cargo_manifestid = cm.id
AND cm.type_cargo_manifestid = tcm.id
AND cm.arrivalid = a.id
AND a.initiallocationid = l.id
AND l.type_locationid = 2
AND a.tripid = t.id
AND t.final_date BETWEEN SYSDATE AND TRUNC(sysdate, 'DAY')+31
GROUP BY l.id, l.name, t.final_date;