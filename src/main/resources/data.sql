
INSERT INTO tab_user (id, name, encoded_password) 
VALUES (nextval('seq_user'), 'administrator', 
'a8d35e1579f1d66da3ed20a78d9a90645525062f0f4aa1b451efe55c8e5fdd462e826d540df684c3');

INSERT INTO tab_authority (id, name) VALUES (nextval('seq_authority'), 'ADMINISTRATOR');
INSERT INTO tab_authority (id, name) VALUES (nextval('seq_authority'), 'OPERATOR');

INSERT INTO tab_user_authority (user_id, authority_id) 
SELECT u.id, a.id FROM tab_user u INNER JOIN tab_authority a ON 1 = 1
WHERE u.name = 'administrator' AND a.name = 'ADMINISTRATOR';

INSERT INTO tab_user_authority (user_id, authority_id) 
SELECT u.id, a.id FROM tab_user u INNER JOIN tab_authority a ON 1 = 1
WHERE u.name = 'operator' AND a.name = 'OPERATOR';

INSERT INTO tab_task (id, assigned_to, title, description, priority) 
SELECT nextval('seq_task'), u.id, 'Sample Task #1', 'Sample Task #1 Description', 4 
FROM tab_user u WHERE u.name = 'administrator';

INSERT INTO tab_task (id, assigned_to, title, description, priority) 
SELECT nextval('seq_task'), u.id, 'Sample Task #2', 'Sample Task #2 Description', 1 
FROM tab_user u WHERE u.name = 'administrator';

INSERT INTO tab_task (id, assigned_to, title, description, priority) 
SELECT nextval('seq_task'), u.id, 'Sample Task #1', 'Sample Task #1 Description', 3 
FROM tab_user u WHERE u.name = 'operator';

INSERT INTO tab_task (id, assigned_to, title, description, priority) 
SELECT nextval('seq_task'), u.id, 'Sample Task #2', 'Sample Task #2 Description', 2 
FROM tab_user u WHERE u.name = 'operator';

INSERT INTO tab_task (id, assigned_to, title, description, priority) 
SELECT nextval('seq_task'), u.id, 'Sample Task #3', 'Sample Task #3 Description', 1 
FROM tab_user u WHERE u.name = 'operator';

