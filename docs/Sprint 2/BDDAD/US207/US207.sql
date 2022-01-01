SET SERVEROUTPUT ON;
DROP FUNCTION func_avg_cm_container;
CREATE OR REPLACE FUNCTION func_avg_cm_container(f_avg_c Cargo_Manifest.year%type)
RETURN VARCHAR
IS
    t_str VARCHAR(100);
    t_cm_year INTEGER;
    t_avg NUMBER;
BEGIN
    SELECT COUNT(*) into t_cm_year
    FROM cargo_manifest
    WHERE year = f_avg_c;
    SELECT AVG(COUNT(ccm.ContainerId_container)) AS "Containers Per Manifest" into t_avg
    FROM Container_Cargo_Manifest ccm
    INNER JOIN Cargo_Manifest cm ON cm.id = ccm.Cargo_ManifestId_cargo_manifest
    AND cm.year = f_avg_c
    GROUP BY ccm.Cargo_ManifestId_cargo_manifest;
        t_str := 'CM: ' || t_cm_year || '. AVG: ' || t_avg || '.';
    RETURN t_str;
END;
BEGIN
DBMS_OUTPUT.PUT_LINE(func_avg_cm_container(2021));
END;