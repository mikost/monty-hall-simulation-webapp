INSERT INTO SHOWCONTESTANT values ('Keeping', 1);
INSERT INTO SHOWCONTESTANT values ('Switching', 2);

CREATE TABLE Roles(id BIGINT, Role VARCHAR(64), RoleGroup VARCHAR(64), FOREIGN KEY (id) REFERENCES PUBLIC.Principals(id), constraint UK_ID_ROLE_ROLEGROUP unique (id, Role, RoleGroup));
INSERT INTO Principals (id, PrincipalID, Password) VALUES (hibernate_sequence.nextVal,'dbmonty', 'dbhall')
SET @the_id=SELECT id from Principals where PrincipalID='dbmonty'
INSERT INTO Roles VALUES (@the_id, 'uzers', 'Roles');
INSERT INTO Principals (id, PrincipalID, Password) VALUES (hibernate_sequence.nextVal,'user2', 'pw2')
SET @the_id=SELECT id from Principals where PrincipalID='user2'
INSERT INTO Roles VALUES (@the_id, 'uzers', 'Roles');

