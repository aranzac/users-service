DELETE FROM roles WHERE id = 3;
DELETE FROM users WHERE username = 'prueba' AND email = 'prueba@prueba';


INSERT INTO users (username, password, email, state) values('prueba','123456','prueba@prueba','enabled');
INSERT INTO roles (id, name) values (3,"ROLE_PRUEBA");