CREATE OR REPLACE PROCEDURE prc_container_my_goods(
    p_container Container.id%type)
IS
    CURSOR c_container(p_container_id container.id%type)
    IS
        SELECT id
        FROM Container
        WHERE id = p_container_id;
        
    CURSOR c_client(p_container_id container.id%type)
    IS
        SELECT type_containerid
        FROM Container
        WHERE id = p_container_id
        AND type_containerid = 1;
        
    r_container c_container%rowtype;
    r_client    c_client%rowtype;
    
    ex_container_id_not_found   exception;
    ex_client_not_leasing       exception;

BEGIN
    
    OPEN c_container(p_container);
    FETCH c_container INTO r_container;
    IF c_container%notfound THEN
        raise ex_container_id_not_found;
    END IF;
    CLOSE c_container;
    
    OPEN c_client(p_container);
    FETCH c_client INTO r_client;
    IF c_client%notfound THEN
        raise ex_client_not_leasing;
    END IF;
    CLOSE c_client;

EXCEPTION
    WHEN ex_container_id_not_found THEN
        raise_application_error(-20010, 'Invalid Container ID');
    WHEN ex_client_not_leasing THEN
        raise_application_error(-20011, 'Container is not leased by client.');
        
END;
 
--Testing
SET SERVEROUTPUT ON;
--Deverá dar "Invalid Container ID" uma vez que não existe este container.
exec prc_container_my_goods(23);
--Deverá dar "Container is not leased by client" uma vez que o tipo de container é Bought.
exec prc_container_my_goods(20);