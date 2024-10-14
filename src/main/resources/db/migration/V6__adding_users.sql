INSERT INTO users (user_id, name, email, password, created_at, updated_at, deleted, active) VALUES
                                                                                                ('5b2a5692-c47f-4751-b065-d42078f3fe56', 'analista', 'analista@cooperfilme.com', '$2a$10$feMJwEau6k.5kyC65S7r7uSWhnav0YBHKGXqL9wsDBQ2l9ZRKiTj.', '2024-10-14 16:11:16', '2024-10-14 16:11:16', 0, 1),
                                                                                                ('7f271271-6c28-4be1-805c-936fa383e3ac', 'revisor', 'revisor@cooperfilme.com', '$2a$10$Gw.m8Shtt.rEntJrfFP9AuOZ620N/MShKn2qNRlZllpuRLpfdgXfy', '2024-10-14 16:11:50', '2024-10-14 16:11:50', 0, 1),
                                                                                                ('9ea41daf-2793-4a0e-9692-5b090cfa25ef', 'aprovador2', 'aprovador2@cooperfilme.com', '$2a$10$Uf7iZfXulBTW9Ahpqu6CE.qUHXnLI0IA7dsv9dUYjirn5DCDOYo0m', '2024-10-14 16:12:30', '2024-10-14 16:12:30', 0, 1),
                                                                                                ('a5ceac0f-14b9-4a53-a470-2894d18031ef', 'aprovador1', 'aprovador1@cooperfilme.com', '$2a$10$N3SLXOd8G75cjxigNTcVc.GgDAdwpBEoU9yz9HkCt/Y//9b9ASHsa', '2024-10-14 16:12:22', '2024-10-14 16:12:22', 0, 1),
                                                                                                ('dcb8cda9-9d8b-4683-bce3-c997b01e7100', 'aprovador3', 'aprovador3@cooperfilme.com', '$2a$10$zDAJR5KIeSNQkxjt/7jiLuwMf3tg2dUX.h5p0EPUHrlLb.3t70qLq', '2024-10-14 16:12:37', '2024-10-14 16:12:37', 0, 1);


INSERT INTO user_roles (user_id, role_id) VALUES
                                             ('5b2a5692-c47f-4751-b065-d42078f3fe56', 1),
                                             ('7f271271-6c28-4be1-805c-936fa383e3ac', 2),
                                             ('9ea41daf-2793-4a0e-9692-5b090cfa25ef', 3),
                                             ('a5ceac0f-14b9-4a53-a470-2894d18031ef', 3),
                                             ('dcb8cda9-9d8b-4683-bce3-c997b01e7100', 3);
