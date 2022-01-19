SET SERVEROUTPUT ON;
DROP FUNCTION func_client_container;
CREATE OR REPLACE FUNCTION func_client_container(f_container Container.id%type)
RETURN VARCHAR
IS
    t_str VARCHAR(100);
    t_containerId INTEGER;
    t_type_container INTEGER;
    t_location VARCHAR(10);
BEGIN
    SELECT c.id AS "Container ID", cr.id AS "Type Container", tl.description AS "Current Location"
    INTO t_containerId, t_type_container, t_location
    FROM Container c, Container_Refrigerated cr, container_cargo_manifest ccm, Cargo_Manifest cm, Type_Cargo_Manifest tcm, Arrival a, Location l, type_location tl
    WHERE c.id = f_container
    AND c.container_refrigeratedid = cr.id
    AND c.id = ccm.ContainerId
    AND ccm.Cargo_ManifestId = cm.id
    AND tcm.id = cm.Type_Cargo_ManifestId
    AND cm.arrivalid = a.id
    AND a.initiallocationid = l.id
    AND l.type_locationid = tl.id;
        t_str := 'Container ID: ' || t_containerId || '. Type Container: ' || t_type_container || '. Current Location: ' || t_location || '.';
    RETURN t_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_client_container(5));
END;