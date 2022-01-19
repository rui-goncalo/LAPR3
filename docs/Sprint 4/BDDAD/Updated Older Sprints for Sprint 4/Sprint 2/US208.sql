SET SERVEROUTPUT ON;
DROP FUNCTION func_ratio;
CREATE OR REPLACE FUNCTION func_ratio(f_cargom container_cargo_manifest.Cargo_ManifestId%type, f_ship Ship.mmsi%type)
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
    FROM Ship s, Cargo_Manifest cm, Vehicle v
    WHERE s.mmsi = f_ship
    AND s.vehicleid = v.id
    AND v.id = cm.vehicleid
    AND cm.id = f_cargom;
        t_str := 'Ratio: ' || t_ratio || '. Ship: ' || t_ship || '.';
    RETURN t_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_ratio(10, 211331613));
END;