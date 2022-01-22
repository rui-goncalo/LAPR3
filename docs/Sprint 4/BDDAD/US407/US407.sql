
DROP PROCEDURE prc_week_in_advance;
CREATE OR REPLACE PROCEDURE prc_week_in_advance
    IS
    v_current_manifest_count INT;
BEGIN

    FOR rec IN (SELECT S.MMSI, CM.ID, TCM.DESIGNATION, A.ARRIVAL_DATE, L.NAME
                FROM SHIP S
                         INNER JOIN VEHICLE V ON V.ID = S.VEHICLEID
                         INNER JOIN CARGO_MANIFEST CM on S.vehicleId = CM.VEHICLEID
                         INNER JOIN ARRIVAL A ON CM.ARRIVALID = A.ID
                         INNER JOIN Type_Cargo_Manifest TCM ON TCM.ID = CM.TYPE_CARGO_MANIFESTID
                         INNER JOIN LOCATION L ON L.ID = A.INITIALLOCATIONID
                WHERE A.ARRIVAL_DATE BETWEEN NEXT_DAY(SYSDATE, 'MONDAY') AND NEXT_DAY(NEXT_DAY(SYSDATE, 'MONDAY'), 'SUNDAY'))
        LOOP
            SELECT COUNT(*) INTO v_current_manifest_count FROM Container_Cargo_Manifest WHERE Cargo_ManifestId = rec.id;
            DBMS_OUTPUT.PUT_LINE('Vehicle:  ' || rec.mmsi || '  || Cargo Manifest ID:  ' || rec.id || '  || Type Cargo Manifest:  ' || rec.designation || '  || Arrival Date:  ' || rec.arrival_date || '  || Actual Location:  ' || rec.name || '  || Containers per Manifest:  ' || v_current_manifest_count || ' ');
        END LOOP;
END;
commit;

begin
prc_week_in_advance();
end;