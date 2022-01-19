SET SERVEROUTPUT ON;
DROP FUNCTION func_map_occupation;
CREATE OR REPLACE FUNCTION func_map_occupation(f_date Arrival.arrival_date%type)
RETURN VARCHAR
IS
    v_month VARCHAR(15);
    v_year NUMBER;
    v_str VARCHAR(100);
    v_name VARCHAR(15);
    v_occupation INTEGER;
    v_capacity INTEGER;
BEGIN
    v_month := EXTRACT(MONTH FROM f_date);
    v_year := EXTRACT(YEAR FROM f_date);
    
    SELECT COUNT(*) AS "Container in Port", l.name, l.capacity
    INTO v_occupation, v_name, v_capacity
    FROM Container c, Container_Cargo_Manifest ccm, Cargo_Manifest cm, Arrival a, Location l
    WHERE c.id = ccm.containerid
    AND ccm.cargo_manifestid = cm.id
    AND cm.arrivalid = a.id
    AND a.initiallocationid = l.id
    AND l.type_locationid = 1
    AND EXTRACT(MONTH FROM a.arrival_date) = v_month
    AND EXTRACT(YEAR FROM a.arrival_date) = v_year
    GROUP BY l.id, l.name, l.capacity;
    
    v_str := 'Capacity: ' || v_capacity || '. Occupation: ' || v_occupation || '. Port name: ' || v_name || '.';
    RETURN v_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_map_occupation('01-SEP-2020'));
END;