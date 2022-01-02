--INSERT VEHICLE--
INSERT INTO vehicle VALUES (1);
INSERT INTO vehicle VALUES (2);
INSERT INTO vehicle VALUES (3);
INSERT INTO vehicle VALUES (4);
INSERT INTO vehicle VALUES (5);
INSERT INTO vehicle VALUES (6);
INSERT INTO vehicle VALUES (7);
INSERT INTO vehicle VALUES (8);
INSERT INTO vehicle VALUES (9);
INSERT INTO vehicle VALUES (10);
INSERT INTO vehicle VALUES (11);
INSERT INTO vehicle VALUES (12);
INSERT INTO vehicle VALUES (13);
INSERT INTO vehicle VALUES (14);
INSERT INTO vehicle VALUES (15);
INSERT INTO vehicle VALUES (16);
INSERT INTO vehicle VALUES (17);
INSERT INTO vehicle VALUES (18);
INSERT INTO vehicle VALUES (19);
INSERT INTO vehicle VALUES (20);
INSERT INTO vehicle VALUES (21);
INSERT INTO vehicle VALUES (22);
INSERT INTO vehicle VALUES (23);
INSERT INTO vehicle VALUES (24);

--INSERT SHIP--
INSERT INTO ship VALUES (211331620, 'SEOUlEXPRE', 9193305, 21, 12.3, 'DHBN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 1);
INSERT INTO ship VALUES (211331624, 'SEOUlEXPRE', 9193321, 21, 12.3, 'DHKL', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 2);
INSERT INTO ship VALUES (211331635, 'SEOUlEXPRE', 9193343, 21, 12.3, 'DHQW', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 3);
INSERT INTO ship VALUES (211331643, 'SEOUlEXPRE', 9193334, 21, 12.3, 'DHER', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 4);
INSERT INTO ship VALUES (211331678, 'SEOUlEXPRE', 9193323, 21, 12.3, 'DHTY', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 5);
INSERT INTO ship VALUES (211331696, 'SEOUlEXPRE', 9193312, 21, 12.3, 'DHUI', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 6);
INSERT INTO ship VALUES (211331683, 'SEOUlEXPRE', 9193332, 21, 12.3, 'DHOP', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 7);
INSERT INTO ship VALUES (211331619, 'SEOUlEXPRE', 9193303, 21, 12.3, 'DHAS', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 8);
INSERT INTO ship VALUES (211331610, 'SEOUlEXPRE', 9193365, 21, 12.3, 'DHDF', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 9);
INSERT INTO ship VALUES (211331612, 'SEOUlEXPRE', 9193356, 21, 12.3, 'DHGH', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 10);
INSERT INTO ship VALUES (211331613, 'SEOUlEXPRE', 9193322, 21, 12.3, 'DHJK', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 11);
INSERT INTO ship VALUES (211311111, 'SEOUlEXPRE', 9193310, 21, 12.3, 'DHGG', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 12);
INSERT INTO ship VALUES (211311112, 'SEOUlEXPRE', 9193311, 21, 12.3, 'DHJJ', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13);
INSERT INTO ship VALUES (210950000, 'VARAMO', 9395044, 21, 12.3,'C4SQ2', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 14);

--INSERT CONTINENT--
INSERT INTO continent VALUES (1, 'Europe');
INSERT INTO continent VALUES (2, 'America');
INSERT INTO continent VALUES (3, 'Africa');

--INSERT COUNTRY--
INSERT INTO country VALUES (1, 'UK', 1);
INSERT INTO country VALUES (2, 'US', 2);
INSERT INTO country VALUES (3, 'Mexico', 2);
INSERT INTO country VALUES (4, 'Brasil', 2);
INSERT INTO country VALUES (5, 'Peru', 2);
INSERT INTO country VALUES (6, 'Morocco', 3);
INSERT INTO country VALUES (7, 'Angola', 3);
INSERT INTO country VALUES (8, 'Turkey', 1);
INSERT INTO country VALUES (9, 'Italy', 1);
INSERT INTO country VALUES (10, 'Canada', 2);

--INSERT TYPE_LOCATION--
INSERT INTO type_location VALUES (1, 'Port');
INSERT INTO type_location VALUES (2, 'Warehouse');
INSERT INTO type_location VALUES (3, 'Ship');
INSERT INTO type_location VALUES (4, 'Truck');

--INSERT LOCATION--
INSERT INTO location VALUES (1, 'Liverpool', 100, 34.91666667, 33.65, 1, 1);
INSERT INTO location VALUES (2, 'Los Angeles', 350, 34.91666667, 33.65, 2, 2);
INSERT INTO location VALUES (3, 'New Jersey', 210, 34.91666667, 33.65, 2, 3);
INSERT INTO location VALUES (4, 'Rio Grande', 120, 34.91666667, 33.65, 2, 4);
INSERT INTO location VALUES (5, 'Salvador', 15, 34.91666667, 33.65, 1, 5);
INSERT INTO location VALUES (6, 'São Paulo', 150, 34.91666667, 33.65, 2, 6);
INSERT INTO location VALUES (7, 'Halifax', 200, 34.91666667, 33.65, 1, 7);
INSERT INTO location VALUES (8, 'Vancouver', 150, 49.28333333,-123.1166667, 2, 8);
INSERT INTO location VALUES (9, 'San Vicente', 300, 44.65,-63.56666667, 1, 9);
INSERT INTO location VALUES (10, 'Pisa', 100, 34.91666667, 33.65, 1, 10);
INSERT INTO location VALUES (11, 'Ship', 200, 34.91666667, 33.65, 3, 7);
INSERT INTO location VALUES (12, 'Warehouse', 350, 34.91666667, 33.65, 3, 3);

--INSERT TYPE_EMPLOYEE--
INSERT INTO type_employee VALUES (1, 'Client');
INSERT INTO type_employee VALUES (2, 'Fleet Manager');
INSERT INTO type_employee VALUES (3, 'Traffic Manager');
INSERT INTO type_employee VALUES (4, 'Warehouse Staff');
INSERT INTO type_employee VALUES (5, 'Warehouse Manager');
INSERT INTO type_employee VALUES (6, 'Port Staff');
INSERT INTO type_employee VALUES (7, 'Port Manager');
INSERT INTO type_employee VALUES (8, 'Ship Captain');
INSERT INTO type_employee VALUES (9, 'Ship Chief Electrical Engineer');
INSERT INTO type_employee VALUES (10, 'Truck Driver');
INSERT INTO type_employee VALUES (11, 'Administrator');

--INSERT EMPLOYEE--
INSERT INTO employee VALUES (1, 'João', 8);
INSERT INTO employee VALUES (2, 'Rui', 9);
INSERT INTO employee VALUES (3, 'Tiago', 10);
INSERT INTO employee VALUES (4, 'Catarina', 7);
INSERT INTO employee VALUES (5, 'Maria', 5);
INSERT INTO employee VALUES (6, 'Vítor', 6);
INSERT INTO employee VALUES (7, 'Lúcia', 3);
INSERT INTO employee VALUES (8, 'Joana', 4);
INSERT INTO employee VALUES (9, 'David', 2);
INSERT INTO employee VALUES (10, 'Francisco', 1);
INSERT INTO employee VALUES (11, 'Rodrigo', 11);

--INSERT EMPLOYEE_LOCATION--
INSERT INTO employee_location VALUES (4, 1);
INSERT INTO employee_location VALUES (5, 2);
INSERT INTO employee_location VALUES (6, 1);
INSERT INTO employee_location VALUES (7, 1);
INSERT INTO employee_location VALUES (8, 2);
INSERT INTO employee_location VALUES (9, 1);

--INSERT TRUCK--
INSERT INTO truck VALUES ('Kotka', 3, 15);
INSERT INTO truck VALUES ('Kotk', 3, 16);
INSERT INTO truck VALUES ('Kot', 3, 17);
INSERT INTO truck VALUES ('Ko', 3, 18);
INSERT INTO truck VALUES ('K', 3, 19);
INSERT INTO truck VALUES ('O', 3, 20);
INSERT INTO truck VALUES ('OL', 3, 21);
INSERT INTO truck VALUES ('OLA', 3, 22);
INSERT INTO truck VALUES ('A', 3, 23);
INSERT INTO truck VALUES ('AL', 3, 24);

--INSERT TYPE_CARGO_MANIFEST--
INSERT INTO type_cargo_manifest VALUES (1, 'loading');
INSERT INTO type_cargo_manifest VALUES (2, 'unloading');

--INSERT AUDITTRAIL--
--Equivale ao CM -> 10
INSERT INTO audittrail VALUES (1, 'jgd', TO_DATE('04-12-2021 09:30', 'DD-MM-YYYY HH24-MI'), 'INSERT');
INSERT INTO audittrail VALUES (2, 'jgv', TO_DATE('15-04-2021 23:20', 'DD-MM-YYYY HH24-MI'), 'UPDATE'); 

--INSERT ARRIVAL--
INSERT INTO arrival VALUES (1, 10, TO_DATE('30-12-2021 12:00', 'DD-MM-YYYY HH24-MI'), 4, 1);
INSERT INTO arrival VALUES (2, 9, TO_DATE('15-09-2021 00:15', 'DD-MM-YYYY HH24-MI'), 3, 2);
INSERT INTO arrival VALUES (3, 8, TO_DATE('04-06-2021 10:00', 'DD-MM-YYYY HH24-MI'), 6, 3);
INSERT INTO arrival VALUES (4, 7, TO_DATE('15-04-2021 23:20', 'DD-MM-YYYY HH24-MI'), 8, 4);
INSERT INTO arrival VALUES (5, 6, TO_DATE('03-01-2021 13:00', 'DD-MM-YYYY HH24-MI'), 4, 5);
INSERT INTO arrival VALUES (6, 5, TO_DATE('29-12-2020 10:00', 'DD-MM-YYYY HH24-MI'), 4, 6);
INSERT INTO arrival VALUES (7, 4, TO_DATE('03-11-2020 23:45', 'DD-MM-YYYY HH24-MI'), 9, 7);
INSERT INTO arrival VALUES (8, 3, TO_DATE('03-09-2020 12:45', 'DD-MM-YYYY HH24-MI'), 7, 8);
INSERT INTO arrival VALUES (9, 2, TO_DATE('03-03-2020 23:00', 'DD-MM-YYYY HH24-MI'), 2, 3);
INSERT INTO arrival VALUES (10, 1, TO_DATE('03-01-2020 09:00', 'DD-MM-YYYY HH24-MI'), 11, 10);
INSERT INTO arrival VALUES (19, 11, TO_DATE('10-01-2022 23:50', 'DD-MM-YYYY HH24-MI'), 3, 9);
--INSERT INTO arrival VALUES (20, 2, TO_DATE('10-04-2020 23:50', 'DD-MM-YYYY HH24-MI'), 2, 4);
INSERT INTO arrival VALUES (21, 5, TO_DATE('30-12-2020 09:00', 'DD-MM-YYYY HH24-MI'), 5, 8);
INSERT INTO arrival VALUES (22, 5, TO_DATE('30-12-2020 09:00', 'DD-MM-YYYY HH24-MI'), 5, 7);

--INSERT CARGO_MANIFEST--
INSERT INTO cargo_manifest VALUES (1, 5.3, 2021, 1, 12, 10, null);
INSERT INTO cargo_manifest VALUES (2, 5.3, 2021, 2, 14, 9, null);
INSERT INTO cargo_manifest VALUES (3, 5.3, 2019, 2, 3, 8, null);
INSERT INTO cargo_manifest VALUES (4, 5.3, 2018, 1, 4, 7, null);
INSERT INTO cargo_manifest VALUES (5, 5.3, 2021, 1, 5, 6, null);
INSERT INTO cargo_manifest VALUES (6, 5.3, 2020, 1, 6, 5, null);
INSERT INTO cargo_manifest VALUES (7, 5.3, 2019, 2, 7, 3, null);
INSERT INTO cargo_manifest VALUES (8, 5.3, 2018, 2, 9, 4, 2);
INSERT INTO cargo_manifest VALUES (9, 5.3, 2017, 1, 10, 2, null);
INSERT INTO cargo_manifest VALUES (10, 5.3, 2018, 2, 11, 1, 1);
INSERT INTO cargo_manifest VALUES (11, 5.3, 2020, 1, 1, null, null);
INSERT INTO cargo_manifest VALUES (12, 5.3, 2020, 2, 14, null, null);
INSERT INTO cargo_manifest VALUES (13, 5.3, 2019, 2, 3, null, null);
INSERT INTO cargo_manifest VALUES (14, 5.3, 2018, 1, 4, null, null);
INSERT INTO cargo_manifest VALUES (15, 5.3, 2021, 1, 5, null, null);
INSERT INTO cargo_manifest VALUES (16, 5.3, 2020, 1, 6, null, null);
INSERT INTO cargo_manifest VALUES (17, 5.3, 2019, 2, 7, null, null);
INSERT INTO cargo_manifest VALUES (18, 5.3, 2018, 2, 9, null, null);
INSERT INTO cargo_manifest VALUES (19, 5.3, 2017, 1, 10, null, null);
INSERT INTO cargo_manifest VALUES (20, 5.3, 2021, 2, 11, 19, null);
--US RUI
INSERT INTO cargo_manifest VALUES (21, 5.3, 2021, 2, 14, null, null);
INSERT INTO cargo_manifest VALUES (22, 5.3, 2021, 2, 14, null, null);

--INSERT TRIP--
INSERT INTO trip VALUES (1, TO_DATE('01-01-2020 09:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('01-03-2020 23:45', 'DD-MM-YYYY HH24-MI'), 1, 10, 211331620);
INSERT INTO trip VALUES (2, TO_DATE('01-03-2020 23:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('31-05-2020 21:10', 'DD-MM-YYYY HH24-MI'), 2, 9, 210950000);
INSERT INTO trip VALUES (3, TO_DATE('01-09-2020 12:45', 'DD-MM-YYYY HH24-MI'), TO_DATE('01-10-2020 03:50', 'DD-MM-YYYY HH24-MI'), 3, 8, 211331635);
INSERT INTO trip VALUES (4, TO_DATE('01-11-2020 23:45', 'DD-MM-YYYY HH24-MI'), TO_DATE('01-12-2020 21:05', 'DD-MM-YYYY HH24-MI'), 4, 7, 211331643);
INSERT INTO trip VALUES (5, TO_DATE('29-12-2020 10:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('03-01-2021 12:00', 'DD-MM-YYYY HH24-MI'), 5, 6, 211331678);
INSERT INTO trip VALUES (6, TO_DATE('01-01-2021 13:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('01-02-2021 00:45', 'DD-MM-YYYY HH24-MI'), 6, 5, 211331696);
INSERT INTO trip VALUES (7, TO_DATE('15-04-2021 23:20', 'DD-MM-YYYY HH24-MI'), TO_DATE('03-06-2021 11:45', 'DD-MM-YYYY HH24-MI'), 7, 4, 211331610);
INSERT INTO trip VALUES (8, TO_DATE('02-06-2021 10:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('09-06-2021 21:30', 'DD-MM-YYYY HH24-MI'), 8, 3, 211331683);
INSERT INTO trip VALUES (9, TO_DATE('13-09-2021 00:15', 'DD-MM-YYYY HH24-MI'), TO_DATE('20-09-2021 23:45', 'DD-MM-YYYY HH24-MI'), 9, 2, 211331612);
INSERT INTO trip VALUES (10, TO_DATE('05-12-2021 12:10', 'DD-MM-YYYY HH24-MI'), TO_DATE('03-01-2022 11:40', 'DD-MM-YYYY HH24-MI'), 10, 1, 211331613);
INSERT INTO trip VALUES (11, TO_DATE('04-01-2022 09:00', 'DD-MM-YYYY HH24-MI'), TO_DATE('10-01-2022 11:40', 'DD-MM-YYYY HH24-MI'), 10, 1, 211331613);
--211331624

--INSERT MESSAGE--
INSERT INTO message VALUES (1, 12, 13, 355, 321.5, TO_DATE('01-01-2021 23:45', 'DD-MM-YYYY HH24-MI'), 1, 211331620);
INSERT INTO message VALUES (2, 12, 13, 355, 321.5, TO_DATE('31-03-2020 21:10', 'DD-MM-YYYY HH24-MI'), 5, 211331624);
INSERT INTO message VALUES (3, 12, 13, 355, 321.5, TO_DATE('24-04-2020 11:15', 'DD-MM-YYYY HH24-MI'), 7, 211331635);
INSERT INTO message VALUES (4, 12, 13, 355, 321.5, TO_DATE('02-06-2020 05:40', 'DD-MM-YYYY HH24-MI'), 9, 211331643);
INSERT INTO message VALUES (5, 12, 13, 355, 321.5, TO_DATE('13-02-2020 19:25', 'DD-MM-YYYY HH24-MI'), 10, 211331678);
INSERT INTO message VALUES (6, 12, 13, 355, 321.5, TO_DATE('20-09-2019 17:01', 'DD-MM-YYYY HH24-MI'), 1, 211331696);
INSERT INTO message VALUES (7, 12, 13, 355, 321.5, TO_DATE('15-01-2019 23:43', 'DD-MM-YYYY HH24-MI'), 5, 211331683);
INSERT INTO message VALUES (8, 12, 13, 355, 321.5, TO_DATE('10-01-2020 18:05', 'DD-MM-YYYY HH24-MI'), 7, 211331610);
INSERT INTO message VALUES (9, 12, 13, 355, 321.5, TO_DATE('11-09-2020 21:45', 'DD-MM-YYYY HH24-MI'), 9, 211331612);
INSERT INTO message VALUES (10, 12, 13, 355, 321.5, TO_DATE('21-07-2019 00:35', 'DD-MM-YYYY HH24-MI'), 10, 211331613);

--INSERT POS_CONTAINER--
INSERT INTO pos_container VALUES (1, 0, 0, 0);
INSERT INTO pos_container VALUES (2, 0, 0, 1);
INSERT INTO pos_container VALUES (3, 0, 0, 2);
INSERT INTO pos_container VALUES (4, 0, 0, 3);
INSERT INTO pos_container VALUES (5, 0, 0, 4);
INSERT INTO pos_container VALUES (6, 0, 0, 5);
INSERT INTO pos_container VALUES (7, 0, 0, 6);
INSERT INTO pos_container VALUES (8, 0, 0, 7);
INSERT INTO pos_container VALUES (9, 0, 0, 8);
INSERT INTO pos_container VALUES (10, 0, 0, 9);
INSERT INTO pos_container VALUES (11, 0, 1, 0);
INSERT INTO pos_container VALUES (12, 0, 1, 1);
INSERT INTO pos_container VALUES (13, 0, 1, 2);
INSERT INTO pos_container VALUES (14, 0, 1, 3);
INSERT INTO pos_container VALUES (15, 0, 1, 4);
INSERT INTO pos_container VALUES (16, 0, 1, 5);
INSERT INTO pos_container VALUES (17, 0, 1, 6);
INSERT INTO pos_container VALUES (18, 0, 1, 7);
INSERT INTO pos_container VALUES (19, 0, 1, 8);
INSERT INTO pos_container VALUES (20, 0, 1, 9);
INSERT INTO pos_container VALUES (21, 0, 2, 0);
INSERT INTO pos_container VALUES (22, 0, 2, 1);

--INSERT CONTAINER_REFRIGERATED--
INSERT INTO container_refrigerated VALUES (1, 7);
INSERT INTO container_refrigerated VALUES (2, -5);

--INSERT TYPE_CONTAINER--
INSERT INTO Type_Container VALUES (1, 'Leasing');
INSERT INTO Type_Container VALUES (2, 'Bought');

--INSERT CONTAINER--
INSERT INTO container VALUES (1, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container VALUES (2, 124.2, 198.2, 876.4, 8, 1, 1);
INSERT INTO container VALUES (3, 124.2, 198.2, 876.4, 7, 2, 2);
INSERT INTO container VALUES (4, 124.2, 198.2, 876.4, 6, 1, 1);
INSERT INTO container VALUES (5, 124.2, 198.2, 876.4, 5, 2, 2);
INSERT INTO container VALUES (6, 124.2, 198.2, 876.4, 4, 2, 1);
INSERT INTO container VALUES (7, 124.2, 198.2, 876.4, 3, 1, 2);
INSERT INTO container VALUES (8, 124.2, 198.2, 876.4, 2, 2, 2);
INSERT INTO container VALUES (9, 124.2, 198.2, 876.4, 1, 2, 1);
INSERT INTO container VALUES (10, 124.2, 198.2, 876.4, 0, 1, 1);
INSERT INTO container VALUES (11, 124.2, 198.2, 876.4, 9, 1, 1);
INSERT INTO container VALUES (12, 124.2, 198.2, 876.4, 8, 1, 1);
INSERT INTO container VALUES (13, 124.2, 198.2, 876.4, 7, 2, 2);
INSERT INTO container VALUES (14, 124.2, 198.2, 876.4, 6, 1, 2);
INSERT INTO container VALUES (15, 124.2, 198.2, 876.4, 5, 2, 1);
INSERT INTO container VALUES (16, 124.2, 198.2, 876.4, 4, 2, 1);
INSERT INTO container VALUES (17, 124.2, 198.2, 876.4, 3, 1, 1);
INSERT INTO container VALUES (18, 124.2, 198.2, 876.4, 2, 2, 2);
INSERT INTO container VALUES (19, 124.2, 198.2, 876.4, 1, 2, 1);
INSERT INTO container VALUES (20, 124.2, 198.2, 876.4, 0, 1, 2);
INSERT INTO container VALUES (21, 124.2, 198.2, 876.4, 1, 2, 2);
INSERT INTO container VALUES (22, 124.2, 198.2, 876.4, 1, 2, 2);

--INSERT CONTAINER_CARGO_MANIFEST--
INSERT INTO container_cargo_manifest VALUES (1, 10, 1);
INSERT INTO container_cargo_manifest VALUES (2, 9, 2);
INSERT INTO container_cargo_manifest VALUES (3, 8, 3);
INSERT INTO container_cargo_manifest VALUES (4, 7, 4);
INSERT INTO container_cargo_manifest VALUES (5, 6, 5);
INSERT INTO container_cargo_manifest VALUES (6, 5, 6);
INSERT INTO container_cargo_manifest VALUES (7, 4, 7);
INSERT INTO container_cargo_manifest VALUES (8, 3, 8);
INSERT INTO container_cargo_manifest VALUES (9, 2, 9);
INSERT INTO container_cargo_manifest VALUES (10, 4, 10);
INSERT INTO container_cargo_manifest VALUES (11, 10, 11);
INSERT INTO container_cargo_manifest VALUES (12, 10, 12);
INSERT INTO container_cargo_manifest VALUES (13, 8, 13);
INSERT INTO container_cargo_manifest VALUES (14, 8, 14);
INSERT INTO container_cargo_manifest VALUES (15, 10, 15);
INSERT INTO container_cargo_manifest VALUES (16, 5, 16);
INSERT INTO container_cargo_manifest VALUES (17, 1, 17);
INSERT INTO container_cargo_manifest VALUES (18, 3, 18);
--INSERT INTO container_cargo_manifest VALUES (19, 2, 19);
INSERT INTO container_cargo_manifest VALUES (20, 1, 20);
INSERT INTO container_cargo_manifest VALUES (21, 2, 21);
INSERT INTO container_cargo_manifest VALUES (22, 2, 22);

--INSERT ROLE--
INSERT INTO role VALUES (1, 'User');
INSERT INTO role VALUES (2, 'Administrator');

--INSERT INTO Client--
INSERT INTO Client VALUES (1, 'Tiago');
INSERT INTO Client VALUES (2, 'Rui');
INSERT INTO Client VALUES (3, 'Joao');

--INSERT USER--
INSERT INTO "USER" VALUES ('jgd', '2dr4', 10, 1, 1);
INSERT INTO "USER" VALUES ('jgv', '2de5', 11, 2, 2);
INSERT INTO "USER" VALUES ('jgq', '2de6', 11, 2, 3);
INSERT INTO "USER" VALUES ('jga', '2de7', 11, 2, NULL);

--INSERT SHIP_CREW--
INSERT INTO ship_crew VALUES (1, 211331620);
INSERT INTO ship_crew VALUES (2, 211331620);
INSERT INTO ship_crew VALUES (1, 211331635);
INSERT INTO ship_crew VALUES (2, 211331635);
INSERT INTO ship_crew VALUES (1, 211331678);
INSERT INTO ship_crew VALUES (2, 211331678);
INSERT INTO ship_crew VALUES (1, 211331683);
INSERT INTO ship_crew VALUES (2, 211331683);
INSERT INTO ship_crew VALUES (1, 211331612);
INSERT INTO ship_crew VALUES (2, 211331612);

--INSERT INTO Container_Client--
-- REFAZER TUDO CONFORME NOVOS VALUES
--INSERT INTO Container_Client VALUES (ClientId, ContainerId, Cargo_ManifestId);
INSERT INTO Container_Client VALUES (1, 1, 10);
INSERT INTO Container_Client VALUES (1, 2, 9);
INSERT INTO Container_Client VALUES (1, 3, 8);
INSERT INTO Container_Client VALUES (2, 4, 7);
INSERT INTO Container_Client VALUES (2, 5, 6);
INSERT INTO Container_Client VALUES (2, 6, 5);
INSERT INTO Container_Client VALUES (3, 7, 4);
INSERT INTO Container_Client VALUES (3, 8, 3);
INSERT INTO Container_Client VALUES (3, 9, 2);
INSERT INTO Container_Client VALUES (1, 10, 4);
INSERT INTO Container_Client VALUES (1, 11, 10);
INSERT INTO Container_Client VALUES (1, 12, 10);
INSERT INTO Container_Client VALUES (2, 13, 8);
INSERT INTO Container_Client VALUES (2, 14, 8);
INSERT INTO Container_Client VALUES (2, 15, 10);
INSERT INTO Container_Client VALUES (3, 16, 5);
INSERT INTO Container_Client VALUES (3, 17, 1);
INSERT INTO Container_Client VALUES (3, 18, 3);
INSERT INTO Container_Client VALUES (1, 20, 1);
INSERT INTO Container_Client VALUES (1, 21, 2);
INSERT INTO Container_Client VALUES (1, 22, 2);
--INSERT INTO Container_Client VALUES (2, 2);
--INSERT INTO Container_Client VALUES (2, 1);
--INSERT INTO Container_Client VALUES (2, 3);
--INSERT INTO Container_Client VALUES (3, 1);
--INSERT INTO Container_Client VALUES (3, 1);
--INSERT INTO Container_Client VALUES (3, 2);
--INSERT INTO Container_Client VALUES (21, 3);
--INSERT INTO Container_Client VALUES (22, 1);