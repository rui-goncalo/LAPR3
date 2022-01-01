CREATE OR REPLACE TRIGGER trg_not_exceed_ship_capacity
BEFORE INSERT OR UPDATE ON Container_Cargo_Manifest
FOR EACH ROW
DECLARE
    v_vehicle INTEGER;
    v_containers INTEGER;
    v_capacity INTEGER;
    ex_exceeded_capacity EXCEPTION;
BEGIN
    --Vai buscar o id do Vehicle
    SELECT v.id INTO v_vehicle
    FROM Vehicle v
    INNER JOIN Ship s ON s.vehicleid = v.id
    INNER JOIN Cargo_Manifest cm ON v.id = cm.vehicleid
    AND cm.id = :new.cargo_manifestid;

    --Faz a contagem dos containers por manifest
    SELECT COUNT(*) AS "Containers per Manifest" INTO v_containers
    FROM container_cargo_manifest ccm
    INNER JOIN cargo_manifest cm ON ccm.cargo_manifestid = cm.id
    INNER JOIN Vehicle v ON cm.vehicleid = v.id
    INNER JOIN Ship s ON v.id = s.vehicleid
    INNER JOIN Container c ON c.id = ccm.containerid
    AND cm.id = :new.cargo_manifestid;
    
    --Calcula a capacidade restante do vehicle
    SELECT s.capacity INTO v_capacity
    FROM Ship s
    INNER JOIN Vehicle v ON s.vehicleid = v.id
    INNER JOIN Cargo_Manifest cm ON cm.vehicleid = v.id
    AND cm.id = :new.cargo_manifestid;

    IF (v_capacity - v_containers) <= 0 THEN
        RAISE ex_exceeded_capacity;
    END IF;
    
EXCEPTION
    WHEN ex_exceeded_capacity THEN
        raise_application_error(-20000, 'Number of containers in the manifest exceeds the ship available capacity.');
        
END trg_not_exceed_ship_capacity;

DROP TRIGGER trg_not_exceed_ship_capacity;


--Testing
INSERT INTO vehicle VALUES (25);
INSERT INTO ship VALUES (111111111, 'SEOUlEXPRE', 1111111, 21, 12.3, 'AAAA', 70, 294.5, 32.6, 2, 13.6, 'B', 1254, 25);
INSERT INTO cargo_manifest VALUES (23, 5.3, 2021, 1, 25, null, null);
INSERT INTO container VALUES (23, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container_cargo_manifest VALUES (23, 23, 1);

--Adiciona-se outro container ao mesmo cargo_manifest porque a capacidade de Ship é 2
INSERT INTO container VALUES (24, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container_cargo_manifest VALUES (24, 23, 2);

--Esta linha dá erro porque já se está a tentar inserir um 3º container num Ship com capacidade para 2 que já está cheio
INSERT INTO container VALUES (25, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container_cargo_manifest VALUES (25, 23, 1);