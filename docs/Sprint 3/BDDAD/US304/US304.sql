SET SERVEROUTPUT ON;
CREATE OR REPLACE FUNCTION func_audit_trail(f_container Container.id%type, f_cargo_manifest Cargo_Manifest.id%type)
RETURN VARCHAR
IS
    v_str VARCHAR(100);
    v_user VARCHAR(10);
    v_date VARCHAR(50);
    v_operation VARCHAR(10);
BEGIN
    SELECT at."user" AS "User", at.date_t AS "Date", at.operation
    INTO v_user, v_date, v_operation
    FROM Audittrail at
    INNER JOIN Cargo_Manifest cm ON at.id = cm.audittrailid
    INNER JOIN Container_Cargo_Manifest ccm ON cm.id = ccm.cargo_manifestid
    INNER JOIN Container c ON c.id = ccm.containerid
    AND c.id = f_container
    AND cm.id = f_cargo_manifest
    ORDER BY at.date_t;
    
    v_str := 'User: ' || v_user || '. Date: ' || v_date || '. Operation: ' || v_operation || '.';
    RETURN v_str;
END;

BEGIN
DBMS_OUTPUT.PUT_LINE(func_audit_trail(13, 8));
END;