--TESTING INTEGRITY RESTRICTIONS

-- Na execução destas linhas, a 2ª deverá dar erro uma vez que
-- o IMO é um atributo UNIQUE e está a ser repetidos.
INSERT INTO ship VALUES (111111111, 'SEOUlEXPRE', 1010100, 21, 12.3, 'DABN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (111111112, 'SEOUlEXPRE', 1010100, 21, 12.3, 'DBKL', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);

-- Na execução destas linhas, a 2ª deverá dar erro uma vez que
-- o CALLSIGN é um atributo UNIQUE e está a ser repetidos.
INSERT INTO ship VALUES (211334620, 'SEOUlEXPRE', 1010101, 21, 12.3, 'DHCN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);
INSERT INTO ship VALUES (211335624, 'SEOUlEXPRE', 1010110, 21, 12.3, 'DHCN', 70, 294.5, 32.6, 350, 13.6, 'B', 1254, 13, 78);

-- Na execução da linha abaixo deverá dar erro no atributo name
-- da tabela Port por exceder o máximo de 30 caracteres previamente definidos
INSERT INTO Port VALUES (29102, 'MaisDeTrintaCaracteresNumVarcharParaDarErro', 350, 1);

-- Na execução destas linhas:
-- A linha abaixo deverá dar erro pela já existência de um registo com a mesma PK.
INSERT INTO location VALUES (1, 'Liverpool', 34.91666667, 33.65, 1);
-- A linha abaixo deverá dar erro por exceder a verificação com o check do intervalo
-- de valores permitidos para o atributo latitude
INSERT INTO location VALUES (11, 'Los Angeles', 94, 33.65, 2);
-- A linha abaixo deverá dar erro por exceder a verificação com o check do intervalo
-- de valores permitidos para o atributo longitude
INSERT INTO location VALUES (12, 'New Jersey', 34.91666667, 190, 2);

-- Na execução da linha abaixo, deverá dar erro devido à inexistência da fk com o id=4.
INSERT INTO country VALUES (11, 'UK', 4);

-- Na execução da linha abaixo, deverá dar erro uma vez que um atributo NOT NULL está NULL.
INSERT INTO continent VALUES (4, null);