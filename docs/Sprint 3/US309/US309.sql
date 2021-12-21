CREATE OR REPLACE TRIGGER trg_ship_on_trip
BEFORE INSERT OR UPDATE ON Cargo_Manifest
FOR EACH ROW
DECLARE
    v_ship_on_trip INTEGER;
    ex_ship_on_trip EXCEPTION;
BEGIN
    SELECT COUNT(*) AS "Active Trips" INTO v_ship_on_trip
    FROM TRIP T INNER JOIN SHIP S ON T.SHIPMMSI = S.MMSI INNER JOIN CARGO_MANIFEST CM ON CM.SHIPMMSI = S.MMSI
    WHERE S.MMSI = :new.shipmmsi AND SYSDATE BETWEEN T.INITIAL_DATE AND T.FINAL_DATE;

    IF v_ship_on_trip > 0 THEN
        RAISE ex_ship_on_trip;
    END IF;

    EXCEPTION
        WHEN ex_ship_on_trip THEN
            raise_application_error(-2000, 'Ship is on a trip');

END trg_ship_on_trip;