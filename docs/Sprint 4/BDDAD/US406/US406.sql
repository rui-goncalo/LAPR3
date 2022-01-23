SET SERVEROUTPUT ON;
DROP PROCEDURE prc_voyages_occupation_rate;
CREATE OR REPLACE PROCEDURE prc_voyages_occupation_rate(f_ship Ship.mmsi%type)
IS
    v_threshold INTEGER;
    
BEGIN
    v_threshold := 66;
    
    FOR rec IN (SELECT DISTINCT t.id, l.name, t.initial_date, a.arrival_date
                FROM Trip t, Vehicle v, Ship s, Arrival a, Location l, Employee_Location el, Employee e, Type_Employee te, Cargo_Manifest cm
                WHERE ((SELECT COUNT(*) AS "Containers Per Manifest"
                        FROM Container_Cargo_Manifest ccm, Cargo_Manifest cm, Vehicle v, Ship s
                        WHERE ccm.cargo_manifestid = cm.id
                        AND cm.vehicleid = v.id
                        AND v.id = s.vehicleid
                        AND s.mmsi = f_ship)/s.capacity*100) < v_threshold
                AND t.vehicleid = v.id
                AND v.id = s.vehicleid
                AND a.tripid = t.id
                AND l.id = t.destinationlocationid
                AND cm.arrivalid = a.id
                AND cm.type_cargo_manifestid = 2
                AND s.mmsi = f_ship
                AND l.id = el.locationid
                AND el.employeeid = e.id
                AND e.type_employeetype_id = te.type_id
                AND te.type_id = 2)
    LOOP
        DBMS_OUTPUT.PUT_LINE('Trip ID: ' || rec.id || '. Location: ' || rec.name || '. Initial date: ' || rec.initial_date || '. Final date: ' || rec.arrival_date || '.');
    END LOOP;
END;

exec prc_voyages_occupation_rate(211331613);

--Para demonstração usar o Ship 211331613 que tem uma occupancy rate de 1.14
--Alterar o valor do threshold por 1.14 e 1.15 para ver que só aparecem os que têm occupancy rate abaixo do pedido.