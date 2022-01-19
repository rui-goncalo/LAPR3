SET SERVEROUTPUT ON;
DROP FUNCTION func_check_container;
CREATE OR REPLACE FUNCTION func_check_container(f_container IN INTEGER, f_client IN INTEGER)
    RETURN VARCHAR
    IS
    v_return VARCHAR(200);
    v_initial_date TIMESTAMP;
    v_final_date TIMESTAMP;
    v_mmsi INTEGER;
    v_initialid INTEGER;
    v_finalid INTEGER;
    v_found INTEGER;
BEGIN
    SELECT COUNT(*) AS "Found"
    INTO v_found
    FROM Arrival A
             INNER JOIN Trip T ON T.id = A.TRIPID
             INNER JOIN Cargo_Manifest CM ON CM.arrivalid = A.id
             INNER JOIN Container_Cargo_Manifest CCM ON CCM.Cargo_ManifestId = CM.id
             INNER JOIN Container C ON C.id = CCM.ContainerId
             INNER JOIN Container_Client CC ON CC.ContainerId = C.id
             INNER JOIN client Cl ON Cl.id = CC.ClientId
    WHERE C.id = f_container AND Cl.id = f_client AND CM.id = CCM.Cargo_ManifestId;
    IF v_found < 1 THEN
        raise_application_error(-20000, 'Container does not match client id.');
    ELSE
        SELECT COUNT(*) AS "Found", T.INITIAL_DATE, A.ARRIVAL_DATE, S.MMSI, T.InitialLocationId, A.DestinationLocationId
        INTO v_found, v_initial_date, v_final_date, v_mmsi, v_initialid, v_finalid
        FROM Arrival A
                 INNER JOIN Trip T ON T.id = A.TRIPID
                 INNER JOIN Vehicle v ON v.id = t.vehicleid
                 INNER JOIN Ship s ON s.vehicleid = v.id
                 INNER JOIN Cargo_Manifest CM ON CM.arrivalid = A.id
                 INNER JOIN Container_Cargo_Manifest CCM ON CCM.Cargo_ManifestId = CM.id
                 INNER JOIN Container C ON C.id = CCM.ContainerId
                 INNER JOIN Container_Client CC ON CC.ContainerId = C.id
                 INNER JOIN client Cl ON Cl.id = CC.ClientId
        WHERE C.id = f_container AND Cl.id = f_client AND CM.id = CCM.Cargo_ManifestId
        GROUP BY T.INITIAL_DATE, A.ARRIVAL_DATE, S.MMSI, T.InitialLocationId, A.DestinationLocationId;
        v_return := 'Initial Date: ' || v_initial_date || '. Final Date: ' || v_final_date ||'. MMSI: ' || v_mmsi || '. Initial ID: ' || v_initialid || '. Final ID: ' || v_finalid || '.';
    END IF;
    RETURN v_return;
END;

BEGIN
    DBMS_OUTPUT.PUT_LINE(func_check_container(16, 3));
END;

--305 select 2º
SELECT A.DestinationLocationId, A.arrival_date, L.longitude, L.latitude, L.NAME AS PORT, C.NAME AS COUNTRY
FROM Arrival A
INNER JOIN LOCATION L ON A.DESTINATIONLOCATIONID = L.ID
INNER JOIN Country C ON c.id = A.DestinationLocationId
INNER JOIN Trip T ON T.id = A.TRIPID
INNER JOIN Vehicle v ON v.id = t.vehicleid
INNER JOIN Ship s ON s.vehicleid = v.id
WHERE S.mmsi = 211331678 AND A.ARRIVAL_DATE BETWEEN TO_DATE('01-12-2020 09:00', 'DD-MM-YYYY HH24-MI') AND TO_DATE('30-12-2021 12:00', 'DD-MM-YYYY HH24-MI');