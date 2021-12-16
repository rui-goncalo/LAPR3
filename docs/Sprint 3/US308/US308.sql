CREATE OR REPLACE TRIGGER trg_not_exceed_ship_capacity
BEFORE INSERT OR UPDATE ON Cargo_Manifest
FOR EACH ROW
DECLARE
    v_containers INTEGER;
    v_capacity INTEGER;
    ex_exceeded_capacity EXCEPTION;
BEGIN
    SELECT COUNT(*) AS "Containers per Manifest" INTO v_containers
    FROM container_cargo_manifest ccm
    INNER JOIN cargo_manifest cm ON ccm.cargo_manifestid = cm.id
    INNER JOIN Ship s ON cm.shipmmsi = s.mmsi
    AND s.mmsi = :new.shipmmsi;
    
    SELECT capacity INTO v_capacity
    FROM Ship
    WHERE mmsi = :new.shipmmsi;

    IF (v_capacity - v_containers) <= 0 THEN
        RAISE ex_exceeded_capacity;
    END IF;
    
EXCEPTION
    WHEN ex_exceeded_capacity THEN
        raise_application_error(-20000, 'Number of container in the manifest exceeds the ship available capacity');
        
END trg_not_exceed_ship_capacity;

DROP TRIGGER trg_not_exceed_ship_capacity;

--Testing
--Introduzir Ship com capacidade para 1 container
INSERT INTO ship VALUES (111111111, 'SEOUlEXPRE', 1111111, 21, 12.3, 'AAAA', 70, 294.5, 32.6, 1, 13.6, 'B', 1254);
--Atribuir cargo manifest ao Ship
INSERT INTO cargo_manifest VALUES (23, 5.3, 2021, 1, 111111111);
INSERT INTO container VALUES (23, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container_cargo_manifest VALUES (23, 23, 1);
--Esta linha deverá dar erro
INSERT INTO cargo_manifest VALUES (24, 5.3, 2021, 1, 111111111); --Este já deve dar erro

--Para apagar as linhas criadas
DELETE FROM container_cargo_manifest WHERE containerid = 23;
DELETE FROM container WHERE id = 23;
DELETE FROM cargo_manifest WHERE id = 23;
DELETE FROM ship WHERE mmsi = 111111111;