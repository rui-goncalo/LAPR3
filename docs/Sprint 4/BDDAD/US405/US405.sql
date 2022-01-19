SET SERVEROUTPUT ON;
DROP FUNCTION func_occupancy_rate_period;
CREATE OR REPLACE FUNCTION func_occupancy_rate_period(f_ship Ship.mmsi%type, f_date_init trip.initial_date%type , f_date_final Arrival.arrival_date%type)
RETURN VARCHAR
IS
    v_ratio NUMBER;
    v_str VARCHAR(100);
    v_day INTEGER;
    v_month INTEGER;
    v_year INTEGER;
    v_dayf INTEGER;
    v_monthf INTEGER;
    v_yearf INTEGER;
BEGIN
    v_day := EXTRACT(DAY FROM f_date_init);
    v_month := EXTRACT(MONTH FROM f_date_init);
    v_year := EXTRACT(YEAR FROM f_date_init);
    
    v_dayf := EXTRACT(DAY FROM f_date_final);
    v_monthf := EXTRACT(MONTH FROM f_date_final);
    v_yearf := EXTRACT(YEAR FROM f_date_final);
    
    SELECT AVG(((SELECT COUNT(*) AS "Containers Per Manifest" FROM Container_Cargo_Manifest ccm, Cargo_Manifest cm, Vehicle v, Ship s
            WHERE ccm.cargo_manifestid = cm.id
            AND cm.vehicleid = v.id
            AND v.id = s.vehicleid
            AND s.mmsi = f_ship)/s.capacity)*100) AS "Occupancy_Rate"
    INTO v_ratio
    FROM Container_Cargo_Manifest ccm
    INNER JOIN Cargo_Manifest cm ON ccm.cargo_manifestid = cm.id
    INNER JOIN Vehicle v ON v.id = cm.vehicleid
    INNER JOIN Ship s ON s.vehicleid = v.id
    INNER JOIN Arrival a ON a.id = cm.arrivalid
    INNER JOIN Trip t ON t.id = a.tripid
    INNER JOIN Location l ON a.initiallocationid = l.id
    INNER JOIN Employee_Location el ON l.id = el.locationid
    INNER JOIN Employee e ON el.employeeid = e.id
    INNER JOIN Type_Employee te ON e.type_employeetype_id = te.type_id
    AND te.type_id = 2
    AND s.mmsi = f_ship
    AND v_year >= EXTRACT(YEAR FROM f_date_init)
    AND v_month >= EXTRACT(MONTH FROM f_date_init)
    AND v_day >= EXTRACT(DAY FROM f_date_init)
    AND v_yearf <= EXTRACT(YEAR FROM f_date_final)
    AND v_monthf <= EXTRACT(MONTH FROM f_date_final)
    AND v_dayf <= EXTRACT(DAY FROM f_date_final);
    
    v_str := 'Ratio: ' || v_ratio || '. INITIAL DATE: ' || f_date_init || '. FINAL DATE: ' || f_date_final || '.';
    RETURN v_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_occupancy_rate_period(211331678, '29-DEC-2020', '29-DEC-2020'));
END;
BEGIN
DBMS_OUTPUT.PUT_LINE(func_occupancy_rate_period(211331610, '10-APR-2021', '15-APR-2021'));
END;