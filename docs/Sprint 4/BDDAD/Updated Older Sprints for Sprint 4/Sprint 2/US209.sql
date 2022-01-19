SET SERVEROUTPUT ON;
DROP FUNCTION func_ratio_moment;
CREATE OR REPLACE FUNCTION func_ratio_moment(f_cargom container_cargo_manifest.Cargo_ManifestId%type, f_ship Ship.mmsi%type)
RETURN VARCHAR
IS
    t_str VARCHAR(100);
    t_ratio NUMBER;
    t_ship INTEGER;
BEGIN
    SELECT ((SELECT COUNT(*) AS "Containers per Manifest"
            FROM container_cargo_manifest
            WHERE Cargo_ManifestId = f_cargom)/s.capacity)*100 AS "Ratio", s.mmsi as "Ship"
    INTO t_ratio, t_ship
    FROM Ship s, Cargo_Manifest cm, Arrival a, Trip t, Vehicle v
    WHERE s.mmsi = f_ship
    AND s.vehicleid = v.id
    AND v.id = cm.vehicleid
    AND cm.id = f_cargom
    AND a.id = cm.arrivalid
    AND a.tripid = t.id
    AND SYSDATE BETWEEN t.initial_date AND t.final_date;
        t_str := 'Ratio: ' || t_ratio || '. Ship: ' || t_ship || '.';
    RETURN t_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_ratio(10, 211331613));
END;