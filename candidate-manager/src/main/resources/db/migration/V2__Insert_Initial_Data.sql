INSERT INTO T_USER(name,state,registrationDate) VALUES('CHRISTIAN',1,NOW());
INSERT INTO T_USER_PASSWORD(idUser,password,state,registrationDate) VALUES(1,'BALDEON',1,NOW());
INSERT INTO T_ROLE(code,name,state,registrationDate) VALUES ('ADMIN','ADMIN',1,NOW());
INSERT INTO T_USER_ROLE(idUser,idRole,state,registrationDate) VALUES (1,1,1,NOW());

INSERT INTO T_CANDIDATE(idCandidate,name,email,gender,expectedSalary,state,registrationDate)
VALUES (1,'Christian Baldeón Baldeón','baldeon.bc@gmai.com','masculino',10000.00,1,NOW());
INSERT INTO T_CANDIDATE(idCandidate,name,email,gender,expectedSalary,state,registrationDate)
VALUES (2,'Leonardo Guitierres Torrez','torres.gt@gmai.com','masculino',5000.00,1,NOW());
INSERT INTO T_CANDIDATE(idCandidate,name,email,gender,expectedSalary,state,registrationDate)
VALUES (3,'Miroslab Perez Tello','miros.pt@gmai.com','masculino',7000.00,1,NOW());
INSERT INTO T_CANDIDATE(idCandidate,name,email,gender,expectedSalary,state,registrationDate)
VALUES (4,'Edgar Duran Medrano','edgar.dm@gmai.com','masculino',15000.00,1,NOW());
INSERT INTO T_CANDIDATE(idCandidate,name,email,gender,expectedSalary,state,registrationDate)
VALUES (5,'Mario Morales Cueto','mario.mc@gmai.com','masculino',10000.00,1,NOW());
