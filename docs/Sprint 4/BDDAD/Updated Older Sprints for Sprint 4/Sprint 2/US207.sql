SET SERVEROUTPUT ON;
DROP FUNCTION func_avg_cm_container;
CREATE OR REPLACE FUNCTION func_avg_cm_container(f_avg_c Cargo_Manifest.date_t%type)
RETURN VARCHAR
IS
    t_str VARCHAR(100);
    t_cm_year INTEGER;
    v_year INTEGER;
    t_avg NUMBER;
BEGIN
    v_year := EXTRACT(YEAR FROM f_avg_c);

    SELECT COUNT(*) into t_cm_year
    FROM cargo_manifest
    WHERE EXTRACT(YEAR FROM date_t) = v_year;
    SELECT AVG(COUNT(ccm.ContainerId)) AS "Containers Per Manifest" into t_avg
    FROM Container_Cargo_Manifest ccm
    INNER JOIN Cargo_Manifest cm ON cm.id = ccm.Cargo_ManifestId
    AND EXTRACT(YEAR FROM date_t) = v_year
    GROUP BY ccm.Cargo_ManifestId;
        t_str := 'CM: ' || t_cm_year || '. AVG: ' || t_avg || '.';
    RETURN t_str;
END;
BEGIN
DBMS_OUTPUT.PUT_LINE(func_avg_cm_container('01-JAN-2021'));
END;