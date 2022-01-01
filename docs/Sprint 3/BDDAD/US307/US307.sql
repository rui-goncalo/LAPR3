CREATE OR REPLACE TRIGGER trg_warehouse_capacity_insufficient
BEFORE INSERT OR UPDATE ON Container_Cargo_Manifest
FOR EACH ROW
DECLARE
    v_type_location INTEGER;
    v_containers INTEGER;
    v_capacity INTEGER;
    ex_exceeded_capacity EXCEPTION;
    ex_not_warehouse EXCEPTION;
BEGIN
    --Verifica se é uma warehouse
    SELECT l.type_locationid INTO v_type_location
    FROM Location l, Cargo_Manifest cm, Arrival a, Type_location tl
    WHERE cm.arrivalid = a.id
    AND a.initiallocationid = l.id
    AND l.type_locationid = tl.id
    AND cm.id = :new.cargo_manifestid;

    IF (v_type_location = 2) THEN
        --Número de containers per manifest
        SELECT COUNT(*) AS "Containers per Manifest" INTO v_containers
        FROM container_cargo_manifest ccm
        INNER JOIN cargo_manifest cm ON ccm.cargo_manifestid = cm.id
        INNER JOIN Vehicle v ON cm.vehicleid = v.id
        INNER JOIN Ship s ON v.id = s.vehicleid
        INNER JOIN Container c ON c.id = ccm.containerid
        AND cm.id = :new.cargo_manifestid;
        
        --Capacidade do Warehouse
        SELECT l.capacity INTO v_capacity
        FROM Location l
        INNER JOIN Arrival a ON l.id = a.initiallocationid
        INNER JOIN Cargo_Manifest cm ON a.id = cm.arrivalid
        INNER JOIN Type_Location tl ON l.type_locationid = tl.id
        AND tl.id = 2
        AND cm.id = :new.cargo_manifestid;
        
        IF (v_capacity - v_containers) <= 0 THEN
            RAISE ex_exceeded_capacity;
        END IF;
    END IF;
    
EXCEPTION
    WHEN ex_exceeded_capacity THEN
        raise_application_error(-20000, 'Exceeds the warehouse available capacity');
        
END trg_warehouse_capacity_insufficient;

DROP TRIGGER trg_warehouse_capacity_insufficient;


--Testing
--Introduzir warehouse com capacidade para 1 container
INSERT INTO location VALUES (13, 'Cardiff', 1, 35.91612367, 31.55, 2, 1);
--Criar a rota para fazer as verificações
INSERT INTO trip VALUES (12, TO_DATE('07-01-2022 06:35', 'DD-MM-YYYY HH24-MI'), TO_DATE('09-01-2022 23:30', 'DD-MM-YYYY HH24-MI'), 10, 4, 211331613);
--Introduzir cargo_manifest com 1 container para aquela location com capacity = 1
INSERT INTO arrival VALUES (20, 12, TO_DATE('09-01-2022 23:30', 'DD-MM-YYYY HH24-MI'), 13, 4);

INSERT INTO container VALUES (26, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO cargo_manifest VALUES (24, 5.3, 2022, 2, 10, 20, null);
INSERT INTO container_cargo_manifest VALUES (26, 24, 2);

--Esta linha já dá erro devido à capacidade do warehouse ser 0 neste momento
INSERT INTO container VALUES (27, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container_cargo_manifest VALUES (27, 24, 2);