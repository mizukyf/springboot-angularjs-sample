
INSERT INTO tab_user (id, name, encoded_password) 
VALUES (nextval('seq_user'), 'administrator', 
'a8d35e1579f1d66da3ed20a78d9a90645525062f0f4aa1b451efe55c8e5fdd462e826d540df684c3');

INSERT INTO tab_role (id, name) VALUES (nextval('seq_role'), 'ADMINISTRATOR');
INSERT INTO tab_role (id, name) VALUES (nextval('seq_role'), 'OPERATOR');

INSERT INTO tab_user_role (user_id, role_id) 
SELECT u.id, r.id FROM tab_user u INNER JOIN tab_role r ON 1 = 1
WHERE u.name = 'administrator' AND r.name = 'ADMINISTRATOR';

INSERT INTO tab_user_role (user_id, role_id) 
SELECT u.id, r.id FROM tab_user u INNER JOIN tab_role r ON 1 = 1
WHERE u.name = 'operator' AND r.name = 'OPERATOR';

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

