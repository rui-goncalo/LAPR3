-- US404
CREATE OR REPLACE FUNCTION fnc_iddle_ship(p_year INTEGER) return varchar
    IS
    v_days_idle INT;
    v_ship_mmsi INT;
    v_str VARCHAR(1000);
    v_const DATE := TO_DATE(p_year || '-01-01', 'YYYY-MM-DD');
BEGIN
    v_str := v_str || 'Year: ' || p_year || CHR(10);
    FOR i IN (SELECT DISTINCT VehicleId FROM Trip) LOOP
            SELECT mmsi INTO v_ship_mmsi FROM SHIP WHERE VEHICLEID = i.VEHICLEID;
            v_days_idle := SYSDATE - v_const;
            FOR j IN (SELECT initial_date, final_date FROM TRIP WHERE VEHICLEID = i.VEHICLEID) LOOP
                IF j.final_date >= v_const  AND j.initial_date <= SYSDATE THEN
                    IF j.initial_date < v_const THEN
                        IF j.final_date > SYSDATE THEN
                            v_days_idle := v_days_idle - (SYSDATE - v_const);
                        ELSE
                            v_days_idle := v_days_idle - (j.final_date - v_const + 1);
                        END IF;
                    ELSE
                        IF j.final_date > SYSDATE THEN
                            v_days_idle := v_days_idle - (SYSDATE - j.initial_date);
                        ELSE
                            v_days_idle := v_days_idle - (j.final_date - j.initial_date + 1);
                        END IF;
                    END IF;
                end if;
            END LOOP;
            v_str := v_str || 'Vehicle ID: ' || i.VEHICLEID || ' | ShipMMSI: ' || v_ship_mmsi || ' | Days Idle: ' || v_days_idle || CHR(10);
            END LOOP;
    return v_str;
END;
commit;

BEGIN
DBMS_OUTPUT.PUT_LINE(fnc_iddle_ship(2022));
end;